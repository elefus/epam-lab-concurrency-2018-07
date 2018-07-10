package com.epam.lesson20180710;

public class Example3 {

    public static void main(String[] args) {

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Hello world");
    }
}
