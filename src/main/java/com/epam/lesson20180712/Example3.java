package com.epam.lesson20180712;

public class Example3 {

    public static void main(String[] args) {
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
//        System.out.println(mainGroup.getName());
//        System.out.println(mainGroup.getMaxPriority());
//        System.out.println(mainGroup.getParent());
//
//        System.out.println(mainGroup.activeCount());
//        System.out.println(mainGroup.activeGroupCount());
//
//
//        System.out.println(mainGroup.getParent().getParent());

        ThreadGroup myGroup = new ThreadGroup(mainGroup, "myGroup") {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Exception in thread from myGroup: " + e);
            }
        };

//        myGroup.interrupt();

        Thread thread = new Thread(myGroup, () -> {
            System.out.println("Hello from thread " + Thread.currentThread());
            throw new RuntimeException("Exception from sub-thread");
        });

        thread.start();
    }


}

