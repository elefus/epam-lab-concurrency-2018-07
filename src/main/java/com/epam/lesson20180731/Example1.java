package com.epam.lesson20180731;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class Example1 {

    public static void main(String[] args) {
        int[] arr = ThreadLocalRandom.current().ints(50, 0, 100).toArray();
        System.out.println("Before: " + Arrays.toString(arr));

        ForkJoinPool.commonPool().invoke(new ForkJoinQuickSortAction(arr));

        System.out.println("After: " + Arrays.toString(arr));
    }
}

class ForkJoinQuickSortAction extends RecursiveAction {

    private static final int SEQUENTIAL_THRESHOLD = 10;
    private final int[] data;
    private final int fromInclusive;
    private final int toInclusive;

    public ForkJoinQuickSortAction(int[] data) {
        this(data, 0, data.length - 1);
    }

    private ForkJoinQuickSortAction(int[] data, int fromInclusive, int toInclusive) {
        this.fromInclusive = fromInclusive;
        this.toInclusive = toInclusive;
        this.data = data;
    }

    @Override
    protected void compute() {
        if (toInclusive - fromInclusive < SEQUENTIAL_THRESHOLD) {
            System.out.println(Thread.currentThread());
            Arrays.sort(data, fromInclusive, toInclusive + 1);
        } else {
            int pivot = partion(data, fromInclusive, toInclusive);
            ForkJoinQuickSortAction left = new ForkJoinQuickSortAction(data, fromInclusive, pivot);
            ForkJoinQuickSortAction right = new ForkJoinQuickSortAction(data, pivot + 1, toInclusive);

            invokeAll(left, right);
        }
    }

    private int partion(int[] array, int fromInclusive, int toInclusive) {
        int pivot = array[fromInclusive];
        int i = fromInclusive - 1;
        int j = toInclusive + 1;
        while (true) {
            do {
                ++i;
            } while (array[i] < pivot);

            do {
                j--;
            } while (array[j] > pivot);

            if (i >= j) {
                return j;
            }

            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}

