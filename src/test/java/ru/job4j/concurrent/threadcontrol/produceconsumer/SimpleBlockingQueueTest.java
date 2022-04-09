package ru.job4j.concurrent.threadcontrol.produceconsumer;

import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SimpleBlockingQueueTest {

    @Test
    public void whenFirstThreadProducerThenSecondThreadConsumer() throws InterruptedException {
        final SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            simpleBlockingQueue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                    }
                }
                });
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            simpleBlockingQueue.poll();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(simpleBlockingQueue.getSize(), is(10));
    }

}