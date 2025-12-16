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

//functions to "play" the game b2a 3yzeen ne3mel set l values fl 9x9 array , w n3mel check f kol set 3la el zeros
// assume el player can put a cell as 0/ empty .

public void setCellValue(int row, int col, int value){
        int[][] board = sudokuBoard.getBoard();
        int prevValue = board[row][col];
        board[row][col]= value;

        if (prevValue== 0 && value != 0){
            emptyCells--;
        }else if (prevValue != 0 && value ==0){ emptyCells++;}
}

public int getCellValue(int row, int col){  //bl marra..
        return sudokuBoard.getBoard()[row][col];
}



}
