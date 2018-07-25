package com.epam.lesson20180725;

import lombok.Value;

import java.util.concurrent.*;

public class Example5 {

    @Value
    static class DelayedBox implements Delayed {

        String destination;
        long deliveryTimeMillis;

        public DelayedBox(String destination, long delay, TimeUnit unit) {
            this.destination = destination;
            this.deliveryTimeMillis = System.currentTimeMillis() + unit.toMillis(delay);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(deliveryTimeMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(deliveryTimeMillis, o.getDelay(TimeUnit.MILLISECONDS));
        }
    }


    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        int numBoxes = 10;
        BlockingQueue<DelayedBox> desk = new DelayQueue<>();

        Runnable operator = () -> {
            try {
                for (int i = 0; i < numBoxes; ++i) {
                    System.out.println("Prepare the box number - " + i);
                    TimeUnit.SECONDS.sleep(1);
                    DelayedBox box = new DelayedBox(String.valueOf(i), 5, TimeUnit.SECONDS);

                    System.out.println("Trying put " + i + " on the desk");
                    desk.put(box);
                    System.out.println("Successfully put " + i + " on the desk!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable courier = () -> {
            try {

                for (int i = 0; i < numBoxes; ++i) {
                    System.err.println("Trying to take box form the desk");
                    DelayedBox box = desk.take();
                    System.err.println("Took the " + box + ", going to deliver");
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        service.submit(operator);
        service.submit(courier);
        service.shutdown();
    }
}
