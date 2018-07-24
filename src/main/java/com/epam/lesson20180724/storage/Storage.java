package com.epam.lesson20180724.storage;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Storage {


    private final Lock readLock;
    private final Lock writeLock;
    private String value = "DEFAULT";

    public Storage() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    @SneakyThrows
    public String read() {
        readLock.lock();
        try {
            TimeUnit.MILLISECONDS.sleep(1);
            return value;
        } finally {
            readLock.unlock();
        }
    }

    @SneakyThrows
    public void write(String newValue) {
        writeLock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
            value = newValue;
        } finally {
            writeLock.unlock();
        }
    }
}
