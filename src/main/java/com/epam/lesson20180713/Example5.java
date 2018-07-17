package com.epam.lesson20180713;

public class Example5 {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread incThread = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                counter.inc();
            }
        });
        Thread dec1Thread = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                counter.dec();
            }
        });

        dec1Thread.start();
        incThread.start();

        incThread.join();
        dec1Thread.join();

        System.out.println(counter.getValue());
    }
}

class Counter {

    private volatile int value = 0;

    public synchronized static void synchroSayHello() {
        System.out.println("Hello");
    }

    public static void sayHello() {
        synchronized (Counter.class) {
            System.out.println("Hello");
        }
    }

    synchronized void inc() {
        ++value;
    }

    void dec() {
        synchronized (this) {
            --value;
        }
    }

    public int getValue() {
        return value;
    }
}
