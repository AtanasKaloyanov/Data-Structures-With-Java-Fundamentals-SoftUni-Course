import implementations.TheMatrix;

public class Main {
    public static void main(String[] args) {
        char[][] matrix = {
                {'o', 'o', 'o', 'o', 'o', 'o'},
                {'o', 'o', 'o', '1', 'o', 'o'},
                {'o', 'o', '1', 'o', '1', '1'},
                {'o', '1', '1', 'w', '1', 'o'},
                {'1', 'o', 'o', 'o', 'o', 'o'}
        };

        TheMatrix theMatrix = new TheMatrix(matrix, 'x', 0, 0);

        theMatrix.solve();

        System.out.println(theMatrix.toOutputString());
    }
}

