package Main;

import Backend.BoardMethods.*;

import java.util.ArrayList;
import java.util.List;

public class Mode0 extends SudokuBoardService {
    private SudokuBoard board;

    public Mode0(SudokuBoard board) {
        this.board = board;
    }

    public List<String> boardValidate() {
        ArrayList<String> rowErrors = new ArrayList<>();
        ArrayList<String> columnErrors = new ArrayList<>();
        ArrayList<String> boxErrors = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (rowValidation(i) == false) {
                rowErrors.add("Error in row: " + (i + 1));
            }
        }
        for (int i = 0; i < 9; i++) {
            if (columnValidation(i) == false) {
                columnErrors.add("Error in Column: " + (i + 1));
            }
        }
        for (int i = 0; i < 9; i++) {
            if (boxValidation(i) == false) {
                boxErrors.add("Error in box: " + (i + 1));
            }
        }
        ArrayList<String> finalErrors = new ArrayList<>();
        finalErrors.addAll(rowErrors);
        finalErrors.addAll(columnErrors);
        finalErrors.addAll(boxErrors);
        return finalErrors;
    }


}
