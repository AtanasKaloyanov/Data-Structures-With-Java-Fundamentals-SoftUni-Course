package T02LinearDataStructures.Lab.ArrayList.Examples;

import T02LinearDataStructures.Lab.ArrayList.ArrayList;
import T02LinearDataStructures.Lab.ArrayList.List;

public class Test2 {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();

        numbers.add(1);
        numbers.forEach((element) -> {
            System.out.println(element);
        });
    }
}
