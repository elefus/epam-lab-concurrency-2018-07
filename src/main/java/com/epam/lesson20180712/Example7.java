package com.epam.lesson20180712;

public class Example7 {

    private static volatile long value = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable inc = () -> {
            for (int i = 0; i < 1_000_000; ++i) {
                // critical section
                {
                    ++value;
                }
                // read
                // modify
                // store
//                value = value + 1;
            }
        };

        Runnable dec = () -> {
            for (int i = 0; i < 1_000_000; ++i) {
                --value;
            }
        };



        Thread incThread = new Thread(inc);
        Thread decThread = new Thread(dec);

        decThread.start();
        incThread.start();

        incThread.join();
        decThread.join();

        System.out.println(value);
    }

    // race-condition
    //   INC                     value                   DEC
    //                             0
    //    0          <- read
    //                             0        read ->       0
    //    1             modify
    //                                      modify       -1
    //    1             store   -> 1
    //                            -1   <-   store        -1
}
