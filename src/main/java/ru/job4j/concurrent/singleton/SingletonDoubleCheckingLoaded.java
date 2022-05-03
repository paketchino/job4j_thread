package ru.job4j.concurrent.singleton;

public class SingletonDoubleCheckingLoaded {

    private static volatile SingletonDoubleCheckingLoaded instance;

    private SingletonDoubleCheckingLoaded() {

    }

    public static SingletonDoubleCheckingLoaded getInstance() {
        if (instance == null) {
            synchronized (SingletonDoubleCheckingLoaded.class) {
                if (instance == null) {
                    instance = new SingletonDoubleCheckingLoaded();
                }
            }
        }
        return instance;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        SingletonDoubleCheckingLoaded tracker = SingletonDoubleCheckingLoaded.getInstance();
    }
}
