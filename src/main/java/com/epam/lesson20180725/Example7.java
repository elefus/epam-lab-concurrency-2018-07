package com.epam.lesson20180725;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.Executors.callable;

public class Example7 {

    private static final AtomicLong VALUE = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        Runnable inc = () -> {
            for (int i = 0; i < 1_000_000; ++i) {
//                long actual;
//                do {
//                    actual = VALUE.get();
//                } while (!VALUE.compareAndSet(actual, actual + 1));

                VALUE.incrementAndGet();
            }
        };

        Runnable dec = () -> {
            for (int i = 0; i < 1_000_000; ++i) {
                long actual;
                long newValue;
                do {

                    // T1
                    actual = VALUE.get(); // 10
                    newValue = actual - 1;

                    // T2
                    // get -> 10
                    // CAS 10 -> 15
                    // T3
                    // get -> 15
                    // CAS 15 -> 10

                    // ABA

                } while (!VALUE.compareAndSet(actual, newValue)); // 10
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.invokeAll(Arrays.asList(callable(inc), callable(dec)));
        service.shutdown();

        System.out.println(VALUE.get());
    }

}
