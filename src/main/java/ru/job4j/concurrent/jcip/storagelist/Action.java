package ru.job4j.concurrent.jcip.storagelist;

public interface Action {

    boolean add(User user);
    boolean update(User user);
    boolean delete(User user);
}
