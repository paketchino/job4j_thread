package ru.job4j.concurrent.jcip.storagelist;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class User {

    @GuardedBy("this")
    private int id;
    private int amount;

    public synchronized static User of(int id, int amount) {
        User user = new User(id, amount);
        user.id = id;
        user.amount = amount;
        return user;
    }

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized int getAmount() {
        return amount;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized void setAmount(int amount) {
        this.amount = amount;
    }
}
