package ru.job4j.concurrent.singleton;

public enum SingletonEnum {
    INSTANCE;

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        SingletonEnum tracker = SingletonEnum.INSTANCE;
    }
}
