package com.epam.lesson20180724;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class Example3 {

    private static class BlockingQueue<E> {

        private final LinkedList<E> list = new LinkedList<>();

        public boolean add(E element) {
            synchronized (list) {
                list.offer(element);
                list.notify();
            }
            return true;
        }

        public E take() throws InterruptedException {
            synchronized (list) {
                while (list.isEmpty()) {
                    list.wait();
                }
                return list.removeFirst();
            }
        }
    }

    private static class SingleThreadExecutor implements Executor {

        private final BlockingQueue<Runnable> tasks = new BlockingQueue<>();
        private final Thread worker;
        private volatile boolean isShutdown = false;

        public SingleThreadExecutor() {
            worker = new Thread(() -> {
                try {
                    while (!isShutdown) {
                        try {
                            tasks.take().run();
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
                        throw new UnsupportedOperationException();
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
            } else {
                tasks.add(command);
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
