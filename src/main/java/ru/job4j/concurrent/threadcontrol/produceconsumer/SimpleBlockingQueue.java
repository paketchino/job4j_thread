package ru.job4j.concurrent.threadcontrol.produceconsumer;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("queue")
    private final Queue<T> queue = new LinkedList<>();

    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized int getSize() {
        return queue.size();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        T rsl = queue.poll();
        notifyAll();
        return rsl;
    }

}
