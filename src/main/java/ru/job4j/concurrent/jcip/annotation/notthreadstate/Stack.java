package ru.job4j.concurrent.jcip.annotation.notthreadstate;

public class Stack<T> {

    private Node<T> head;

    public void push(T value) {
        Node<T> temp = new Node<>(value);
        if (head == null) {
            head = temp;
            return;
        }
        temp.next = head;
        head = temp;
    }

    public T poll() {
        Node<T> temp = head;
        if (temp == null) {
            throw new IllegalArgumentException("Stack is Empty");
        }
        head = temp;
        temp.next = null;
        return temp.value;
    }

    private class Node<T> {

        private Node<T> next;
        private final T value;

        public Node(T value) {
            this.value = value;
        }
    }

}
