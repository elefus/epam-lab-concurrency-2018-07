package com.epam.lesson20180727;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Example8 {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);

        System.out.println(phaser.getPhase());
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                phaser.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        phaser.arrive();
        System.out.println();
        System.out.println(phaser.getPhase());
        phaser.register();
        System.out.println(phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();

        phaser.arriveAndDeregister();
        System.out.println(phaser.getPhase());
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());

        phaser.arriveAndDeregister();

        System.out.println(phaser.isTerminated());

    }
}
