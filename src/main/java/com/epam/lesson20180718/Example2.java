package com.epam.lesson20180718;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Example2 {

    public static void main(String[] args) throws InterruptedException {
        Storage storage = new Storage();

        Runnable task = () -> {
            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                storage.send();
            }
        };
        Thread thread = new Thread(task);
        thread.start();


        TimeUnit.MILLISECONDS.sleep(500);
        thread.stop();

        TimeUnit.MICROSECONDS.sleep(100);
        System.out.println(thread.getState());


//        new Thread(task).start();
//
//        TimeUnit.MICROSECONDS.sleep(10);
//
//        synchronized (storage) {
//            System.out.println(storage.getX());
//            System.out.println(storage.getY());
//        }
    }
}

// x + y = 100
class Storage {
    private volatile int x = 100;
    private volatile int y = 0;

    @SneakyThrows
    public synchronized void send() {
        try {
            --x;
            TimeUnit.SECONDS.sleep(1);
            ++y;
        } catch (Error error) {
            error.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public synchronized int[] getValues() {
        return new int[]{x, y};
    }
}
