package com.epam.lesson20180720;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Example2 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        service.execute(task);
        service.execute(task);
        service.execute(task);

        service.shutdownNow();
        service.shutdownNow();
    }
}
