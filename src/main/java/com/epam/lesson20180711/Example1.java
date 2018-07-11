package com.epam.lesson20180711;

import java.util.concurrent.TimeUnit;

public class Example1 {

    public static void main(String[] args) throws InterruptedException {
        Thread main = Thread.currentThread();
        System.out.println(main);

        HelloWorldThread task1 = new HelloWorldThread();
        task1.setName("Task2");
        task1.start();

        Runnable task2 = () -> System.out.println("Hello from thread " + Thread.currentThread());
        Thread thread = new Thread(task2);
        thread.setName("Task2");
        thread.start();


        TimeUnit.SECONDS.sleep(100);
    }
}

class HelloWorldThread extends Thread {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Hello from thread " + Thread.currentThread());
    }
}
