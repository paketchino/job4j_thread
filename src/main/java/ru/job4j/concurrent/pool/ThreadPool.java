package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.threadcontrol.produceconsumer.SimpleBlockingQueue;
import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
    private SimpleBlockingQueue<Runnable> taskQueue = null;
    private List<ThreadRunnable> threads = new ArrayList<>();
    private volatile boolean isStopped = false;
    private final int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        taskQueue = new SimpleBlockingQueue<>(5);
        for (int i = 0; i < size; i++) {
            threads.add(new ThreadRunnable(taskQueue));
        }
        for (ThreadRunnable th : threads) {
            new Thread(th).start();
        }
    }
    public void work(Runnable job) throws InterruptedException {
        this.taskQueue.offer(job);
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void shutdown() {
        this.isStopped = true;
        for (ThreadRunnable thread : threads) {
            thread.doStop();
        }
    }

}
