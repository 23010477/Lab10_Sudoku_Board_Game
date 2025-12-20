package Main;

import java.io.IOException;

public interface Controllable {
    boolean[] getCatalog();

    int[][] getGame(char level);

    void driveGames(String sourcePath);

    // A boolean array which says if a specifc cell is correct or invalid
    boolean[][] verifyGame(int[][] game);

    // contains the cell x, y and solution for each missing cell
    int[][] solveGame(int[][] game);

    // Logs the user action
    void logUserAction(UserAction userAction) throws IOException;
}
