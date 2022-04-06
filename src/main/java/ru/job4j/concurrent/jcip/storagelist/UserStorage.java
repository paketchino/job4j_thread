package ru.job4j.concurrent.jcip.storagelist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage implements Action, Transformer {

    @GuardedBy("this")
    private List<User> storageList;

    public UserStorage(List<User> storageList) {
        synchronized (UserStorage.class) {
            this.storageList = new ArrayList<>(storageList);
        }
    }

    @Override
    public synchronized boolean add(User user) {
        return this.storageList.add(User.of(user.getId(), user.getAmount()));
    }

    @Override
    public synchronized boolean update(User user) {
        return this.storageList.add(User.of(user.getId(), user.getAmount()));
    }

    @Override
    public synchronized boolean delete(User user) {
        return this.storageList.remove(User.of(user.getId(), user.getAmount()));
    }


    @Override
    public synchronized void transfer(int fromId, int told, int amount) {
        User userFrom = this.storageList.stream()
                .filter(userFirst -> userFirst.getId() == fromId).findFirst().orElse(null);
        User userTo = this.storageList.stream()
                .filter(userSecond -> userSecond.getId() == told).findFirst().orElse(null);
        if (userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
        }
    }

}
