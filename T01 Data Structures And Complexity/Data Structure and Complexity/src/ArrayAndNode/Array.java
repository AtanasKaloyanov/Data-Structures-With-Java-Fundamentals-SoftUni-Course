package ArrayAndNode;

public class Array {
    private static int[] array = new int[5];

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {

            if (i == array.length) {
                array = grow();
            }

            array[i] = i + 5;
        }

        for (int currentElement : array) {
            System.out.print(currentElement + " ");
        }
    }

    public static int[] grow() {
        int[] newArray = new int[array.length * 2];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }
}
