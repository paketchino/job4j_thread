package ru.job4j.concurrent.threadcontrol;

public class CountBarrier {

    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            count = count + total;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (this) {
            while (count != total && count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}