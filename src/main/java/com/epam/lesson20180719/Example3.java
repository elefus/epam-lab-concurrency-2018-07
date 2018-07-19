package com.epam.lesson20180719;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class Example3 {

    private static class SingleThreadExecutor implements Executor {

        private final Queue<Runnable> tasks = new LinkedList<>();
        private final Thread worker;
        private boolean isShutdown = false;

        public SingleThreadExecutor() {
            worker = new Thread(() -> {
                try {
                    Thread current = Thread.currentThread();
                    while (!current.isInterrupted()) {
                        synchronized (tasks) {
                            while (tasks.isEmpty()) {
                                tasks.wait();
                            }
                        }
                        tasks.remove().run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }, "Single-thread worker");
            worker.start();
        }

        public void shutdown() {

        }

        public Queue<Runnable> shutodwnNow() {
            synchronized (tasks) {
                worker.interrupt();
            }
            throw new UnsupportedOperationException();
        }

        @Override
        public void execute(Runnable command) {
            // Double
            // Check
            // Lock
            synchronized (tasks) {
                tasks.offer(command);
                tasks.notify();
            }
        }
    }


    public static void main(String[] args) {
        Executor executor = new SingleThreadExecutor();

        Runnable task = () -> {
            try {
                System.out.println("Before sleep");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("After sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        executor.execute(task);
        executor.execute(task);
        executor.execute(task);
    }

}
