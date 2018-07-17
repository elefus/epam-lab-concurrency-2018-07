package com.epam.lesson20180713;

import lombok.Synchronized;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class Example6 {


    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        CounterWithPrivateLock counter = new CounterWithPrivateLock();

        Field lockField = CounterWithPrivateLock.class.getDeclaredField("$lock");
        lockField.setAccessible(true);
        Object lock = lockField.get(counter);

        System.out.println(lock.getClass());

        Thread incThread = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                counter.inc();
            }
            System.out.println("Inc end");
        });
        Thread dec1Thread = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                counter.dec();
            }
            System.out.println("Dec end");
        });

        dec1Thread.start();
        incThread.start();

        synchronized (lock) {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("After sleep");
        }

        incThread.join();
        dec1Thread.join();

        System.out.println(counter.getValue());

    }
}

class CounterWithPrivateLock {

    private volatile int value = 0;

    @Synchronized
    void inc() {
        ++value;
    }

    @Synchronized
    void dec() {
        --value;
    }

    public int getValue() {
        return value;
    }
}