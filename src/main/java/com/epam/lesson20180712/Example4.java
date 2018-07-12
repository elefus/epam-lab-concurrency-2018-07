package com.epam.lesson20180712;

import java.util.concurrent.TimeUnit;

public class Example4 {

    public static void main(String[] args) throws InterruptedException {
        Thread daemon = new Thread(() -> {
            try {
                while (true) {
                    try {
                        System.out.println(Thread.currentThread().isDaemon());
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("Hello from daemon");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {

            } finally {
                System.out.println("Finally in daemon");
            }
        });
        daemon.setDaemon(true);
        daemon.setDaemon(false);
        daemon.setDaemon(true);
        daemon.start();

        TimeUnit.SECONDS.sleep(3);

        daemon.join();
    }
}
