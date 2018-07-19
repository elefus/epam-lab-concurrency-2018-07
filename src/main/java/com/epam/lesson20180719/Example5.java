package com.epam.lesson20180719;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Example5 {

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

        TimeUnit.MILLISECONDS.sleep(500);

        List<Runnable> runnables = executor.shutdownNow();
        System.out.println(runnables.size());

        System.out.println("Main end");
    }
}
