package com.epam.lesson20180727;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Example4 {

    private static volatile boolean isStarted = false;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(1);

        service.submit(() -> {
            try {
                isStarted = true;
                latch.countDown();
                TimeUnit.SECONDS.sleep(1);
                System.out.println("End!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        latch.await();
        System.out.println(isStarted);

        service.shutdown();


    }
}
