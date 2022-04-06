package ru.job4j.concurrent.jcip.annotation;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    private final List<T> list;

    public SingleLockList(List<T> list) {
        synchronized (SingleLockList.class) {
            this.list = Collections.synchronizedList(list);
        }
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    public synchronized List<T> copy(List<T> list) {
        List<T> copyList = new ArrayList<>();
        for (T t : list) {
            copyList.add(t);
        }
        return copyList;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }

}
