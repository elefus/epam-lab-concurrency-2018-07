package com.epam.lesson20180731;

import java.util.concurrent.RecursiveTask;

public class Example3 {
}

class FJPFindMax<T extends Comparable<? super T>> extends RecursiveTask<T> {

    private final T[] data;

    FJPFindMax(T[] data) {
        this.data = data;
    }

    @Override
    protected T compute() {
        return null;
    }
}
