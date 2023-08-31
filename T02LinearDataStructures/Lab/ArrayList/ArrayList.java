package T02LinearDataStructures.Lab.ArrayList;

import java.util.Arrays;
import java.util.Iterator;


public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 4;
    private Object[] elements;
    private int size;

    public ArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }


    @Override
    public boolean add(E element) {
        if (this.size == this.elements.length) {
            this.elements = grow();
        }

        this.size++;
        this.elements[this.size] = element;

        return true;
    }

    @Override
    public boolean add(int index, E element) {
        checkIndex(index);
        insert(index, element);
        return false;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return this.getElement(index);
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldElement = getElement(index);
        this.elements[index] = element;
        return oldElement;
    }

    @Override
    public E remove(int index) {
        this.checkIndex(index);
        E element = this.getElement(index);

        this.elements[index] = null;
        this.size--;
        shift(index);

        /*
 for (int i = index; i < this.size; i++) {
         this.elements[i] = this.elements[i + 1];
     }

     this.elements[this.size] = null;
        */

        ensureCapacity();

        /*
 if (this.size < this.elements.length / 3) {
            this.elements = shrink();
        }
         */

        return element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        int index = -1;

        for (int i = 0; i < this.elements.length; i++) {
            if (this.elements[i].equals(element)) {
                index = i;
                break;
            }
        }

        return index;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    private Object[] grow() {
        return Arrays.copyOf(this.elements, this.elements.length * 2);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Index out of bounds: %d for size %d", index, this.size));
        }
    }

    @SuppressWarnings("unchecked")
    private E getElement(int index) {
        return (E) this.elements[index];
    }

    public void insert(int index, E element) {
        if (this.size == this.elements.length) {
            this.elements = grow();
        }

        E lastElement = this.getElement(this.size - 1);

        for (int i = this.size - 1; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }

            this.elements[this.size] = lastElement;
            this.elements[index] = element;
            this.size++;
    }

    private Object[] shrink() {
        return Arrays.copyOf(this.elements, this.elements.length / 2);
    }

    private void shift(int index) {
     for (int i = index; i < this.size; i++) {
         this.elements[i] = this.elements[i + 1];
     }

     this.elements[this.size] = null;
    }

    private void ensureCapacity() {
        if (this.size < this.elements.length / 3) {
            this.elements = shrink();
        }
    }
}
