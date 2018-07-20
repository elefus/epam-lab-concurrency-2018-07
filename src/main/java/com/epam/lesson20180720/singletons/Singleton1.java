package com.epam.lesson20180720.singletons;

public class Singleton1 {

    public static final Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {
        throw new UnsupportedOperationException();
    }
}
