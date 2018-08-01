package com.epam.lesson20180731;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class Example2 {

    public static void main(String[] args) {
        int[] data = IntStream.generate(() -> 1).limit(100).toArray();

        ForkJoinPool pool = new ForkJoinPool(3);
        ForkJoinTask<?> task = pool.submit(() -> System.out.println(new ForkJoinSumArray(data).invoke()));
        task.join();
    }
}

class ForkJoinSumArray extends RecursiveTask<Long> {

    private static final int SEQUENTIAL_THRESHOLD = 10;
    private final int[] data;
    private final int fromInclusive;
    private final int toInclusive;

    public ForkJoinSumArray(int[] data) {
        this(data, 0, data.length - 1);
    }

    ForkJoinSumArray(int[] data, int fromInclusive, int toInclusive) {
        this.data = data;
        this.fromInclusive = fromInclusive;
        this.toInclusive = toInclusive;
    }

    @Override
    protected Long compute() {
        if (toInclusive - fromInclusive < SEQUENTIAL_THRESHOLD) {
            System.out.println(Thread.currentThread());
            long sum = 0;
            for (int i = fromInclusive; i <= toInclusive; ++i) {
                sum += data[i];
            }
            return sum;
        }
        int mid = fromInclusive + ((toInclusive - fromInclusive) >>> 1);
        ForkJoinSumArray left = new ForkJoinSumArray(data, fromInclusive, mid);
        ForkJoinSumArray right = new ForkJoinSumArray(data, mid + 1, toInclusive);

        left.fork();
        right.fork();

        return right.join() + left.join();
    }
}
