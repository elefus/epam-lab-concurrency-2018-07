package com.epam.lesson20180720;

import java.util.concurrent.*;

public class Example3 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable task) {
                Thread thread = new Thread(task);


                return thread;
            }
        });
//
//
//        service.execute(() -> {
//            System.out.println(Thread.currentThread());
//        });
//
//        service = Executors.newFixedThreadPool(4);
//
//        service = Executors.newCachedThreadPool();


        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 16, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("LOL NO!");
            }
        });
        executor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("First task completed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        executor.setCorePoolSize(100500);

        executor.shutdown();
        executor.execute(() -> {
            System.out.println("Another task");
        });

        ExecutorService unconfigurable = Executors.unconfigurableExecutorService(executor);

        clientMethod(unconfigurable);
        System.out.println(executor.getCorePoolSize());

    }

    public static void clientMethod(ExecutorService service) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) service;
        executor.setCorePoolSize(10);
    }
}
