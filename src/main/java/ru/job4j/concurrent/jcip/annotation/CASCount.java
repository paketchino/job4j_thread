package ru.job4j.concurrent.jcip.annotation;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int told = 0;
        int nextValue = 0;
        do {
            told = get();
            nextValue = told++;
        } while (!count.compareAndSet(told, nextValue));
    }

    public int get() {
        return count.get();
    }
}
