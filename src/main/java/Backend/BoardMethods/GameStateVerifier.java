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

        }
        return null;
    }
}
