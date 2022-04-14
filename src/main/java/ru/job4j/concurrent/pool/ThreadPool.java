package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.threadcontrol.produceconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool implements Runnable {

    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public void work(Runnable job) throws InterruptedException {
        while (tasks.isEmpty()) {
            wait();
        }
        for (int i = 0; i < size; i++) {
            System.out.println(Thread.currentThread().getName() + " work");
            threads.add(new Thread(job));
            tasks.offer(job);
        }
        for (Thread st : threads) {
            System.out.println(Thread.currentThread().getName() + " thread starting");
            st.start();
        }
    }

    public void shutdown() {
        for (Thread th : threads) {
            th.interrupt();
        }
    }

    @Override
    public void run() {
        while (Thread.currentThread().isInterrupted()) {
            try {
                System.out.println(Thread.currentThread().getName() + " running");
                tasks.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shutdown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread threadFirst = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(i);
                    }
                }
        );

        Thread threadSecond = new Thread(
                () -> {
                    for (int i = 11; i < 20; i++) {
                        System.out.println(i);
                    }
                }
        );
        Thread threadThird = new Thread(
                () -> {
                    for (int i = 21; i < 30; i++) {
                        System.out.println(i);
                    }
                }
        );

        ThreadPool threadPool = new ThreadPool();
        threadPool.work(threadFirst);
        threadPool.work(threadSecond);
        threadPool.work(threadThird);
        threadPool.run();
        threadPool.shutdown();
        Thread.currentThread().join();
    }
}
