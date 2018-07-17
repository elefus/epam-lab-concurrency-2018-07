package com.epam.lesson20180713;

public class Example1 {

    private static volatile long value = 0L;

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        Runnable inc = () -> {
            for (int i = 0; i < 1_000_000; ++i) {
                // critical section
                synchronized (lock) {
                    ++value;
                    // call.method() <- throw new RuntimeException();
                }
                // read
                // modify
                // store
//                value = value + 1;
            }
        };

        Runnable dec1 = () -> {
            for (int i = 0; i < 1_000_000; ++i) {
                synchronized (lock) {
                    --value;
                }
            }
        };

        Thread incThread = new Thread(inc);
        Thread dec1Thread = new Thread(dec1);
        Thread dec2Thread = new Thread(dec1);

        dec1Thread.start();
        dec2Thread.start();
        incThread.start();

        incThread.join();
        dec1Thread.join();
        dec2Thread.join();

        System.out.println(value);
    }

    // race-condition
    //   INC                     value                   DEC
    //                             0
    //    0          <- read       0
    //    0                        0        read ->       0
    //    1             modify     0        modify       -1
    //    1             store   -> 1                     -1
    //                            -1   <-   store        -1

    // biased
    //
    //                           object
    //                  <-  *      0
    //    0          <- read       0 ->
    //    1             modify     0 ->
    //    1             store   -> 1 ->
    //                  * ->       1 * ->
    //                             1        read ->       1
    //                             1        modify        0
    //                             0    <-  store         0
    //                             0 <-  *
}
