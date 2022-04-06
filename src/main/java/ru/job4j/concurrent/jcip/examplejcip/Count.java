package ru.job4j.concurrent.jcip.examplejcip;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Count {

    @GuardedBy("this")
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
