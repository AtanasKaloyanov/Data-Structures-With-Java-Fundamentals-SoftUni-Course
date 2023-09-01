package T02LinearDataStructures.Lab.LinkedList.Examples;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> numbers = new LinkedList<>();

        numbers.addFirst(1);
        numbers.addFirst(2);

        System.out.println(numbers);

        numbers.getFirst();
    }
}
