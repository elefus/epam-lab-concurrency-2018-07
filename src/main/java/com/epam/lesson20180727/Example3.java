package com.epam.lesson20180727;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example3 {

    private static final ThreadLocal<String> var = new ThreadLocal<>();

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        System.out.println(var.get());
        var.set("Main");
        System.out.println(var.get());
        System.out.println(var.hashCode());

        service.submit(() -> {
            System.out.println(var.get());
            var.set("Task-1");
            System.out.println(var.get());
            System.out.println(var.hashCode());
        });

        service.submit(() -> {
            System.out.println(var.get());
            var.set("Task-2");
            System.out.println(var.get());
            System.out.println(var.hashCode());
        });

        service.submit(() -> {
            System.out.println(var.get());
            var.set("Task-3");
            System.out.println(var.get());
            System.out.println(var.hashCode());
        });

        service.shutdown();
    }
}
