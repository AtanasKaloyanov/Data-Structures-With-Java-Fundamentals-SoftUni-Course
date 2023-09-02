package T02LinearDataStructures.Lab.LinkedList;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E> {
    private Node<E> head;
    private int size;

    private static class Node<E> {
        private Node<E> next;
        private E element;

        public Node(E value) {
            this.element = value;
        }
    }

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);

        if (this.head != null) {
            newNode.next = this.head;
        }

        this.head = newNode;
        this.size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(element);

        if (this.head == null) {
            this.head = newNode;
        } else {
            Node<E> current = this.head;
            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;
        }
        this.size++;
    }

    @Override
    public E removeFirst() {
        ensureNoEmpty();
        E element = this.head.element;

        if (this.size == 1) {
            this.head = null;
        } else {
            Node<E> newHead = this.head.next;
            this.head.next = null;
            this.head = newHead;
        }

        this.size--;
        return element;
    }

    @Override
    public E removeLast() {
        ensureNoEmpty();
        Node<E> current = this.head;
        Node<E> previous = null;
        E element;

        while (current.next != null) {
            previous = current;
            current = current.next;
        }

        if (previous == null) {
            element = removeFirst();
        } else {
            element = previous.next.element;
            previous.next = null;
        }

        return element;
    }

    @Override
    public E getFirst() {
        return this.head.element;
    }

    @Override
    public E getLast() {
        Node<E> current = this.head;

        while (current.next != null) {
            current = current.next;
        }

        return current.element;
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
            private Node<E> current = head;

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

    private void ensureNoEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("Illegal remove for empty LinkedList");
        }
    }
}
