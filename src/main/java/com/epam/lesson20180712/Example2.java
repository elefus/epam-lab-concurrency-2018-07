package com.epam.lesson20180712;

public class Example2 {

    private static boolean isAlive;
    private static long value = 0;

    public static void main(String[] args) {
        Runnable counterTask = () -> {
            isAlive = true;
            for (int i = 0; i < 1_000_000_000; ++i) {
                ++value;
            }
            isAlive = false;
        };

        Thread counterThread = new Thread(counterTask);
        counterThread.start();

        // busy wait
        try {
            while (counterThread.isAlive()) {
                counterThread.join(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(value);

    }
}
