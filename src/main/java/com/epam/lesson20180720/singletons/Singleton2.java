package com.epam.lesson20180720.singletons;

public class Singleton2 {

    private static volatile Singleton2 INSTANCE;

    public static synchronized Singleton2 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton2();
        }
        return INSTANCE;
    }

    private Singleton2() {
        throw new UnsupportedOperationException();
    }
}
