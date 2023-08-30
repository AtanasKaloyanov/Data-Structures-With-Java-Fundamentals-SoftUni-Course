package T02LinearDataStructures.Lab.ArrayList;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        // size
        System.out.println(numbers.size());

        // isEmpty
        System.out.println(numbers.isEmpty());

        // get
        System.out.println(numbers.get(0));

        // set
        System.out.println(numbers.set(0, 10));
        numbers.forEach( (element) -> System.out.print(element + " "));


    }
}
