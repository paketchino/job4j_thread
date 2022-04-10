package ru.job4j.concurrent.linked;

public final class Node<T> {

    private Node<T> next;
    private final T value;

    public Node(T value) {
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
