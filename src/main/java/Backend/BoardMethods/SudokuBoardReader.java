package Backend.BoardMethods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuBoardReader {

    public int[][] ReadBoardFromFile(String filename) {
        int[][] board = new int[9][9];

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {

            String fileLine;
            int row = 0;

            fileLine = bufferedReader.readLine();

            while (fileLine != null && row < 9) {
                String[] boardValues = fileLine.split(",");
                for (int column = 0; column < 9; column++) {
                    board[row][column] = Integer.parseInt(boardValues[column]);
                }
                row++;
                fileLine = bufferedReader.readLine();
            } // tm t3be2at el Array bnaga7

        } catch (IOException e) {
            e.printStackTrace();
        }

        return board;
    }

}
