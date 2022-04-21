package ru.job4j.concurrent.pool.mergesort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndexArray<T> extends RecursiveTask<Integer> {

    private final T[] arr;
    private final int from;
    private final int to;
    private final T el;

    public ParallelSearchIndexArray(T[] arr, int from, int to, T el) {
        this.arr = arr;
        this.from = from;
        this.to = to;
        this.el = el;
    }

    private int find10Length(T[] arr, T el) {
        int result = -1;
        for (int i = from; i < to; i++) {
            if (el.equals(arr[i])) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    protected Integer compute() {
        if ((from - to) <= 10) {
            return find10Length(arr, el);
        }
        int mid = (from + to) / 2;
        ParallelSearchIndexArray<T> leftSearch = new ParallelSearchIndexArray<>(arr, from, mid, el);
        ParallelSearchIndexArray<T> rightSearch = new ParallelSearchIndexArray<>(arr, mid + 1, to, el);
        leftSearch.fork();
        rightSearch.fork();
        int leftE = leftSearch.join();
        int rightE = rightSearch.join();
        return (rightE != -1) ? leftE : rightE;
    }

    public static <T> int findIndex(T[] arr, T el) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearchIndexArray<>(arr, 0, arr.length - 1, el));
    }

}
