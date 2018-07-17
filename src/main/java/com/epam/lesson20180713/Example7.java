package com.epam.lesson20180713;

import java.util.concurrent.TimeUnit;

public class Example7 {

    public static void main(String[] args) throws InterruptedException {
        A a = new B();

        Thread main = Thread.currentThread();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                main.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        synchronized (a) {
            Thread thread = new Thread(() -> {
                a.method();
            });
            thread.start();
            thread.join();
        }
    }

}

abstract class A {

    abstract  void abstractMethod();

    public void method() {
        System.out.println("Synchro-method");
    }
}


class B extends A {

    @Override
    void abstractMethod() {

    }

    @Override
    public  void method() {
        System.out.println("Non-synchro-method");
    }
}