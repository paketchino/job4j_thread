package ru.job4j.concurrent.jcip.storagelist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

@ThreadSafe
public class UserStorage implements Action, Transformer {

    @GuardedBy("this")
    private Map<Integer, User> userMap;

    public UserStorage(Map<Integer, User> userMap) {
        this.userMap = userMap;
    }

    public synchronized Map<Integer, User> copy() {
        Map<Integer, User> copy = new ConcurrentHashMap<>();
        for (User u : userMap.values()) {
            copy.put(u.getId(), u);
        }
        return copy;
    }

    @Override
    public synchronized boolean add(User user) {
        return userMap.put(user.getId(), new User(user.getId(), user.getAmount())) != null;
    }

    @Override
    public synchronized boolean update(User user) {
        return userMap.replace(user.getId(), new User(user.getId(), user.getAmount())) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
        return userMap.remove(user.getId()) != null;
    }

    @Override
    public synchronized void transfer(int fromId, int told, int amount) {
        if (copy().isEmpty()) {
            throw new IllegalArgumentException("Список пуст");
        }
        User userFromId = copy().get(fromId);
        User userToldId = copy().get(told);
        if (userFromId.getAmount() >= amount) {
            userFromId.setAmount(userFromId.getAmount() - amount);
            userToldId.setAmount(userToldId.getAmount() + amount);
        }
    }

}
