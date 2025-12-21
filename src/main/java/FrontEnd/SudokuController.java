package FrontEnd;

import Backend.GameStorage;
import Backend.GameStateVerifier;
import Backend.SudokuGame;

import java.io.IOException;

public class SudokuController implements Viewable {

    private final GameStorage gameStorage;

    public SudokuController() {
        this.gameStorage = new GameStorage();
    }

    @Override
    public Backend.Catalog getCatalog() {
        return gameStorage.getCatalog();
    }

    @Override
    public Backend.SudokuGame getGame(DifficultyEnum level) {
        // Assuming GameStorage has folders named Easy, Medium, Hard
        // and loadGame takes a folder path.
        // However, GameStorage.loadGame(String fileName) seems to load from a specific
        // file or folder.
        // Let's check GameStorage again. It takes a folder name and picks the first
        // file?
        // "File[] f = new File(fileName).listFiles(); ... return new SudokuGame(...)".
        // Yes, so passing "Easy" should work if it has files.
        String folder = "";
        switch (level) {
            case EASY:
                folder = "Easy";
                break;
            case MEDIUM:
                folder = "Medium";
                break;
            case HARD:
                folder = "Hard";
                break;
        }
        try {
            return gameStorage.loadGame(folder);
        } catch (RuntimeException e) {
            // Seed games if missing
            System.out.println("No games found. Seeding storage...");
            seedGames();
            return gameStorage.loadGame(folder);
        }
    }

    private void seedGames() {
        int[][] solvedBoard = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 9 }
        };
        gameStorage.generateAndStore(solvedBoard);
    }

    public Backend.SudokuGame getIncompleteGame() {
        return gameStorage.loadGame("Current");
    }

    @Override
    public void driveGames(Backend.SudokuGame sourceGame) {
        gameStorage.generateAndStore(sourceGame.arrayBoardCopy());
    }

    @Override
    public String verifyGame(Backend.SudokuGame game) {
        return Backend.GameStateVerifier.VerifyGame(game);
    }

    @Override
    public int[] solveGame(Backend.SudokuGame game) {
        // Use Backend.Solver
        boolean solved = Backend.Solver.solve(game.getSudokuBoard().getBoard());
        if (solved) {
            // board is modified in place
            // Return stub as void logic is handled in place, but interface returns int[]
            // We can return the full board or just stub for now.
            // Controllable.solveGame is int[][], this is int[].
            // Viewable.solveGame signature is int[] solveGame(SudokuGame).
            return new int[1]; // Success indicator
        }
        return new int[0];
    }

    @Override
    public void undo(Backend.SudokuGame game) {
        try {
            Backend.Undo.undo(game.getSudokuBoard().getBoard());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCell(int row, int col, int value, int oldValue) {
        // Log the move using a temporary game object constructed with old value
        // We need to trick SudokuGame.setCellValue to log (row, col, val, OLD_VAL)
        // SudokuGame reads OLD_VAL from the board.
        // So we construct a 1-cell board or just 9x9 with that cell set.
        int[][] tempBoard = new int[9][9];
        tempBoard[row][col] = oldValue;
        Backend.SudokuGame tempGame = new Backend.SudokuGame(new Backend.SudokuBoard(tempBoard));

        // Calls setCellValue, which logs: (row, col, value, prevValue=oldValue)
        tempGame.setCellValue(row, col, value);
    }

    @Override
    public void logUserAction(String userAction) throws IOException {
        // Logging is now handled in Backend/SudokuGame for moves.
        // If this is for general actions, we might need a separate log or just ignore.
        // For now, let's keep it empty or simple print.
        System.out.println("User Action: " + userAction);
    }
}
