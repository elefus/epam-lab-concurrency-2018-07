package com.epam.lesson20180726;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Example3 {

    private static volatile int[] arr = new int[]{1, 2, 3};

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        AtomicIntegerArray atomicArray = new AtomicIntegerArray(arr);

        service.submit(() -> {
            atomicArray.updateAndGet(2, val -> val * 2);
            System.out.println(arr[0]);
        });

        service.submit(() -> {
            atomicArray.incrementAndGet(2);
            System.out.println(arr[0]);
        });

        service.shutdown();

    }
}
