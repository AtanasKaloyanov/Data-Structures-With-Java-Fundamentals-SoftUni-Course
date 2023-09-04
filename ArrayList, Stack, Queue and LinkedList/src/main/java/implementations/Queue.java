package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {
    private Node<E> head;
    private int size;

    private static class Node<E> {
        private Node<E> next;
        private E element;

        public Node(E element) {
            this.element = element;
        }
    }

    public Queue() {

    }

    @Override
    public void offer(E element) {
        Node<E> newElement = new Node<>(element);

        if (this.size == 0) {
            this.head = newElement;
        } else {
            Node<E> current = this.head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newElement;
        }

        this.size++;
    }

    @Override
    public E poll() {
        ensureNotEmpty();
        Node<E> oldHead = this.head;
        Node<E> newHead = oldHead.next;

        oldHead.next = null;
        this.head = newHead;

        this.size--;
        return oldHead.element;
    }

    @Override
    public E peek() {
        ensureNotEmpty();
        return this.head.element;
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
                return current != null;
            }

            @Override
            public E next() {
                E element = current.element;
                current = current.next;
                return element;
            }
        };
    }


        // head         E element 1
        //              Node 2

        // Node 2       E element 2
        //              Node 3

        // Node 3       E element 3
        //               null

        private void ensureNotEmpty () {
            if (isEmpty()) {
                throw new IllegalStateException(String.format("The quee is empty."));
            }
        }

}
