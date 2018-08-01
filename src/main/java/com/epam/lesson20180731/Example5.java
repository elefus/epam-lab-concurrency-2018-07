package com.epam.lesson20180731;

import java.util.concurrent.*;

public class Example5 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = new ForkJoinPool(1);

        Future<?> submit = executor.submit(() -> {
            System.out.println("Some task");
            Future<?> anotherTask = executor.submit(() -> {
                System.out.println("Another task");
            });
            try {
                anotherTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        submit.get();
    }
}
