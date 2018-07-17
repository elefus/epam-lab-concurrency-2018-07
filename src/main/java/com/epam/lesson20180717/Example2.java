package com.epam.lesson20180717;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Example2 {

    // T1
    // T2

    // A
    // B

    // T1 -> A
    // T2 -> B

    // T1(A) -> B
    // T2(B) -> A

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        new Thread(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                synchronized (a) {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (b) {
                        System.out.println("Got it (1)!");
                    }
                }
            }
        }, "Thread-1").start();

        Thread thread = new Thread(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                synchronized (b) {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (a) {
                        System.out.println("Got it (2)!");
                    }
                }
            }
        }, "Thread-2");
        thread.start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(thread.getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
