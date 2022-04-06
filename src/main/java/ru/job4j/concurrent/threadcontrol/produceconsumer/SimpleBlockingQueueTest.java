package ru.job4j.concurrent.threadcontrol.produceconsumer;

public class SimpleBlockingQueueTest {

    public static void main(String[] args) {
        String balence = "as";
        SimpleBlockingQueue<String> stringSimpleBlockingQueue = new SimpleBlockingQueue<>();

        Thread first = new Thread(

                () -> {
                    System.out.println(Thread.currentThread().getName() + " stared");
                    stringSimpleBlockingQueue.poll();
                }
        );
        Thread second = new Thread(

                () -> {
                    stringSimpleBlockingQueue.poll();
                    stringSimpleBlockingQueue.offer(balence);
                }
        );
    }
}
