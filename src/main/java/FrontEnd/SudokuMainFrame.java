package FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SudokuMainFrame extends JFrame {

    private final Controllable controller;
    private SudokuPanel sudokuPanel;
    private JPanel controlPanel;
    private JButton verifyBtn;
    private JButton solveBtn;
    private JButton undoBtn;

    public SudokuMainFrame() {
        super("Sudoku Game - Lab 10");

        // Wire up Facade
        SudokuController realController = new SudokuController();
        this.controller = new SudokuAdapter(realController);

        // Initial Startup Check
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Check Catalog
        boolean[] catalogue = controller.getCatalog();
        boolean hasUnfinished = catalogue[0];
        boolean hasAllModes = catalogue[1];

        if (hasUnfinished) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Unfinished game found. Continue?",
                    "Resume Game", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                loadGame('c'); // 'c' for current
            } else {
                showDifficultySelection(hasAllModes);
            }
        } else {
            showDifficultySelection(hasAllModes);
        }
    }

    private void showDifficultySelection(boolean hasAllModes) {
        promptDifficulty();
    }

    private void promptDifficulty() {
        String[] options = { "Easy", "Medium", "Hard" };
        int choice = JOptionPane.showOptionDialog(this,
                "Select Difficulty",
                "New Game",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (choice == 0)
            loadGame('e');
        else if (choice == 1)
            loadGame('m');
        else if (choice == 2)
            loadGame('h');
        else
            System.exit(0);
    }

    private void loadGame(char level) {
        try {
            int[][] board = controller.getGame(level);
            setupGameUI(board);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Game not found: " + e.getMessage());
        }
    }

    private void createControlPanel() {
        controlPanel = new JPanel();

        verifyBtn = new JButton("Verify");
        verifyBtn.addActionListener(e -> {
            String res = controller.verifyGame(sudokuPanel.getBoard());
            // In a real app we'd highlight cells.
            JOptionPane.showMessageDialog(this, res);
            try {
                controller.logUserAction(new UserAction("User Clicked Verify"));
            } catch (IOException ex) {
            }
        });

        solveBtn = new JButton("Solve");
        solveBtn.addActionListener(e -> {
            int[][] b = sudokuPanel.getBoard();
            int[][] solved = controller.solveGame(b);
            if (solved.length == 9) {
                sudokuPanel.updateBoard(solved);
            } else {
                JOptionPane.showMessageDialog(this, "Could not solve configuration!");
            }
        });

        undoBtn = new JButton("Undo");
        undoBtn.addActionListener(e -> {
            int[][] b = sudokuPanel.getBoard();
            controller.undo(b); // Modifies b in place via backend/file
            sudokuPanel.updateBoard(b); // Refresh UI
        });

        controlPanel.add(verifyBtn);
        controlPanel.add(solveBtn);
        controlPanel.add(undoBtn);
    }

    private void setupGameUI(int[][] board) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        sudokuPanel = new SudokuPanel();
        sudokuPanel.setBoard(board);
        sudokuPanel.setBoardChangeListener(new SudokuPanel.BoardChangeListener() {
            @Override
            public void onBoardChange() {
            }

            @Override
            public void onCellChange(int row, int col, int newValue, int oldValue) {
                controller.updateCell(row, col, newValue, oldValue);
            }
        });

        add(sudokuPanel, BorderLayout.CENTER);

        createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
        revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SudokuMainFrame().setVisible(true);
        });
    }
}
