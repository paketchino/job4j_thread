package ru.job4j.concurrent.atomar;

public class Count {

    private int value;

    public void increment() {
        value++;
    }

    public int getValue() {
        return value;
    }
}
