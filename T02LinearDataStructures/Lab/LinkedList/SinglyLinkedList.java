package T02LinearDataStructures.Lab.LinkedList;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E>{
    private Node<E> head;
    private int size;

    private static class Node<E> {
        private Node<E> next;
        private E element;

        Node(E value) {
            this.element = value;
        }
    }
    @Override
    public void addFirst(E element) {

    }

    @Override
    public void addLast(E element) {

    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E getFirst() {
        return this.head.element;
    }

    @Override
    public E getLast() {
        Node<E> current = this.head;

        while(current.next != null) {
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
