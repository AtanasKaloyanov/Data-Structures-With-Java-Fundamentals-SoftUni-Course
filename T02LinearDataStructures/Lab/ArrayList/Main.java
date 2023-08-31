package T02LinearDataStructures.Lab.ArrayList;


public class Main {

    public static void main(String[] args) {
        int[] array = new int[8];
        array[0] = 3;
        array[1] = 5;
        array[2] = 7;
        array[3] = 9;

        int index = 0;
        int element = 1;

        int lastElement = array[3];

        for (int i = 3; i > index; i--) {
             array[i] = array[i - 1];
        }

        array[index] = element;

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }

        System.out.println();
        System.out.println(lastElement);
    }
}
