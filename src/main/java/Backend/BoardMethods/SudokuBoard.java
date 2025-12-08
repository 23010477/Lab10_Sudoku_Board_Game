package Backend.BoardMethods;

public class SudokuBoard {
    private final int[][] board;
    private static SudokuBoard instance;

    private SudokuBoard(int[][] board) {
        this.board = board;
    }

    public synchronized static SudokuBoard getInstance(String filePath) {
        if (instance == null) {
            int[][] readBoard = new SudokuBoardReader().ReadBoardFromFile(filePath);
            instance = new SudokuBoard(readBoard);
        }
        return instance;
    }

    public int[] getRow(int index) {
        if (index >= 0 && index <= 8) {
            int[] rowCopy = new int[9];
            System.arraycopy(board[index], 0, rowCopy, 0, 9);
            return rowCopy;
        } else {
            throw new IllegalArgumentException("Invalid index");
        }

    }

    public int[] getColumn(int index) {
        if (index >= 0 && index <= 8) {
            int[] columnCopy = new int[9];
            for (int i = 0; i < 9; i++) {
                columnCopy[i] = board[i][index];
            }
            return columnCopy;
        } else {
            throw new IllegalArgumentException("Invalid index");
        }
    }

    public int[][] get3x3Box(int index) {
        if (index >= 0 && index <= 8) {
            int[][] box = new int[3][3];

            int startingRowIndex = (index / 3) * 3; // 7esbt soduko m3roofa la2etha lel boxes
            int startingColumnIndex = (index % 3) * 3;

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++) {
                    box[i][j] = board[startingRowIndex + i][startingColumnIndex + j];
                }
            return box;

        } else {
            throw new IllegalArgumentException("Invalid index");
        }

    }

    public int[][] getBoard() {

        return board;
    }

}
