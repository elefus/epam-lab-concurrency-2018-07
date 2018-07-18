package com.epam.lesson20180718;

import java.util.BitSet;
import java.util.HashMap;

public class Example1 {

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>(16, 0.75f);
        map.put(1, "a");
        map.put(3, "a");
        map.put(17, "a");
        map.put(33, "a");
        map.put(129, "a");
        map.put(65, "a");
        map.put(257, "a");
        map.put(513, "a");
        map.put(1025, "a");
        map.put(2049, "a");
        map.put(4097, "a");
        map.put(999, "a");
        System.out.println();
    }
}
