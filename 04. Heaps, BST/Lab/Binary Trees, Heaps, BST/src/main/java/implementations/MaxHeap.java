package implementations;

import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {
     private List<E> elements;

     public MaxHeap() {
         this.elements = new ArrayList<>();
     }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
      this.elements.add(element);
      this.heapify(this.size() - 1);
    }

    private void heapify(int index) {
         while (hasParent(index) && less(parent(getParentIndex(index)), this.elements.get(index))) {
             int parentIndex = getParentIndex(index);
             Collections.swap(this.elements, parentIndex, index);
             index = parentIndex;
         }
    }

    private int getParentIndex(int index) {
         return (index - 1) / 2;
    }

    private E parent(int index) {
         return this.elements.get(index);
    }

    private boolean less(E first, E second) {
         return first.compareTo(second) < 0;
    }

    private boolean hasParent(int index) {
         return index > 0;
    }


    @Override
    public E peek() {
        if (this.size() == 0) {
            throw new IllegalStateException("The size of the elements is 0.");
        }

        return this.elements.get(0);
    }
}
