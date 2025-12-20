package FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SudokuPanel extends JPanel {
    private final JTextField[][] cells = new JTextField[9][9];

    public SudokuPanel() {
        setLayout(new GridLayout(9, 9));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        initializeCells();
    }

    private void initializeCells() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("SansSerif", Font.BOLD, 20));

                // Add borders to simulate 3x3 blocks
                int top = (row % 3 == 0) ? 2 : 1;
                int left = (col % 3 == 0) ? 2 : 1;
                int bottom = (row == 8) ? 2 : 1;
                int right = (col == 8) ? 2 : 1;

                cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.GRAY));

                cells[row][col] = cell;
                add(cell);
            }
        }
    }

    public void setBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    cells[i][j].setText(String.valueOf(board[i][j]));
                    cells[i][j].setEditable(false);
                    cells[i][j].setBackground(new Color(240, 240, 240));
                    cells[i][j].setForeground(Color.BLACK);
                } else {
                    cells[i][j].setText("");
                    cells[i][j].setEditable(true);
                    cells[i][j].setBackground(Color.WHITE);
                    cells[i][j].setForeground(Color.BLUE);
                }
            }
        }
    }

    public interface BoardChangeListener {
        void onBoardChange();
    }

    private BoardChangeListener changeListener;

    public void setBoardChangeListener(BoardChangeListener listener) {
        this.changeListener = listener;
    }

    public int countEmptyCells() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].getText().trim().isEmpty()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int[][] getBoard() {
        int[][] board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = cells[i][j].getText().trim();
                if (!text.isEmpty() && text.matches("[1-9]")) {
                    board[i][j] = Integer.parseInt(text);
                } else {
                    board[i][j] = 0;
                }
            }
        }
        return board;
    }
}
