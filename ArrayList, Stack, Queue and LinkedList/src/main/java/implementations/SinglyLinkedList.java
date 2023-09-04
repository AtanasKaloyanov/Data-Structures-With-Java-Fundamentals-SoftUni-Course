package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E> {
    private Node<E> head;
    private int size;

    private static class Node<E> {
        private Node<E> next;
        private E element;

        public Node(E element) {
            this.element = element;
        }
    }

    public SinglyLinkedList() {

    }

    @Override
    public void addFirst(E element) {
           Node<E> newHead = new Node<>(element);
           Node<E> oldHead = this.head;

           this.head = newHead;
           newHead.next = oldHead;
           this.size++;
    }

    @Override
    public void addLast(E element) {

    }

    @Override
    public E removeFirst() {
        ensureNotEmpty();
        Node<E> oldHead = this.head;
        Node<E> newHead = oldHead.next;
        this.head.next = null;
        this.head = newHead;

        this.size--;
        return oldHead.element;
    }

    @Override
    public E removeLast() {
        ensureNotEmpty();
        return null;
    }

    @Override
    public E getFirst() {
        ensureNotEmpty();
        return this.head.element;
    }

    @Override
    public E getLast() {
        ensureNotEmpty();
        return null;
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
            Node<E> current = head;

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public E next() {
                E element = this.current.element;
                this.current = this.current.next;
                return element;
            }
        };
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException(String.format("The LinkedList is empty."));
        }
    }
}
