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

    private static <T> int find(List<T> list, T el) {
        int result = -1;
        for (T t : list) {
            if (el.equals(t)) {
                result = list.indexOf(t);
                break;
            }
        }
        return result;
    }

    public static <T> int findIndex(List<T> list, T el) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearchIndexArray<>(list, 0, list.size(), el));
    }

    @Override
    protected Integer compute() {
        if (list.size() < 10) {
            return find(list, el);
        }
        int mid = (from + to) / 2;
        ParallelSearchIndexArray<T> leftSearch = new ParallelSearchIndexArray<>(list, from, mid, el);
        ParallelSearchIndexArray<T> rightSearch = new ParallelSearchIndexArray<>(list, mid + 1, to, el);
        leftSearch.fork();
        rightSearch.fork();
        int leftE = leftSearch.join();
        int rightE = rightSearch.join();
        return rightE != -1 ? rightE : leftE;
    }

    @Override
    public String toString() {
        return "ParallelSearchIndexArray{" + "list=" + list
                + ", from=" + from + ", to="
                + to + ", el=" + el + '}';
    }

    public static void main(String[] args) {
        List<String> listInt = new ArrayList<>();
        listInt.add("Thread");
        listInt.add("Pool");
        ParallelSearchIndexArray<String> stringParallelSearchIndexArray =
                new ParallelSearchIndexArray<>(listInt, 0, listInt.size(), "Thread");
        System.out.println(stringParallelSearchIndexArray);
        System.out.println(findIndex(listInt, "Pool"));
        System.out.println(findIndex(listInt, "Thread"));
    }
}
