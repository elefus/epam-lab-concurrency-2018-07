package com.epam.lesson20180717;

import java.util.List;

public class Example1 {

    public static void main(String[] args) {

    }

    // T3
    private void anotherMethod(List<String> list) {
        // ...
        synchronized (list) {
            list.clear();
        }
    }

    // check and action

    // list = {"a", "b", "c"};

    // T1
    // T2
    public void process(List<String> list) {
        synchronized (list) {
            // T1
            // T2
            // check
            if (!list.isEmpty()) {
                // T1
                // T2
                System.out.println(list);

                // T1
                // T2
                // action
                System.out.println(list.get(0));

                // T1
                list.clear();
            }
        }
    }

}
