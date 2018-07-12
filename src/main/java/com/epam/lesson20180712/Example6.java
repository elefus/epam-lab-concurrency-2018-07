package com.epam.lesson20180712;

import java.util.concurrent.TimeUnit;

public class Example6 {

    enum ThreadState {
        RUNNING,
        PAUSED,
        STOPPED
    }

    private static volatile ThreadState state;
    private static volatile long value;


    // long = 8 bytes
    // 64-bit - volatile read/write
    // 32-bit

    // 0xFFFFFFFF00000000

    // Reader-thread
    // read-upper 0xFFFFFFFF
    // read-lower 0xFFFFFFFFCCCCCCCC
    //
    // Writer-thread
    // 0xEEEEEEEECCCCCCCC
    //

    public static void main(String[] args) throws InterruptedException {
        Runnable counter = () -> {
            while (true) {
                switch (state) {
                    case RUNNING:
                        ++value;
                        break;

                    case PAUSED:
                        break;

                    case STOPPED:
                        return;
                }
            }
        };

        Thread counterThread = new Thread(counter);
        state = ThreadState.RUNNING;
        counterThread.start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(counterThread.getState());

        System.out.println("Value before pause = " + value);
        state = ThreadState.PAUSED;
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Value after pause = " + value);

        state = ThreadState.STOPPED;
        System.out.println(counterThread.getState());

        System.out.println("Value end = " + value);
        System.out.println("Main end");
    }
}
