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
    private Map<Integer, User> userMap = new ConcurrentHashMap<>();

    public UserStorage(Map<Integer, User> userMap) {
        synchronized (UserStorage.class) {
            this.userMap = userMap;
        }
    }

    public Map<Integer, User> copy() {
        Map<Integer, User> copy = new ConcurrentHashMap<>();
        for (User u : userMap.values()) {
            copy.put(u.getId(), u);
        }
        return copy;
    }

    @Override
    public synchronized boolean add(User user) {
        return copy().put(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean update(User user) {
        return copy().replace(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
        return copy().remove(user.getId()) != null;
    }

    private boolean searchId(Predicate<Integer> predicate) {
       boolean rsl = false;
        for (User id : copy().values()) {
            if (predicate.test(id.getId())) {
                rsl = true;
            }
        }
        return rsl;
    }

    private boolean searchFromId(int fromId) {
        return searchId(id -> id.equals(fromId));
    }

    private boolean searchToldId(int told) {
        return searchId(id -> id.equals(told));
    }


    @Override
    public synchronized void transfer(int fromId, int told, int amount) {
        User userFromId = copy().values()
                .stream().filter(id -> searchFromId(fromId)).findFirst().orElse(null);
        User toldId = copy().values()
                .stream().filter(id -> searchToldId(told)).findFirst().orElse(null);
        if (userFromId.getAmount() >= amount) {
            userFromId.setAmount(userFromId.getAmount() - amount);
            toldId.setAmount(toldId.getAmount() + amount);
        }
    }

}
