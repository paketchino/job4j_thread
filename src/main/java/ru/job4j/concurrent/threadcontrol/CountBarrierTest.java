package ru.job4j.concurrent.threadcontrol;

public class CountBarrierTest {

    public static void main(String[] args) {
        int total = 0;
        CountBarrier countBarrier = new CountBarrier(total);

        Thread threadFirst = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " stated");
                    countBarrier.count();
                }, "Thread One"
        );

        Thread threadSecond = new Thread(

                () -> {
                    System.out.println(Thread.currentThread().getName() + " stated");
                    countBarrier.count();
                }, "Thread Two"
        );

        Thread threadThird = new Thread(

                () -> {
                    System.out.println(Thread.currentThread().getName() + " stated");
                    countBarrier.await();
                },  "Thread Three"
        );

        threadFirst.start();
        threadSecond.start();
        threadThird.start();
    }
}
