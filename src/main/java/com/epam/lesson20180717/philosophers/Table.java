package com.epam.lesson20180717.philosophers;

import lombok.Synchronized;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;

public class Table {

    private final List<Sector> sectors = new LinkedList<>();

    @Synchronized
    public void sit(Philosopher philosopher) {
        int lastSectorIndex = sectors.size();
        sectors.add(new Sector(lastSectorIndex, new Stick()));

        int philosopherIndex = lastSectorIndex + 1;
        sectors.add(new Sector(philosopherIndex, philosopher));
        philosopher.setSectorIndex(philosopherIndex);
    }

    public Stick getLeftStick(Philosopher philosopher) {
        return (Stick) sectors.get(philosopher.getSectorIndex() - 1).getObject();
    }

    public Stick getRightStick(Philosopher philosopher) {
        return (Stick) sectors.get((philosopher.getSectorIndex() + 1) % sectors.size()).getObject();
    }

    public Stick getStickWithLowestIndex(Philosopher philosopher) {
        int indexStick = Math.min(philosopher.getSectorIndex() - 1, (philosopher.getSectorIndex() + 1) % sectors.size());
        return (Stick) sectors.get(indexStick).getObject();
    }

    public Stick getStickWithHighestIndex(Philosopher philosopher) {
        int indexStick = Math.max(philosopher.getSectorIndex() - 1, (philosopher.getSectorIndex() + 1) % sectors.size());
        return (Stick) sectors.get(indexStick).getObject();
    }

    @Value
    private static class Sector {
        int index;
        Object object;
    }
}
