package ru.job4j.concurrent.buffer;

public class Buffer {
    private StringBuilder sb = new StringBuilder();

    public synchronized void add(int value) {
        System.out.println(value);
        sb.append(value);
    }

    @Override
    public synchronized String toString() {
        return sb.toString();
    }
}
