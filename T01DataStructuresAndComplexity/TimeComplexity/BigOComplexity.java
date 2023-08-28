package T01DataStructuresAndComplexity.TimeComplexity;

public class BigOComplexity {
    boolean contains(int[] numbers, int number) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == number) {
                return true;
            }
        }

        return false;
    }

    // O(f(n))

}
