package implementations;

import interfaces.AbstractQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {
    private List<E> elements;

    public PriorityQueue() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        heapifyUp(this.size() - 1);
    }



    private void heapifyUp(int index) {
        while (index > 0 && less(parentAt(getMiddleIndex(index)), this.elements.get(index))) {
            int middleIndex = getMiddleIndex(index);
            Collections.swap(this.elements, middleIndex, index);
            index = middleIndex;
        }
    }

    private int getMiddleIndex(int index) {
        return (index - 1) / 2;
    }

    private E parentAt(int index) {
        return this.elements.get(index);
    }

    private boolean less(E first, E second) {
        return first.compareTo(second) < 0;
    }

    @Override
    public E peek() {
        ensureNotEmpty();
        return this.elements.get(0);
    }

    @Override
    public E poll() {
        ensureNotEmpty();
        E elementForRemoving = this.elements.get(0);
        Collections.swap(this.elements, 0, this.size() - 1);
        this.elements.remove(size() - 1);
        this.heapifyDown(0);

        return elementForRemoving;
    }

    /*

   formula for left child index  (by given parentIndex)   -   int leftIndex =  (parentIndex * 2) + 1
   formula for right child index (by given parentIndex)   -   int rightIndex = (parentIndex * 2) + 2


   indices                                   0   1   2   3  4  5  6   7

 1. the number in heap                       25  17  16  9  5  8  15  6
 2.  swapping the first and the last         6   17  16  9  5  8  15  25
 3.  deleting the last                       6   17  16  9  5  8  15
 4   swapping until the element on the       17  6   16  9  5  8  15
 parentIndex is smaller than the element     17  9   16  6  5  8  15
 on the childIndex
                   25                                           6                                      6

           17              16                             17           16                      17              16

        9     5         8       15                  9         5      8    15              9         5       8      15

     6                                         25


                  17                                           17

          6              16                           9                  16

     9       5        8      15               6            5          8         15

     */

    private void heapifyDown(int index) {
        while (index < this.elements.size() / 2) {
            int childIndex = 2 * index + 1; // leftChild

            if (childIndex + 1 < this.elements.size() && less(this.elements.get(childIndex), this.elements.get(childIndex + 1))) {
                childIndex = childIndex + 1;     // rightChild
            }

            if (less(this.elements.get(childIndex), this.elements.get(index))) {
                break;
            }

            Collections.swap(this.elements, index, childIndex);
            index = childIndex;
        }
    }

    private void ensureNotEmpty() {
        if (this.size() == 0) {
            throw new IllegalStateException("The collection is empty.");
        }
    }
}
