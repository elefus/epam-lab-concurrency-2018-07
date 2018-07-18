package com.epam.lesson20180718;

import java.util.concurrent.TimeUnit;

public class Example3 {

    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();

        Thread counter = new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.holdsLock(object));
                long value = 0;
                for (long i = 0; i < Integer.MAX_VALUE; ++i) {
                    ++value;
                }
                System.out.println(value);
            }
        });
        counter.start();

        Thread.sleep(500);
        counter.suspend();


        TimeUnit.SECONDS.sleep(1);
//        counter.resume();
        System.out.println("After resume");

        synchronized (object) {
            System.out.println("Got it!");
        }
    }
}
