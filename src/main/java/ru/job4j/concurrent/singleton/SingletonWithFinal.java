package ru.job4j.concurrent.singleton;

public class SingletonWithFinal {

    private static final SingletonWithFinal INSTANCE = new SingletonWithFinal();

    private SingletonWithFinal() {
    }

    public static SingletonWithFinal getInstance() {
        return INSTANCE;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        SingletonWithFinal singleWithFinal = getInstance();
    }
}
