package ru.job4j.concurrent.thread.threadexample;


public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        another.start();
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getState());
    }
}
