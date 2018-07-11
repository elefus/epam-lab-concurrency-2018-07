package com.epam.lesson20180711;

import java.util.concurrent.TimeUnit;

public class Example4 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Before start = " + thread.getState());

        thread.start();

        System.out.println("Before sleep = " + thread.getState());

        TimeUnit.SECONDS.sleep(1);;

        System.out.println("Before join = " + thread.getState());

        thread.join();

        System.out.println("End: " + thread.getState());
    }
}
