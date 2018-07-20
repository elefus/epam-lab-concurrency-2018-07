package com.epam.lesson20180720;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Example4 {

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.schedule(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 3, TimeUnit.SECONDS);

        service.schedule(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 3, TimeUnit.SECONDS);


        service.scheduleWithFixedDelay(() -> System.out.println("Hello repeat"), 0, 1, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(() -> System.out.println("Hello!"), 0, 3, TimeUnit.SECONDS);
    }
}
