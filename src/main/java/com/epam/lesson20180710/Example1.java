package com.epam.lesson20180710;

public class Example1 {

    public static void main(String[] args) {



        int a = 42;
        int b = 42;
        int c = a + b + (10 * 2);
        method2();

        // new String = 0xEEEFFF00
        // System.out = 0xFFAA3344
        // "hell"     = 0x00011122

        //
        // _  <- operand stack

        Class<String> stringClass = String.class;
        System.out.println(new String("hell"));
    }

    public static void method2() {

    }
}
