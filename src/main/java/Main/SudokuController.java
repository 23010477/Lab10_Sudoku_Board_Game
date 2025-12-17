package Main;

import java.io.*;
import java.util.*;

public class SudokuController implements Viewable {

    private final String BASE_PATH = "sudoku_data";

    public SudokuController() {
        new File(BASE_PATH + "/easy").mkdirs();
        new File(BASE_PATH + "/medium").mkdirs();
        new File(BASE_PATH + "/hard").mkdirs();
        new File(BASE_PATH + "/current").mkdirs();
    }

    @Override
    public Catalog getCatalog() {
        boolean unfinished = new File(BASE_PATH + "/current/game.txt").exists();
        boolean easy = hasFiles(BASE_PATH + "/easy");
        boolean medium = hasFiles(BASE_PATH + "/medium");
        boolean hard = hasFiles(BASE_PATH + "/hard");
        return new Catalog(unfinished, easy && medium && hard);
    }

    private boolean hasFiles(String path) {
        File f = new File(path);
        return f.exists() && f.isDirectory() && f.list() != null && f.list().length > 0;
    }

    @Override
    public Game getGame(DifficultyEnum level) throws NotFoundException {
        // Basic implementation: Pick random
        String path = BASE_PATH + "/" + level.toString().toLowerCase();
        File folder = new File(path);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0)
            throw new NotFoundException("No " + level + " games found.");

        File selected = files[new Random().nextInt(files.length)];
        return loadGame(selected);
    }

    public Game getIncompleteGame() throws NotFoundException {
        File f = new File(BASE_PATH + "/current/game.txt");
        if (!f.exists())
            throw new NotFoundException("No incomplete game.");
        return loadGame(f);
    }

    private Game loadGame(File f) {
        int[][] b = new int[9][9];
        try (Scanner s = new Scanner(f)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (s.hasNextInt())
                        b[i][j] = s.nextInt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Game(b);
    }

    @Override
    public void driveGames(Game sourceGame) throws SolutionInvalidException {
        // Assume sourceGame is valid full solution for now
        // Generate E/M/H
        generate(sourceGame, 10, "easy");
        generate(sourceGame, 20, "medium");
        generate(sourceGame, 25, "hard");
    }

    private void generate(Game src, int remove, String difficulty) {
        int[][] board = new int[9][9];
        for (int i = 0; i < 9; i++)
            System.arraycopy(src.board[i], 0, board[i], 0, 9);

        RandomPairs rp = new RandomPairs();
        List<int[]> holes = rp.generateDistinctPairs(remove);
        for (int[] h : holes) {
            board[h[0]][h[1]] = 0;
        }

        // Save
        String filename = "game_" + System.currentTimeMillis() + "_" + difficulty + ".txt";
        saveGame(new Game(board), new File(BASE_PATH + "/" + difficulty + "/" + filename));
    }

    public void saveGame(Game game, File file) {
        try (PrintWriter pw = new PrintWriter(file)) {
            for (int[] row : game.board) {
                for (int val : row)
                    pw.print(val + " ");
                pw.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String verifyGame(Game game) {
        // Check for 0s
        boolean incomplete = false;
        for (int[] r : game.board)
            for (int c : r)
                if (c == 0)
                    incomplete = true;
        if (incomplete)
            return "incomplete";

        // Check validity (Rows, Cols, 3x3)
        if (isValidSudoku(game.board))
            return "valid";
        return "invalid"; // Placeholder for specific error
    }

    private boolean isValidSudoku(int[][] board) {
        // Use Sets for validation or simple arrays
        for (int i = 0; i < 9; i++) {
            Set<Integer> row = new HashSet<>();
            Set<Integer> col = new HashSet<>();
            Set<Integer> box = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0 && !row.add(board[i][j]))
                    return false;
                if (board[j][i] != 0 && !col.add(board[j][i]))
                    return false;
                int r = 3 * (i / 3) + j / 3;
                int c = 3 * (i % 3) + j % 3;
                if (board[r][c] != 0 && !box.add(board[r][c]))
                    return false;
            }
        }
        return true;
    }

    @Override
    public int[] solveGame(Game game) throws InvalidGame {
        // Should implement the Permutation solver here
        // For now, return empty array implies no solution or just stub
        return new int[0];
    }

    @Override
    public void logUserAction(String userAction) throws IOException {
        File log = new File(BASE_PATH + "/current/log.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(log, true))) {
            pw.println(userAction);
        }
    }
}
