package com.epam.lesson20180718.storage;

import java.util.concurrent.TimeUnit;

public class Storage {

    private String value = "DEFAULT";

    public String read() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }

    public void write(String newValue) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }
}
