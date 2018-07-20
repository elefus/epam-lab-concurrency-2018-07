package com.epam.lesson20180720.singletons;

public enum Singleton4 {
    INSTANCE("abc", 66);

    private final String str;
    private final int value;

    Singleton4(String str, int value) {
        this.str = str;
        this.value = value;
    }

    public void method() {
        System.out.println("Method from singleton");
    }

    public static void staticMethod() {
        System.out.println("Method from singleton");
    }
}

class Launcher2 {

    public static void main(String[] args) {
        Singleton4.INSTANCE.method();
        Singleton4.staticMethod();
    }
}
