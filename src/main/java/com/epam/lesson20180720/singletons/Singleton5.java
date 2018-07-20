package com.epam.lesson20180720.singletons;

public class Singleton5 {

    public static Singleton5 getInstance() {
//        return Singleton5Holder.INSTANCE;
        return Holder.INSTANCE.getInstance();
    }

    public static void staticMethod() {
        System.out.println("Void method");
    }

    private static class Singleton5Holder {
        private static final Singleton5 INSTANCE = new Singleton5();
    }

    private enum Holder {
        INSTANCE;

        private final Singleton5 instance = new Singleton5();

        public Singleton5 getInstance() {
            return instance;
        }
    }
}
