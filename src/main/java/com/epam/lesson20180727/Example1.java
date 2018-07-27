package com.epam.lesson20180727;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Example1 {

    /**
     * Существует парковка, которая одновременно может вмещать не более 5 автомобилей.
     * Если парковка заполнена полностью, то вновь прибывший автомобиль должен подождать пока не освободится хотя бы одно место.
     * После этого он сможет припарковаться.
     */
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Semaphore parking = new Semaphore(-1);

        Runnable car = () -> {
            String threadName = Thread.currentThread().getName();
            try {
                parking.acquire();
                System.out.println("Заехал на парковку: " + threadName);
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Покидает парковку: " + threadName);
                parking.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        service.submit(car);
        service.submit(car);
        service.submit(car);
        service.submit(car);
        service.submit(car);
        service.submit(car);
        service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                parking.release(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();

    }
}
