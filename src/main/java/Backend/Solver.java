package Backend;

public class Solver {

    public Solver() {
    }

    public static boolean solve(int[][] board) {

        // Get the 5 empty cell positions as Flyweight objects
        SudokuGame game = new SudokuGame(board);
        Flyweightcell[] emptyCells = findEmptyCells(board);

        CombinationIterator iterator = new CombinationIterator();
        GameStateVerifier validator = new GameStateVerifier();

        while (iterator.hasNext()) {

            int[] values = iterator.next();

            // Fill the empty cells 
            for (int i = 0; i < 5; i++) {
                int row = emptyCells[i].getrow();
                int col = emptyCells[i].getcol();
                board[row][col] = values[i];
            }

            // Validate the full board
            if (GameStateVerifier.getBoardState(game)
                    == GameStateVerifier.BoardState.VALID) {
                return true;
            }

            // Reset cells to 0
            for (int i = 0; i < 5; i++) {
                int row = emptyCells[i].getrow();
                int col = emptyCells[i].getcol();
                board[row][col] = 0;
            }
        }

        // No valid solution found
        return false;
    }
}
    


	



