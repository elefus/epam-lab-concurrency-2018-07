package com.epam.lesson20180709;

public class Example2 {

    // HEAP
    // NON-HEAP
    public static void main(String[] args) {
        A a = new A();

        System.out.println(a);


        int[] arr = new int[]{1, 2, 3};



        A[] arr1 = new A[]{new A(), new A(), new A()};

        /*
             [12 + 4 + {ref, ref, ref}]
                         |
                         [12 + 4 + 2 + 6]
                             [




         */

        System.out.println(arr[4]);
    }
}

// byte      1
// short     2
// int       4
// long      8
// float     4
// double    8
// char      2
// boolean   ? (1)
// reference ? (4 - 32-bit, 8 - 64-bit)

class A {

    // header
    // 8 - 32-bit
    // 12 - 64-bit
    private int field1;    // 4
    private short field2;  // 2
    // padding             // 6
}

// 6
// 8
// 24

class B {

}