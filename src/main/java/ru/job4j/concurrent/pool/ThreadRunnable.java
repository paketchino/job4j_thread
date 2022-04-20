package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.threadcontrol.produceconsumer.SimpleBlockingQueue;

public class ThreadRunnable implements Runnable {

    private Thread thread = null;
    private SimpleBlockingQueue taskQueue = null;
    private volatile boolean isStopped = false;

    public ThreadRunnable(SimpleBlockingQueue queue) {
        taskQueue = queue;
    }

    @Override
    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped()) {
            try {
                Runnable runnable = (Runnable) taskQueue.poll();
                runnable.run();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void doStop() {
        isStopped = true;
        this.thread.interrupt();
    }

    public boolean isStopped() {
        return isStopped;
    }

}
