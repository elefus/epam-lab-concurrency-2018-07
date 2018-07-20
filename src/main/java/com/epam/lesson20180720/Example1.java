package com.epam.lesson20180720;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class Example1 {

    private static class SingleThreadExecutor implements Executor {

        private final Queue<Runnable> tasks = new LinkedList<>();
        private final Thread worker;
        private volatile boolean isShutdown = false;

        public SingleThreadExecutor() {
            worker = new Thread(() -> {
                try {
                    while (!isShutdown) {
                        try {
                            synchronized (tasks) {
                                while (tasks.isEmpty()) {
                                    tasks.wait();
                                }
                            }
                            tasks.remove().run();
                        } catch (Throwable ex) {
                            if (ex instanceof InterruptedException) {
                                throw ex;
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Worker was interrupted");
                }
            }, "Single-thread worker");
            worker.start();
        }

        public void shutdown() {

        }

        public Queue<Runnable> shutodwnNow() {
            if (!isShutdown) {
                synchronized (tasks) {
                    if (!isShutdown) {
                        worker.interrupt();
                        isShutdown = true;
                        return new LinkedList<>(tasks);
                    }
                    return new LinkedList<>();
                }
            }
            return new LinkedList<>();
        }

        @Override
        public void execute(Runnable command) {
            if (isShutdown) {
                throw new IllegalStateException("Executor already shutdown");
            }
            synchronized (tasks) {
                if (!isShutdown) {
                    tasks.offer(command);
                    tasks.notify();
                } else {
                    throw new IllegalStateException("Executor already shutdown");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SingleThreadExecutor executor = new SingleThreadExecutor();

        Runnable task = () -> {
            try {
                System.out.println("Before sleep");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("After sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        executor.execute(task);
        executor.execute(task);

        TimeUnit.SECONDS.sleep(2);

        Queue<Runnable> rejectedTasks = executor.shutodwnNow();
        System.out.println(rejectedTasks.size());
    }

}
