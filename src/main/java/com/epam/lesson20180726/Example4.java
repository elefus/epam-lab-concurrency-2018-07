package com.epam.lesson20180726;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class Example4 {


    public static void main(String[] args) throws InterruptedException {
        A ref = new A();

        AtomicIntegerFieldUpdater<A> updater = AtomicIntegerFieldUpdater.newUpdater(A.class, "field");

        ExecutorService service = Executors.newCachedThreadPool();

        service.submit(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                updater.incrementAndGet(ref);
            }
        });

        service.submit(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                updater.decrementAndGet(ref);
            }
        });

        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);

        System.out.println(ref.field);
    }
}


class A {

    volatile int field;
}