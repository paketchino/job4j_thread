package ru.job4j.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

public class Lock implements Runnable {

    String name;
    ReentrantLock lock;

    public Lock(String name, ReentrantLock lock) {
        this.name = name;
        this.lock = lock;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Запуск потока " + name);
        try {
            System.out.println("Lock");
            lock.lock();
            System.out.println("Пoтoк " + name + " блокирует счетчик.");
            Shared.count++;
            System.out.println("Пoтoк " + name + " ожидает ");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            System.out.println("Unlock");
            lock.unlock();
        }
    }
    static class Shared {
        static int count = 0;
    }

    public static void main(String[] args) {
        ReentrantLock lock1 = new ReentrantLock();
        new Lock("A", lock1);
        new Lock("B", lock1);
    }
}
