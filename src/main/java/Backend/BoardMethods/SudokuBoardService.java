package Backend.BoardMethods;

import java.util.ArrayList;
import java.util.List;

public abstract class SudokuBoardService {
    private static final String PATH =

            "C:/Users/CozyAk/IdeaProjects/Lab9-Sodoku_solution_verifier/src/main/java/Backend/BoardMethods/test.txt";

    public boolean rowValidation(int index) {
        int[] row = SudokuBoard.getInstance(PATH).getRow(index);
        ArrayList<Integer> duplicatedValues = new ArrayList<>();
        ArrayList<String> duplicatedValuesLocation = new ArrayList<>();
        boolean checkedValidation = true;
        int length = row.length;
        try {
            for (int i = 0; i < length; i++) {
                if (row[i] == 0)
                    continue;
                for (int j = i + 1; j < length; j++) {
                    if (row[j] == 0)
                        continue;
                    if (row[i] == row[j]) {
                        checkedValidation = false;
                        duplicatedValues.add(row[i]);
                        duplicatedValuesLocation.add("" + i + "," + j + "");
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (checkedValidation == true) {
            return true;
        } else {
            System.out.println("Row: " + (index + 1) + " The case is " + checkedValidation
                    + " The duplicated values are " + duplicatedValues +
                    "At location of " + duplicatedValuesLocation);
            return false;
        }
    }

    public boolean columnValidation(int index) {
        int[] column = SudokuBoard.getInstance(PATH).getColumn(index);
        int length = column.length;
        ArrayList<Integer> duplicatedValues = new ArrayList<>();
        ArrayList<String> duplicatedValuesLocation = new ArrayList<>();
        boolean checkedValidation = true;
        try {
            for (int i = 0; i < length; i++) {
                if (column[i] == 0)
                    continue;
                for (int j = i + 1; j < length; j++) {
                    if (column[j] == 0)
                        continue;
                    if (column[i] == column[j]) {
                        checkedValidation = false;
                        duplicatedValues.add(column[i]);
                        duplicatedValuesLocation.add("" + i + "," + j + "");
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (checkedValidation == true) {
            return true;
        } else {
            System.out.println("Column: " + (index + 1) + " The case is " + checkedValidation
                    + " The duplicated values are " + duplicatedValues +
                    "At location of " + duplicatedValuesLocation);
            return false;
        }
    }

    public boolean boxValidation(int index) {
        SudokuBoard SD = SudokuBoard.getInstance(PATH);
        ArrayList<Integer> duplicatedValues = new ArrayList<>();
        ArrayList<String> duplicatedValuesLocation = new ArrayList<>();
        int[][] box = SD.get3x3Box(index);
        boolean checkedValidation = true;
        try {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (box[i][j] == 0)
                        continue;
                    for (int m = 0; m < 3; m++) {
                        for (int n = 0; n < 3; n++) {
                            if (box[m][n] == 0) {
                                continue;
                            }
                            if (i == m && j == n) {
                                continue;
                            }
                            if (box[i][j] == box[m][n]) {
                                checkedValidation = false;
                                duplicatedValues.add(box[i][j]);
                                duplicatedValuesLocation.add("" + i + "," + j + " & " + m + "," + n + "");

                            }
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if (checkedValidation == true) {
            return true;
        } else {
            System.out.println("Box: " + (index + 1) + " The case is " + checkedValidation
                    + " The duplicated values are " + duplicatedValues +
                    "At location of " + duplicatedValuesLocation);
            return false;
        }

    }

    public boolean boardValidation() {
        int[][] board = SudokuBoard.getInstance(PATH).getBoard();
        boolean checkedValidation = true;
        try {
            for (int i = 0; i < 9; i++) {
                if (!rowValidation(i)) {
                    checkedValidation = false;
                }
            }
            System.out.println("-------------------------------------");
            for (int j = 0; j < 9; j++) {
                if (!columnValidation(j)) {
                    checkedValidation = false;
                }
            }
            System.out.println("-------------------------------------");
            for (int m = 0; m < 9; m++) {
                if (!boxValidation(m)) {
                    checkedValidation = false;
                }
            }
            System.out.println("-------------------------------------");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (checkedValidation) {
            System.out.println("Valid");
            return true;
        } else {
            System.out.println("Invalid");
            return false;
        }
    }
}
