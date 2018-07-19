package com.epam.lesson20180718.storage;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Storage {

    private final Object readLock = new Object();
    private final Object writeLock = new Object();
    private String value = "DEFAULT";
    private int reading = 0;

    @SneakyThrows
    public String read() {
        synchronized (writeLock) {
            ++reading;
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1);
            return value;
        } finally {
            synchronized (readLock) {
                if (--reading == 0) {
                    readLock.notify();
                }
            }
        }
    }

    @SneakyThrows
    public void write(String newValue) {
        synchronized (writeLock) {
            synchronized (readLock) {
                while (reading != 0) {
                    readLock.wait();
                }
            }
            TimeUnit.SECONDS.sleep(1);
            value = newValue;
        }
    }
}
