package AverageAndWorstCase;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AverageCase {
    public static void main(String[] args) {
        int[] array = new Random()
                .ints()
                .limit(1000000)
                .toArray();

        int randomIndex = ThreadLocalRandom.current().nextInt(0, array.length);
        int randomElement = array[randomIndex];

        contains(array, randomElement);
    }

    public static boolean contains(int[] array, int number) {

        for (int i = 0; i < array.length; i++) {
            int currentElement = array[i];

            if (currentElement == number) {
                System.out.printf("The element is found on %d index.", i);
                return true;
            }

        }
        System.out.println("The element is not found.");
        return false;
    }
}
