package com.epam.lesson20180718.storage;

public class Launcher {

    public static void main(String[] args) {
        Storage storage = new Storage();

        new Writer(storage).start();

        for (int i = 0; i < 5; ++i) {
            new Reader("Reader-" + i, storage).start();
        }
    }
}
