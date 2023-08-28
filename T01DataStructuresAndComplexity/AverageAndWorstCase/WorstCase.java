package T01DataStructuresAndComplexity.AverageAndWorstCase;

import java.util.Random;

public class WorstCase {
    public static void main(String[] args) {
        int[] array = new Random()
                .ints()
                .limit(1000000)
                .map((currentElement) -> Math.abs(currentElement))
                .toArray();

        array[array.length - 1] = -1;

        contains(array, -1);
    }

    private static boolean contains(int[] array, int number) {
        for (int i = 0; i < array.length; i++) {
            int currentElement = array[i];

            if (currentElement == number) {
                System.out.printf("The element is found on %d index", i);
                return true;
            }
        }

        System.out.println("The element is not found");
        return false;
    }

}
