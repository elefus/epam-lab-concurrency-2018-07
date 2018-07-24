package com.epam.lesson20180724;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Example2 {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
    }
}
