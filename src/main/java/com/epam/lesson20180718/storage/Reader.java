package com.epam.lesson20180718.storage;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Reader extends Thread {

    private final Storage storage;

    public Reader(String name, Storage storage) {
        super(name);
        this.storage = storage;
    }

    @Override
    @SneakyThrows
    public void run() {
        for (int i = 0; i < 30; ++i) {
            String actualValue = storage.read();
            System.out.println(getName() + " read value: " + actualValue);
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
