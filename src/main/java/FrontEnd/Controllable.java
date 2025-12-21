package FrontEnd;

import java.io.IOException;

public interface Controllable {
    boolean[] getCatalog();

    int[][] getGame(char level);

    void driveGames(String sourcePath);

    // Returns status string: Valid / Incomplete / Invalid with errors
    String verifyGame(int[][] game);

    // contains the cell x, y and solution for each missing cell
    int[][] solveGame(int[][] game);

    void undo(int[][] game);

    void updateCell(int row, int col, int value, int oldValue);

    // Logs the user action
    void logUserAction(UserAction userAction) throws IOException;

    // Save current game state
    void saveCurrentGame(int[][] board);

    // Clear current game state
    void clearCurrentGame();

    // Save current game with initial board state
    void saveCurrentGameWithInitial(int[][] currentBoard, int[][] initialBoard);

    // Get initial board state
    int[][] getInitialBoard();
}
