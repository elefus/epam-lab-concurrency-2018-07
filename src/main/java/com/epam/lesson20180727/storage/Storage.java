package com.epam.lesson20180727.storage;

import lombok.SneakyThrows;
import org.omg.CORBA.INTERNAL;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Storage {

    private final Semaphore semaphore = new Semaphore(Integer.MAX_VALUE, true);
    private String value = "DEFAULT";

    @SneakyThrows
    public String read() {
        semaphore.acquire();

        try {
            TimeUnit.MILLISECONDS.sleep(1);
            return value;
        } finally {
            semaphore.release();
        }
    }

    @SneakyThrows
    public void write(String newValue) {
        semaphore.acquire(Integer.MAX_VALUE);
        TimeUnit.SECONDS.sleep(1);
        value = newValue;
        semaphore.release(Integer.MAX_VALUE);
    }
}
