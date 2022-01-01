package chap07;

import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

import static chap07.ParallelStreamsHarness.FORK_JOIN_POOL;


// 1차: 39 msecs, 2차: 39 msecs, 3차:
public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    public static final long THRESHOLD = 10_000;
    private final long[] numbers; // 더할 숫자
    private final int start; // 각 서브작업별 시작숫자
    private final int end;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) { // 10만개 < 1만개
            return computeSequentially(); // 업무가 충분히 작아졌을 경우 실행
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        leftTask.fork();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }

    public static void main(String[] args) {
        forkJoinSum(100_000); // 십만
    }
}

