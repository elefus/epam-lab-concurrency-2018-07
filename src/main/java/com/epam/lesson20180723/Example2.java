package com.epam.lesson20180723;

import java.util.concurrent.*;

public class Example2 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Callable<Long> counter = () -> {
            long value = 0;
            for (long i = 0; i < Integer.MAX_VALUE; ++i) {
                ++value;
            }
            return value;
        };

        Future<Long> future1 = service.submit(counter);
        Future<Long> future2 = service.submit(counter);
        Future<Long> future3 = service.submit(counter);
        service.shutdown();

        TimeUnit.MILLISECONDS.sleep(500);

        future1.cancel(true);
        System.out.println(future1.isCancelled());
        try {
            Long result = future1.get();
            System.out.println(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
