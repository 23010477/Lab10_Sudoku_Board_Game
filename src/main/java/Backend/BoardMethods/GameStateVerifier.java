package Backend.BoardMethods;
import Backend.BoardMethods.SudokuBoard;
import Main.Mode0;
import java.util.List;
public class GameStateVerifier {

    public enum BoardState{VALID,INVALID,INCOMPLETE}


    //bosso homa t2reeban kolohm 7ayeb2o static 3a4an el mwdoo3 mafhoo4 instances 2slun w ya3ni el calls 7ateb2a 2shal
    public static BoardState getBoardState(SudokuGame sudokuGame){

        Mode0 validator = new Mode0(sudokuGame.getSudokuBoard());
        List<String> errors = validator.boardValidate();

        if(!errors.isEmpty()){
            return BoardState.INVALID;
        }else if(sudokuGame.getEmptyCellCount()>0)
            return BoardState.INCOMPLETE;
        else{ return BoardState.VALID;}
    }

    public static boolean isColValid(SudokuGame game,int col){
        Mode0 mode0 = new Mode0(game.getSudokuBoard());
        return mode0.columnValidation(col);
    }
    public static boolean isRowValid(SudokuGame game,int col){
        Mode0 mode0 = new Mode0(game.getSudokuBoard());
        return mode0.rowValidation(col);
    }

    public static boolean isBoxValid (SudokuGame game,int box){
        Mode0 mode0 = new Mode0(game.getSudokuBoard());
        return mode0.boxValidation(box);
    }







}
