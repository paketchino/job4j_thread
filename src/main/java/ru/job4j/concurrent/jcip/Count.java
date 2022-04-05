package ru.job4j.concurrent.jcip;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Count {

    @GuardedBy("this")
    private int value;

    public synchronized void incrementFirst() {
        this.value++;
    }

    public synchronized int get() {
        return this.value;
    }

    public synchronized int incrementSecond() {
        return this.value++;
    }
}
