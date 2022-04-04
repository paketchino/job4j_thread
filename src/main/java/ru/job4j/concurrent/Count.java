package ru.job4j.concurrent;

public class Count {

    private int value;

    public synchronized void increment() {
        value++;
    }

    public synchronized int getValue() {
        return value;
    }

    public void incrementSecond() {
        synchronized (this) {
            value++;
        }
    }

    public int getValueSecond() {
        synchronized (this) {
            return value;
        }
    }
}
