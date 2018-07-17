package com.epam.lesson20180717.philosophers;

public class Philosopher extends Thread {

    private int sectorIndex;

    public Philosopher(String name) {
        super(name);
    }

    public void setSectorIndex(int sectorIndex) {
        this.sectorIndex = sectorIndex;
    }

    public int getSectorIndex() {
        return sectorIndex;
    }

    public void inviteTo(Table table) {
        start();
    }
}
