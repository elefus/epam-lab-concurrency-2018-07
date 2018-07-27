package com.epam.lesson20180726;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Example5 {

    public static void main(String[] args) {
        A ref1 = new A();
        A ref2 = new A();
        A ref3 = new A();

        AtomicMarkableReference<A> atomic = new AtomicMarkableReference<>(ref1, true);
        AtomicStampedReference<A> atomicStamped = new AtomicStampedReference<>(ref1, 0);
        atomicStamped.getStamp();

        ExecutorService service = Executors.newCachedThreadPool();

        service.submit(() -> {
            try {
                boolean[] wrapper = {true};
                A ref = atomic.get(wrapper);  // ref1 A
                TimeUnit.SECONDS.sleep(1);
                atomic.compareAndSet(ref, ref2, true, false); // ABA
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.submit(() -> {
            try {
                boolean[] wrapper = {true};
                TimeUnit.MILLISECONDS.sleep(100);
                A ref = atomic.get(wrapper);  // ref1
                System.out.println("2 = " + atomic.compareAndSet(ref, ref3, true, false)); // ref3 B
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.submit(() -> {
            try {
                boolean[] wrapper = {false};
                TimeUnit.MILLISECONDS.sleep(200);
                A ref = atomic.get(wrapper);  // ref3
                atomic.compareAndSet(ref, ref1, false, false);  // ref1 A
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.shutdown();


    }
}
