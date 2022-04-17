package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.threadcontrol.produceconsumer.SimpleBlockingQueue;
import java.util.ArrayList;
import java.util.List;

public class ThreadPool implements Runnable {

    private Thread thread = null;
    private final SimpleBlockingQueue<Runnable> taskQueue = new SimpleBlockingQueue<>(5);
    private List<Thread> threads = new ArrayList<>();
    private boolean isStopped = false;
    private final int size = Runtime.getRuntime().availableProcessors();

    public void work(Runnable job) throws InterruptedException {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(job));
            this.taskQueue.offer(job);
        }
        for (Thread th : threads) {
            new Thread(th).start();
        }
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }

    public synchronized void shutdown() {
        this.isStopped = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public synchronized void waitUntilAllTasksFinished() {
        while (this.taskQueue.getSize() > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        thread = Thread.currentThread();
        while (!isStopped()) {
            try {
                Runnable runnable = (Runnable) taskQueue.poll();
                runnable.run();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
