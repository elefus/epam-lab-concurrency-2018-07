package com.epam.lesson20180726;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class Example6 {

    public static void main(String[] args) {
        LongAdder adder = new LongAdder();

        LongAccumulator accumulator = new LongAccumulator((left, right) -> left * right, 1);
        accumulator.accumulate(10);
        accumulator.accumulate(20);
        System.out.println(accumulator.getThenReset());
    }
}
