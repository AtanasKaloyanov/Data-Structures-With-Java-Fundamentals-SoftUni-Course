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
        Node<E> newNode = new Node<>(element);

           if (this.size == 0) {
               this.head = newNode;
           } else {
               Node<E> current =  this.head;
               while(current.next != null) {
                   current = current.next;
               }

               current.next = newNode;
           }

           this.size++;
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
        E element;

        if (this.size == 1) {
            element = this.head.element;
            this.head = null;
        } else {
            Node<E> previous = null;
            Node<E> current = this.head;

            while (current.next != null) {
                previous = current;
                current = current.next;
            }

            element = current.element;
            previous.next = null;
        }

        this.size--;
        return element;

//        ensureNotEmpty();
//        Node<E> current = this.head;
//        Node<E> previous = null;
//        E element;
//
//        while (current.next != null) {
//            previous = current;
//            current = current.next;
//        }
//
//        if (previous == null) {
//            element = removeFirst();
//        } else {
//            element = previous.next.element;
//            previous.next = null;
//        }
//
//        return element;
    }

    @Override
    public E getFirst() {
        ensureNotEmpty();
        return this.head.element;
    }

    @Override
    public E getLast() {
        ensureNotEmpty();
        E element;

        if (this.size == 1) {
            element = this.head.element;
        } else {
            Node<E> current = this.head;

            while(current.next != null) {
                current = current.next;
            }

            element = current.element;
        }

        return element;
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
