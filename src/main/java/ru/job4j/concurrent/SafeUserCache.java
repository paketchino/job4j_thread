package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class SafeUserCache {

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger integer = new AtomicInteger();


    public void add(User user) {
        users.put(integer.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        List<User> copy = new ArrayList<>();
        users.values().forEach(user -> copy.add(User.of(user.getName())));
        return copy;
    }
}
