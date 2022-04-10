package ru.job4j.concurrent.threadcontrol;

import ru.job4j.concurrent.threadcontrol.produceconsumer.SimpleBlockingQueue;

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(5);
        final Thread consumer = new Thread(
                () -> {
                    while (Thread.currentThread().isInterrupted()) {
                            try {
                                System.out.println(queue.poll());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                    }
                }
        );
        final Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        );
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        consumer.interrupt();
    }
}
