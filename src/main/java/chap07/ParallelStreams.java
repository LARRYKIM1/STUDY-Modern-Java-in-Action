package chap07;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {
    // 1차: 6 msecs, 2차: 6 msecs, 3차:
    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    // 1차: 102 msecs, 2차: 101 msecs, 3차:
    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();
    }

    // 1차: 392 msecs, 2차: 314 msecs, 3차:
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();
    }

    // 1차: 17 msecs, 2차: 17 msecs, 3차:
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
    }

    // 1차: 5 msecs, 2차: 4 msecs, 3차:
    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();
    }

    // 1차: 6 msecs, 2차: 6 msecs, 3차:
    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    // 1차: 57 msecs, 2차: 56 msecs, 3차:
    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static class Accumulator {
        private long total = 0;
        public void add(long value) {
            total += value;
        }
    }
}
