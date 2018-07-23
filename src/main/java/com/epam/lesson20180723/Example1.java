package com.epam.lesson20180723;

import java.io.IOException;
import java.util.concurrent.*;

public class Example1 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(4);

        Callable<Integer> counter = () -> {
            int value = 0;
            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                ++value;
            }
            throw new IOException("Something going wrong");
        };

        Future<Integer> future = service.submit(counter);
        service.shutdown();

        System.out.println("After submit");

        System.out.println(future.isDone());

        try {
            getWithException(service, future);
        } catch (IOException ex) {
            System.out.println("We expected this catch!");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private static void getWithException(ExecutorService service, Future<Integer> future) throws Throwable {
        try {
            service.awaitTermination(1, TimeUnit.DAYS);

            System.out.println(future.isDone());

            Integer result = future.get(1, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw e.getCause();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
