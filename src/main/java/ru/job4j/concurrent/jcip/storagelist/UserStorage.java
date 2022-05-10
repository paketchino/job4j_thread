package ru.job4j.concurrent.jcip.storagelist;

import net.jcip.annotations.ThreadSafe;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage implements Action, Transformer {

    private final Map<Integer, User> userMap = new ConcurrentHashMap<>();

    @Override
    public synchronized boolean add(User user) {
        return userMap.put(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean update(User user) {
        return userMap.replace(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
        return userMap.remove(user.getId()) != null;
    }

    @Override
    public synchronized void transfer(int fromId, int told, int amount) {
        if (userMap.isEmpty()) {
            throw new IllegalArgumentException("Список пуст");
        }
        User userFromId = userMap.get(fromId);
        User userToldId = userMap.get(told);
        if (userFromId.getAmount() >= amount) {
            userFromId.setAmount(userFromId.getAmount() - amount);
            userToldId.setAmount(userToldId.getAmount() + amount);
        }
    }
}
