package Backend;

import FrontEnd.RandomPairs;

import java.io.*;
import java.util.List;

public class GameStorage {
    public GameStorage() {
        createFolder("Games");
        createFolder("Easy");
        createFolder("Medium");
        createFolder("Hard");
        createFolder("Current");
    }

    private void createFolder(String fileName) {
        File folder = new File(fileName);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public Catalog getCatalog() {
        boolean hasCurrent = hasFiles("Current");
        boolean hasEasy = hasFiles("Easy");
        boolean hasMedium = hasFiles("Medium");
        boolean hasHard = hasFiles("Hard");

        return new Catalog(hasCurrent, hasEasy && hasMedium && hasHard);
    }

    private boolean hasFiles(String fileName) {
        File f = new File(fileName);
        File[] f1 = f.listFiles();
        return f1 != null && f1.length > 0;
    }

    public void generateAndStore(int[][] solvedBoard) {
        saveGame("Easy", removeCells(solvedBoard, 10));
        saveGame("Medium", removeCells(solvedBoard, 20));
        saveGame("Hard", removeCells(solvedBoard, 25));
    }

    private int[][] removeCells(int[][] board, int count) {

        int[][] copy = copyBoard(board);
        RandomPairs randomPairs = new RandomPairs();
        List<int[]> cells = randomPairs.generateDistinctPairs(count);

        for (int[] c : cells) {
            copy[c[0]][c[1]] = 0;
        }
        return copy;
    }

    private void saveGame(String fileName, int[][] board) {

        try {
            File f = new File(fileName + "/" + System.currentTimeMillis() + ".sdk");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(board);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("failed saving the game");
        }
    }

    public SudokuGame loadGame(String fileName) {

        File[] f = new File(fileName).listFiles();
        if (f == null || f.length == 0) {
            throw new RuntimeException("No game found");
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(f[0]));
            int[][] board = (int[][]) in.readObject();
            in.close();
            return new SudokuGame(board);
        } catch (Exception e) {
            throw new RuntimeException("Failed loading the game");
        }
    }

    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 9);
        }
        return copy;
    }

    public void saveCurrentGame(int[][] board) {
        // Clear existing files in Current folder first
        clearCurrentGame();
        // Save the current board to Current folder
        saveGame("Current", board);
    }

    public void saveCurrentGameWithInitial(int[][] currentBoard, int[][] initialBoard) {
        // Clear existing files in Current folder first
        clearCurrentGame();

        // Save current board
        try {
            File currentFile = new File("Current/current.sdk");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(currentFile));
            out.writeObject(currentBoard);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("failed saving current board");
        }

        // Save initial board
        try {
            File initialFile = new File("Current/initial.sdk");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(initialFile));
            out.writeObject(initialBoard);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("failed saving initial board");
        }
    }

    public int[][] loadInitialBoard() {
        try {
            File initialFile = new File("Current/initial.sdk");
            if (!initialFile.exists()) {
                return null; // No initial board saved
            }
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(initialFile));
            int[][] board = (int[][]) in.readObject();
            in.close();
            return board;
        } catch (Exception e) {
            return null;
        }
    }

    public void clearCurrentGame() {
        File currentFolder = new File("Current");
        if (currentFolder.exists() && currentFolder.isDirectory()) {
            File[] files = currentFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".sdk")) {
                        file.delete();
                    }
                }
            }
        }
    }
}