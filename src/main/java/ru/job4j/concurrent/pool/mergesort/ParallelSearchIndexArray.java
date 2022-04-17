package ru.job4j.concurrent.pool.mergesort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndexArray<T> extends RecursiveTask<Integer> {

    private final List<T> list;
    private final int from;
    private final int to;
    private final T el;

    public ParallelSearchIndexArray(List<T> list, int from, int to, T el) {
        this.list = list;
        this.from = from;
        this.to = to;
        this.el = el;
    }

    @Override
    public String toString() {
        return "ParallelSearchIndexArray{" + "list=" + list
                + ", from=" + from + ", to="
                + to + ", el=" + el + '}';
    }

    private static <T> T find(List<T> list, T el) {
        T result = null;
        for (T t : list) {
            if (t.equals(el)) {
                result = t;
                break;
            }
        }
        return result;
    }

    @Override
    protected Integer compute() {
        if (from == to) {
            return from;
        }
        int mid = (from + to) / 2;
        ParallelSearchIndexArray<T> left = new ParallelSearchIndexArray<>(
                list, from, mid, findIndex(list, el));
        ParallelSearchIndexArray<T> right = new ParallelSearchIndexArray<>(
                list, mid + 1, list.size(), findIndex(list, el));
        left.fork();
        right.fork();
        Integer rightE = right.join();
        Integer leftE = left.join();
        return rightE != -1 ? rightE : leftE;
    }

    public static <T> T findIndex(List<T> list, T el) {
        T result = null;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        if (list.size() < 10) {
            result = find(list, el);
        } else {
            forkJoinPool.invoke(new ParallelSearchIndexArray<>(list, 0, list.size(), el));
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> listInt = new ArrayList<>();
        listInt.add("Thread");
        listInt.add("Pool");
        ParallelSearchIndexArray<String> stringParallelSearchIndexArray =
                new ParallelSearchIndexArray<>(listInt, 0, listInt.size(), "Pool");
        System.out.println(stringParallelSearchIndexArray);
    }
}
