package com.epam.lesson20180712;

import java.util.concurrent.TimeUnit;

public class Example5 {

    private static long value = 0;


    public static void main(String[] args) throws InterruptedException {
        Thread counter = new Thread(() -> {
            Thread current = Thread.currentThread();
            for (int i = 0; i < 1_000_000_000L; ++i) {
                ++value;
            }
            System.out.println(Thread.currentThread().isInterrupted());
        });


        counter.start();

        TimeUnit.SECONDS.sleep(1);
//        counter.interrupt();

        System.out.println(value);
    }
}
