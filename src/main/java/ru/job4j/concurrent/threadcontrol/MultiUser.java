package ru.job4j.concurrent.threadcontrol;

public class MultiUser {

    public static void main(String[] args) throws InterruptedException {
        Barrier barrier = new Barrier();

        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() +  " stated");
                    barrier.on();
                },
                "Master"
        );

        Thread slave = new Thread(

                () -> {
                    barrier.check();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Salve"
        );

        master.start();
        slave.start();
    }

}
