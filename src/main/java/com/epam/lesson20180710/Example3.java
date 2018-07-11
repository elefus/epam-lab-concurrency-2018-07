package com.epam.lesson20180710;

import java.util.concurrent.TimeUnit;

public class Example3 {

    public static void main(String[] args) throws InterruptedException {
//        int[][] arr = new int[1000][];

        StringBuilder[][] arr = new StringBuilder[1000][];

        for (int i = 0; i < arr.length; ++i) {
//            int[] ints = new int[100_000];
//            Thread.sleep(60 * 60 * 1000);
            TimeUnit.MILLISECONDS.sleep(20);

            StringBuilder[] builders = new StringBuilder[100_000];
            for (int j = 0; j < builders.length; ++j) {
                builders[j] = new StringBuilder();
            }

            System.out.println(builders);

            arr[i] = builders;
        }


        System.out.println(arr);


        System.out.println("Hello world");
    }
}
