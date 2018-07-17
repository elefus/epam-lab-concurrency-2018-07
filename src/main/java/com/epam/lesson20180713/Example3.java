package com.epam.lesson20180713;

public class Example3 {

    private static volatile Long value = 0L;

    public static void main(String[] args) throws InterruptedException {
        Runnable inc = () -> {
            for (int i = 0; i < 1_000_000; ++i) {
                synchronized (value) {
                    ++value;
                }
            }
        };

        Runnable dec1 = () -> {
            for (int i = 0; i < 1_000_000; ++i) {
                synchronized (value) {
                    --value;
                }
            }
        };

        Thread incThread = new Thread(inc);
        Thread dec1Thread = new Thread(dec1);

        dec1Thread.start();
        incThread.start();

        incThread.join();
        dec1Thread.join();

        System.out.println(value);
    }
}
