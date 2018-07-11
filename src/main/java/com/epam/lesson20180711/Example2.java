package com.epam.lesson20180711;

import java.util.concurrent.TimeUnit;

public class Example2 {

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());

        long start = System.nanoTime();

        Runnable task = () -> {
            long value = 0;
            for (long i = 0; i < 1_000_000_000L; ++i) {
                ++value;
            }
            System.out.println(value);
        };

        Thread counter1 = new Thread(task);
        Thread counter2 = new Thread(task);
        Thread counter3 = new Thread(task);
        counter1.start();
        counter2.start();
        counter3.start();

        try {
            counter1.join();
            counter2.join();
            counter3.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(end - start));
    }
}
