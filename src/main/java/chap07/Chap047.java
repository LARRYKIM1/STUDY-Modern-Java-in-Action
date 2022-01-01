package chap07;

import java.util.stream.LongStream;

public class Chap047 {
    public static void main(String[] args) {
        System.out.println(sideEffectSum(1_000_000));
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    static class Accumulator {
        public long total = 0;
        public void add(long value) { total += value; }
    }
}
