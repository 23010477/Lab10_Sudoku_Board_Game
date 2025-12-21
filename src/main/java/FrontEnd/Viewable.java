package FrontEnd;

import java.io.IOException;

public interface Viewable {
    Backend.Catalog getCatalog();

    // Returns a random game with the specified difficulty
    // Note: the Game class is the representation of the soduko game in the
    // controller
    Backend.SudokuGame getGame(DifficultyEnum level);

    // Gets a sourceSolution and generates three levels of difficulty
    void driveGames(Backend.SudokuGame sourceGame);

    // Given a game, if invalid returns invalid and the locates the invalid
    // duplicates
    // if valid and complete, return a value
    // if valid and incomplete, returns another value
    // The exact repersentation as a string is done as you best see fit
    // Example for return values:
    // Game Valid -> "valid"
    // Game incomplete -> "incomplete"
    // Game Invalid -> "invalid 1,2 3,3 6,7"
    String verifyGame(Backend.SudokuGame game);

    // returns the correct combination for the missing numbers
    // Hint: So, there are many ways you can approach this, one way is
    // to have a way to map an index in the combination array to its location in the
    // board
    // one other way to to try to encode the location and the answer all in just one
    // int
    int[] solveGame(Backend.SudokuGame game);

    void undo(Backend.SudokuGame game);

    void updateCell(int row, int col, int value, int oldValue);

    // Logs the user action
    void logUserAction(String userAction) throws IOException;

    // Save current game state
    void saveCurrentGame(Backend.SudokuGame game);

    // Clear current game state
    void clearCurrentGame();

    // Save current game with initial board state
    void saveCurrentGameWithInitial(int[][] currentBoard, int[][] initialBoard);

    // Get initial board state
    int[][] getInitialBoard();
}
