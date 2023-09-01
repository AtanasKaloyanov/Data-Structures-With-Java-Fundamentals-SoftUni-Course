package T02LinearDataStructures.Lab.Stack;

public interface AbstractStack<E> extends Iterable<E> {
     void push(E element);
     E pop();
     E peek();

     int size();

     boolean isEmpty();

}
