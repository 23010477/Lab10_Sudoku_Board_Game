package FrontEnd;

import Backend.SudokuGame;
import Backend.SudokuBoard;
import java.io.IOException;

public class SudokuAdapter implements Controllable {

    private final Viewable controller;

    public SudokuAdapter(Viewable controller) {
        this.controller = controller;
    }

    @Override
    public boolean[] getCatalog() {
        Backend.Catalog c = controller.getCatalog();
        return new boolean[] { c.hasCurrentGame(), c.hasModes() };
    }

    @Override
    public int[][] getGame(char level) {
        DifficultyEnum diff = DifficultyEnum.EASY;
        if (level == 'm' || level == 'M')
            diff = DifficultyEnum.MEDIUM;
        if (level == 'h' || level == 'H')
            diff = DifficultyEnum.HARD;

        if (level == 'c' || level == 'C') {
            if (controller instanceof SudokuController) {
                return ((SudokuController) controller).getIncompleteGame().getSudokuBoard().getBoard();
            }
        }

        Backend.SudokuGame g = controller.getGame(diff);
        return g.getSudokuBoard().getBoard();
    }

    @Override
    public void driveGames(String sourcePath) {
        // Load Game from path
        java.io.File f = new java.io.File(sourcePath);
        if (!f.exists())
            throw new RuntimeException("File not found: " + sourcePath);

        // Basic loader
        int[][] b = new int[9][9];
        try (java.util.Scanner s = new java.util.Scanner(f)) {
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    if (s.hasNextInt())
                        b[i][j] = s.nextInt();
        } catch (Exception e) {
            throw new RuntimeException("Error reading file");
        }

        controller.driveGames(new SudokuGame(new SudokuBoard(b)));
    }

    @Override
    public String verifyGame(int[][] game) {
        return controller.verifyGame(new SudokuGame(new SudokuBoard(game)));
    }

    @Override
    public int[][] solveGame(int[][] game) {
        Backend.SudokuGame sGame = new SudokuGame(new SudokuBoard(game));
        int[] result = controller.solveGame(sGame);
        if (result.length > 0) {
            // Solved in place
            return sGame.getSudokuBoard().getBoard();
        }
        return new int[0][0]; // Failed
    }

    @Override
    public void undo(int[][] game) {
        controller.undo(new SudokuGame(new SudokuBoard(game)));
    }

    @Override
    public void updateCell(int row, int col, int value, int oldValue) {
        controller.updateCell(row, col, value, oldValue);
    }

    @Override
    public void logUserAction(UserAction userAction) throws IOException {
        controller.logUserAction(userAction.action);
    }

    @Override
    public void saveCurrentGame(int[][] board) {
        controller.saveCurrentGame(new SudokuGame(new SudokuBoard(board)));
    }

    @Override
    public void clearCurrentGame() {
        controller.clearCurrentGame();
    }

    @Override
    public void saveCurrentGameWithInitial(int[][] currentBoard, int[][] initialBoard) {
        controller.saveCurrentGameWithInitial(currentBoard, initialBoard);
    }

    @Override
    public int[][] getInitialBoard() {
        return controller.getInitialBoard();
    }
}
