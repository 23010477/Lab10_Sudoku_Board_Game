package Backend;

import FrontEnd.RandomPairs;

import java.io.*;
import java.util.List;

public class GameStorage {
 public GameStorage(){
     createFolder("Games");
     createFolder("Easy");
     createFolder("Medium");
     createFolder("Hard");
     createFolder("Current");
 }
 private void createFolder(String fileName){
     File folder=new File(fileName);
     if(!folder.exists()){
         folder.mkdir();
     }
 }

 public Catalog getCatalog(){
     boolean hasCurrent= hasFiles("Current");
     boolean hasEasy= hasFiles("Easy");
     boolean hasMedium= hasFiles("Medium");
     boolean hasHard= hasFiles("Hard");

     return new Catalog(hasCurrent, hasEasy && hasMedium && hasHard);
 }

 private boolean hasFiles(String fileName){
    File f=new File(fileName);
    File[] f1=f.listFiles();
    return f1!=null && f1.length>0;
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
            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(f));
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
            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(f[0]));
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
}