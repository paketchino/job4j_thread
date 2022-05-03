package ru.job4j.concurrent.singleton;

public class SingletonLazyLoad {

    private static SingletonLazyLoad instance;

    public SingletonLazyLoad() {
    }

    public static synchronized SingletonLazyLoad getInstance() {
        if (instance == null) {
            instance = new SingletonLazyLoad();
        }
        return instance;
    }

    public Item add(Item model) {
        return model;
    }


}
