package com.epam.lesson20180717.philosophers;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Waiter {

    private static final Table TABLE = new Table();
    private static volatile boolean dinnerStarted = false;

    public static void main(String[] args) throws InterruptedException {
        Thread waiter = Thread.currentThread();
        waiter.setName("Waiter");

        System.out.println("Creating philosophers");
        Philosopher mo = createPhilosopher("Mo");
        Philosopher lao = createPhilosopher("Lao");
        Philosopher kun = createPhilosopher("Kun");

        mo.inviteTo(TABLE);
        lao.inviteTo(TABLE);
        kun.inviteTo(TABLE);

        System.out.println("Ready to eating");
        TimeUnit.SECONDS.sleep(1);

        synchronized (TABLE) {
            dinnerStarted = true;
            TABLE.notifyAll();
        }


        for (int i = 0; i < 10; ++i) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Do u need something?");
        }
    }

    private static Philosopher createPhilosopher(String name) {
        return new Philosopher(name) {

            @Override
            @SneakyThrows
            public void run() {
                TABLE.sit(this);

                synchronized (TABLE) {
                    while (!dinnerStarted) {
                        TABLE.wait();
                    }
                }
//                while (!dinnerStarted);

                synchronized (TABLE.getStickWithLowestIndex(this)) {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (TABLE.getStickWithHighestIndex(this)) {
                        System.out.println(name + " start eating");
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println(name + " end eating");
                    }
                }
            }
        };
    }

}
