package ru.job4j.concurrent.semaphore;

import java.util.concurrent.Semaphore;

class IncThread implements Runnable {

    String name;
    Semaphore semaphore;

    public IncThread(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Запуск потока " + name);
        try {
            System.out.println("Поток " + name + " ожидает разрешения");
            semaphore.acquire();
            System.out.println("Поток " + name + " получает разрешение");
            for (int i = 0; i < 5; i++) {
                Shared.count--;
                System.out.println(name + " : " + Shared.count);
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("Поток " + name + " освобождает разрешение");
        semaphore.release();
    }

    static class Shared {
        static int count = 5;
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        IncThread decThread = new IncThread("A", semaphore);
        decThread.run();
    }
}
