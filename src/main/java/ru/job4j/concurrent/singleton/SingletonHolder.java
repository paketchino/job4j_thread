package ru.job4j.concurrent.singleton;

public class SingletonHolder {

    private SingletonHolder() {
    }

    public static SingletonHolder getInstance() {
        return Holder.INSTANCE;
    }

    public Item add(Item model) {
        return model;
    }

    private static final class Holder {
        private static final SingletonHolder INSTANCE = new SingletonHolder();
    }

    public static void main(String[] args) {
        SingletonHolder tracker = SingletonHolder.getInstance();
    }
}
