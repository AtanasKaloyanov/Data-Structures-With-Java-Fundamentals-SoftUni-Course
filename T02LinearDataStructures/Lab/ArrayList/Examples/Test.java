package T02LinearDataStructures.Lab.ArrayList.Examples;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        // int size()
        System.out.println(numbers.size());

        // boolean isEmpty()
        System.out.println(numbers.isEmpty());

        // E get(int index)
        System.out.println(numbers.get(0));

        // E set(int index, E element)
        System.out.println(numbers.set(0, 10));
        numbers.forEach( (element) -> System.out.print(element + " "));

        // E remove(int index)
        System.out.println();
        System.out.println(numbers.remove(0));

        // void add(int index, E element)

        numbers.add(2, 4);
        numbers.forEach( (element) -> System.out.print(element + " "));

        // boolean contains(Object o)
        System.out.println();
        System.out.println(numbers.contains(20));

        // int indexOf(Object o)
        System.out.println(numbers.indexOf(4));
    }
}
