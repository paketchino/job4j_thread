package ru.job4j.concurrent.semaphore;

import java.util.concurrent.Semaphore;

 class DecThread implements Runnable {

    String name;
    Semaphore semaphore;

    public DecThread(String name, Semaphore semaphore) {
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
                Shared.count++;
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
        public static int count = 0;
    }


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        DecThread decThread = new DecThread("B", semaphore);
        decThread.run();
    }
}
