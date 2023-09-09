package implementations;

import interfaces.AbstractReversedList;

import java.util.Arrays;
import java.util.Iterator;

public class ReversedList<E> implements AbstractReversedList<E> {
    private Object[] elements;
    private int size;
    private final static int INNITIAL_CAPACITY = 2;

    public ReversedList() {
        this.elements = new Object[INNITIAL_CAPACITY];
    }

    @Override
    public void add(E element) {
        if (this.size == this.elements.length) {
            this.elements = grow();
        }

        this.elements[this.size++] = element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public E get(int index) {
        ensureIndex(index);
        int realIndex = this.size - 1 - index;
        return getAt(realIndex);
    }

    @Override
    public E removeAt(int index) {
        ensureIndex(index);
//        int realIndex = this.size - 1 - index;
        int realIndex = index;
        E element = getAt(realIndex);
        shiftTo(realIndex);
        this.size--;
        return element;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            int index = size - 1;

            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public E next() {
                return getAt(index--);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private E getAt(int index) {
        return (E) this.elements[index];
    }

    private void ensureIndex(int index) {
        if (index < 0 || index >= this.elements.length) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
    }

    private Object[] grow() {
        return Arrays.copyOf(this.elements, this.elements.length * 2);
    }

    private void shiftTo(int index) {
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }

        this.elements[this.size - 1] = null;
    }

}
