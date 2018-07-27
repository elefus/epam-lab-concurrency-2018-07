package com.epam.lesson20180726;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

public class Example2 {

    public static void main(String[] args) {
        AtomicReference<String> atomic;

    }
}

class Stack<T> {

    private AtomicReference<Node<T>> head = new AtomicReference<>();

    // T1 -> 2
    // ~1

    // T2 -> 3
    // ~1
    // ~2

    // 3
    // 2
    // 1
    // _

    public void push(T value) {
        Node<T> oldHead;
        Node<T> newHead = new Node<>(value);
        do {
            oldHead = head.get();
            newHead.next = oldHead;
        } while (!head.compareAndSet(oldHead, newHead));
    }

    public T pop() {
        Node<T> oldHead;
        Node<T> newHead;
        do {
            oldHead = head.get();
            if (oldHead == null) {
                throw new NoSuchElementException("Stack is empty");
            }
            newHead = oldHead.next;
        } while (!head.compareAndSet(oldHead, newHead));

        return oldHead.value;
    }

    private static class Node<T> {

        private T value;
        private Node<T> next;

        public Node() {
            this(null);
        }

        public Node(T value) {
            this.value = value;
            next = null;
        }
    }
}

