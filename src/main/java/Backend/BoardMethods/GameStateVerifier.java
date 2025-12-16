package Backend.BoardMethods;
import Backend.BoardMethods.SudokuBoard;
import Main.Mode0;
import java.util.List;
public class GameStateVerifier {

    public enum BoardState{VALID,INVALID,INCOMPLETE}

    public static String VerifyGame(SudokuGame game){ // da zy el board state bas beyraga3 string messages
        Mode0 validator = new Mode0(game.getSudokuBoard());
        List<String> errors = validator.boardValidate();


        StringBuilder verificationResults = new StringBuilder();

        if(!errors.isEmpty()){
            verificationResults.append("INVALID, Errors are: ");
            for(String s:errors){
                verificationResults.append(s).append("\n");
            }
        }else if(game.getEmptyCellCount()>0){
            verificationResults.append("INCOMPLETE, Board has ").append(game.getEmptyCellCount()).append(" empty cells");

        }else {verificationResults.append("VALID");}
        return verificationResults.toString();
    }


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

    private static int[][] getBoardCopy(SudokuGame game){
        return game.arrayBoardCopy();
    }
    public static String getEmptyCellIndices(SudokuGame game){ //teb2o t3melo parse b2a
        StringBuilder indices = new StringBuilder();
        int[][] board = getBoardCopy(game);

        for(int row = 0; row <9; row++){
            for(int col = 0; col<9 ; col++){
                indices.append("(").append(row+1).append(",").append(col+1).append(")").append("\n");
            }
        }
        return indices.toString();
    }





}
