package implementations;

public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {
        fillMatrix(this.startRow, this.startCol);
    }

    private void fillMatrix(int startRow, int startCol) {
        if (this.matrix[startRow][startCol] != this.toBeReplaced) {
            return;
        }

        this.matrix[startRow][startCol] = this.fillChar;

        if (startRow - 1 >= 0) {
            fillMatrix(startRow - 1, startCol);
        }

        if (startRow + 1 < this.matrix.length) {
            fillMatrix(startRow + 1, startCol);
        }

        if (startCol - 1 >= 0) {
            fillMatrix(startRow, startCol - 1);
        }

        if (startCol + 1 < this.matrix[startRow].length) {
            fillMatrix(startRow, startCol + 1);
        }
    }


    public String toOutputString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                sb.append(matrix[i][j]);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
