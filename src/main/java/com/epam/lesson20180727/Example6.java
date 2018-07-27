package com.epam.lesson20180727;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Example6 {

    private static int[] arr1 = ThreadLocalRandom.current().ints(10_000_000).toArray();
    private static int[] arr2 = arr1.clone();

    public static void main(String[] args) {
        sort(Arrays::sort, arr1);
        sort(Arrays::parallelSort, arr2);
    }

    private static void sort(Consumer<int[]> sort, int[] arr) {
        long start = System.nanoTime();
        sort.accept(arr);

        System.out.println(System.nanoTime() - start);
    }
}
