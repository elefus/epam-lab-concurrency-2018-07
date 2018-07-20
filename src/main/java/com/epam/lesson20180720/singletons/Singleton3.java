package com.epam.lesson20180720.singletons;

import java.util.concurrent.TimeUnit;

public class Singleton3 {

    private static volatile Singleton3 INSTANCE;
    private static final Object lock = new Object();

    private String str;
    private int value;

    private Singleton3(String str, int value) {
        this.str = str;
        this.value = value;
    }

    // Double
    // Check
    // Lock
    public static Singleton3 getInstance() {
        Singleton3 localRef = INSTANCE;
        if (localRef == null) {
            synchronized (lock) {
                localRef = INSTANCE;
                if (localRef == null) {
                    INSTANCE = localRef = new Singleton3("abc", 55);
                }
            }
        }
        return localRef;
    }
}

class Launcher {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (Singleton3.class) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(2);
        System.out.println(Singleton3.getInstance());
    }
}