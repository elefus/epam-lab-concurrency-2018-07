package com.epam.lesson20180719;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class Example1 {

    public static void main(String[] args) {
        Executor executor = new PerformInCurrentThreadExecutor();

        Runnable task = () -> {
            try {
                System.out.println("Before sleep");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("After sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        executor.execute(task);
        executor.execute(task);
        executor.execute(task);
    }
}

class PerformInCurrentThreadExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
