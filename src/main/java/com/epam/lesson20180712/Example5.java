package com.epam.lesson20180712;

import java.util.concurrent.TimeUnit;

public class Example5 {

    public static void main(String[] args) throws InterruptedException {
        Thread counter = new Thread(() -> {
            Thread current = Thread.currentThread();
            boolean isInterrupted = false;
            for (int i = 0; i < 1_000_000_000; ++i) {
                isInterrupted = current.isInterrupted();
            }
            System.out.println(isInterrupted);
        });

        counter.start();

        TimeUnit.SECONDS.sleep(1);
        counter.interrupt();
    }
}
