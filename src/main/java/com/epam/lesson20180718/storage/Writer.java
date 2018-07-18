package com.epam.lesson20180718.storage;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Writer extends Thread {

    private final Storage storage;

    public Writer(Storage storage) {
        super("Writer");
        this.storage = storage;
    }

    @Override
    @SneakyThrows
    public void run() {
        for (int i = 0; i < 10; ++i) {
            storage.write(String.valueOf(i));
            TimeUnit.SECONDS.sleep(3);
        }

    }
}
