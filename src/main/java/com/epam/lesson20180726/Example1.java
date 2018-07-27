package com.epam.lesson20180726;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Example1 {

    public static void main(String[] args) {
        AtomicReference<Double> atomicDouble = new AtomicReference<>(10d);

        Float floatValue = 5f;
    }


}

class AtomicFloat extends Number {

    private final AtomicInteger bits;

    public AtomicFloat() {
        this(0);
    }

    public AtomicFloat(float initialValue) {
        bits = new AtomicInteger(Float.floatToIntBits(initialValue));
    }

    public boolean compareAndSet(float expected, float newValue) {
        return bits.compareAndSet(Float.floatToIntBits(expected), Float.floatToIntBits(newValue));
    }

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return Float.intBitsToFloat(bits.get());
    }

    @Override
    public double doubleValue() {
        return 0;
    }
}


