package com.epam.lesson20180710;

public class Example2 {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> System.out.println("Defaul handler"));

        Thread main = Thread.currentThread();
        ThreadGroup threadGroup = main.getThreadGroup();
        main.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("Handler for thread main");
            System.out.println(Thread.currentThread());
            throw new RuntimeException(e);
        });
        System.out.println(main);

        method1();
        method3();
    }

    private static void method1() {
        try {
            method2();
        } catch (RuntimeException ex) {
            throw ex;
        }
    }

    private static void method2() {
        throw new RuntimeException("Hello from method2");
    }

    private static void method3() {
        System.out.println("From method 3");
    }


}
