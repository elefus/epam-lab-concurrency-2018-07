package com.epam.lesson20180713;

import java.util.concurrent.TimeUnit;

public class Example2 {

    public static void main(String[] args) throws InterruptedException {
//        Thread thread = new Thread(() -> {
//            System.out.println(Thread.currentThread().isInterrupted());
//            try {
//                System.out.println("Before sleep");
//                TimeUnit.SECONDS.sleep(3);
//                System.out.println("After sleep");
//            } catch (InterruptedException e) {
//                System.out.println("Interrupted: " + Thread.currentThread().isInterrupted());
//            }
//        });
//
//        thread.start();
//        thread.interrupt();

        Thread.currentThread().interrupt();
        TimeUnit.SECONDS.sleep(5);
    }
}
