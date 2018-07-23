package com.epam.lesson20180723;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.Executors.callable;

public class Example4 {

    private static volatile long value = 0L;

    // T1
    // T2
    // T3


    // lock

    // T3 T1 T2
    //


    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();

        Runnable inc = () -> {
            for (int i = 0; i < 1_000_000; ++i) {
                if (lock.tryLock()) {
                    try {
                        ++value;
                    } finally {
                        lock.unlock();
                    }
                }
            }
        };

        Runnable dec = () -> {
            for (int i = 0; i < 1_000_000; ++i) {
                lock.lock();
                try {
                    --value;
                } finally {
                    lock.unlock();
                }
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Future<Object>> futures = service.invokeAll(Arrays.asList(callable(inc), callable(dec)));
        for (Future<Object> future : futures) {
            System.out.println(future.isDone());
            try {
                System.out.println(future.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();

        System.out.println(value);
    }
}
