package implementations;

import interfaces.AbstractStack;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {
    private Node<E> top;
    private int size;

    private static class Node<E> {
        private Node<E> previous;
        private E element;

        public Node(E element) {
            this.element = element;
        }
    }

    public Stack() {

    }

    @Override
    public void push(E element) {
        Node<E> newTop = new Node<>(element);
        Node<E> oldTop = this.top;
        this.top = newTop;
        newTop.previous = oldTop;

        this.size++;
    }

    @Override
    public E pop() {
        ensureNotEmpty();

        Node<E> oldHead = this.top;
        Node<E> newHead = oldHead.previous;

        oldHead.previous = null;
        this.top = newHead;

        this.size--;

        return oldHead.element;
    }

    @Override
    public E peek() {
        ensureNotEmpty();
        return this.top.element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            Node<E> current = top;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E  element = current.element;
                current = current.previous;
                return element;
            }
        };
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException(String.format("The stack is empty."));
        }
    }
}
