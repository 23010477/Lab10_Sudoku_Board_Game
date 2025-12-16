package Backend.BoardMethods;

public class SudokuGame {
    private SudokuBoard sudokuBoard;
    private int emptyCells;

    public SudokuGame(SudokuBoard sudokuBoard){
        this.sudokuBoard = sudokuBoard;
        emptyCells = emptyCellCounter();
    }

    public SudokuGame(int[][] sudokuBoard){// extra 3a4an yeb2a 3ndoko tare2tean n3mel game
        this.sudokuBoard = new SudokuBoard(sudokuBoard);
        emptyCells = emptyCellCounter();
    }

    private int emptyCellCounter(){ //m4 7ate7tago t3melolha call ,
        int count = 0;              //7a3melha call fl constructor mara wa7da fl 2wel w el updates 7ateb2a 3ndi hena
                                    //otherwise the count might get messed up

        int[][] board = sudokuBoard.getBoard();
        for (int i = 0; i<9; i++){
            for(int j=0; j<9;j++){
                if(board[i][j]==0){
                    count++;
                }
            }
        }
        return count;
    }

    public int getEmptyCellCount(){
        return emptyCells;
    }








}
