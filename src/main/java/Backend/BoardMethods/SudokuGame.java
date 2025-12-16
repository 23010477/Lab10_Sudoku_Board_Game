package Backend.BoardMethods;

public class SudokuGame {
    private SudokuBoard sudokuBoard;
    private int emptyCells;

    public SudokuGame(SudokuBoard sudokuBoard){
        this.sudokuBoard = sudokuBoard;
    }

    public SudokuGame(int[][] sudokuBoard){// extra 3a4an yeb2a 3ndoko tare2tean n3mel game
        this.sudokuBoard = new SudokuBoard(sudokuBoard);

    }

    public int emptyCellCounter(){
        int count = 0;
        return count;
    }








}
