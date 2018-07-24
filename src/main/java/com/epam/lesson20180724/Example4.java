package com.epam.lesson20180724;

public class Example4<E> {

    private E[] arr = (E[]) new Object[10];

    public void add(int index, E el) {
        arr[index] = el;
    }

    public static void main(String[] args) {
        String[] strings = new String[3];
        strings[0] = "123";

        Object[] objects = strings;

        Integer[] integers = (Integer[]) objects;
        Integer integer = integers[0];

    }

}
