package com.epam.lesson20180719;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Example4 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runnable task = () -> {
            try {
                System.out.println("Before sleep");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("After sleep" + Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        executor.execute(task);
        executor.execute(task);
        executor.execute(task);

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

//        executor.execute(task);

        System.out.println("Main end");
    }
}
