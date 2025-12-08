package Main;

import Backend.BoardMethods.SudokuBoard;

import java.util.ArrayList;
import java.util.List;

//El CLI HENAA

public class VerifyMe {

    public static void main(String[] args) {
        if (args.length != 2) { // [0] is the mode , [1] is the filepath.
            System.out.println("Please Provide Valid Arguments!");
            System.out.println("VALID example: java VerifyMe <Mode> <FilePath>");
            System.out.println("Available Modes: 'ZeroHelpers' 'ThreeHelpers' 'TwentySevenHelpers'");
            return;
        }

        String validationMode = args[0];
        String filePath = args[1];

        SudokuBoard board = SudokuBoard.getInstance(filePath);

        int[][] printableBoard = board.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(printableBoard[i][j] + " ");
            }
            System.out.println();
        }

        Mode0 checker = new Mode0(board);
        System.out.println("\nValidation Results:");

        List<String> errors = checker.boardValidate();

        if (errors.isEmpty()) {
            System.out.println("Board is VALID ");
        } else {
            errors.forEach(System.out::println);
            System.out.println("Board is INVALID ");
        }

    }

}
