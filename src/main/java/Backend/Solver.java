package Backend;

public class Solver {

    public Solver() {
    }

    public static boolean solve(int[][] board) {
        return solveBoard(board);
    }

    private static boolean solveBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int n = 1; n <= 9; n++) {
                        if (isValid(board, row, col, n)) {
                            board[row][col] = n;
                            if (solveBoard(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int[][] board, int row, int col, int number) {
        // Row and Column check
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == number || board[i][col] == number) {
                return false;
            }
        }

        // Box check
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == number) {
                    return false;
                }
            }
        }
        return true;
    }
}
