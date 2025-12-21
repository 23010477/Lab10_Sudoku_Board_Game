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

    public interface BoardChangeListener {
        void onBoardChange();

        void onCellChange(int row, int col, int newValue, int oldValue);
    }

    private BoardChangeListener changeListener;
    private int tempOldValue = 0; // To store value on focus gain

    public void setBoardChangeListener(BoardChangeListener listener) {
        this.changeListener = listener;
    }

    private void initializeCells() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("SansSerif", Font.BOLD, 20));

                // Add borders to simulate 3x3 blocks
                int top = (row % 3 == 0) ? 3 : 1;
                int left = (col % 3 == 0) ? 3 : 1;
                int bottom = (row == 8) ? 3 : 1;
                int right = (col == 8) ? 3 : 1;

                cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                final int r = row;
                final int c = col;

                cell.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        String txt = cell.getText().trim();
                        tempOldValue = txt.isEmpty() ? 0 : Integer.parseInt(txt);
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        String txt = cell.getText().trim();
                        int newValue = txt.isEmpty() ? 0 : (txt.matches("[0-9]") ? Integer.parseInt(txt) : 0);
                        if (newValue != tempOldValue) {
                            if (changeListener != null) {
                                changeListener.onCellChange(r, c, newValue, tempOldValue);
                            }
                        }
                    }
                });

                cells[row][col] = cell;
                add(cell);
            }
        }
    }

    private int[][] initialBoard;

    public void setBoard(int[][] board) {
        // storage of initial state for styling
        initialBoard = new int[9][9];
        for (int i = 0; i < 9; i++)
            System.arraycopy(board[i], 0, initialBoard[i], 0, 9);

        renderBoard(board);
    }

    public void updateBoard(int[][] board) {
        renderBoard(board);
    }

    private void renderBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    cells[i][j].setText(String.valueOf(board[i][j]));
                } else {
                    cells[i][j].setText("");
                }

                // Styling logic based on INITIAL state
                if (initialBoard[i][j] != 0) {
                    cells[i][j].setEditable(false);
                    cells[i][j].setBackground(new Color(240, 240, 240));
                    cells[i][j].setForeground(Color.BLACK);
                } else {
                    cells[i][j].setEditable(true);
                    cells[i][j].setBackground(Color.WHITE);
                    cells[i][j].setForeground(Color.BLUE);
                }
            }
        }
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
