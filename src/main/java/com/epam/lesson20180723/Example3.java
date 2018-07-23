package com.epam.lesson20180723;

import java.util.concurrent.*;

public class Example3 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(4);

        Future<Integer> future = service.submit(() -> {
            System.out.println("Runnable");
        }, 1);
        try {
            Integer taskNumber = future.get();
            System.out.println(taskNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
