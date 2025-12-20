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

    private void setupGameUI(int[][] board) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        sudokuPanel = new SudokuPanel();
        sudokuPanel.setBoard(board);
        add(sudokuPanel, BorderLayout.CENTER);

        createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
        revalidate();
    }

    private void createControlPanel() {
        controlPanel = new JPanel();

        verifyBtn = new JButton("Verify");
        verifyBtn.addActionListener(e -> {
            boolean[][] res = controller.verifyGame(sudokuPanel.getBoard());
            // In a real app we'd highlight cells.
            JOptionPane.showMessageDialog(this, "Verification complete (Visual feedback stub).");
            try {
                controller.logUserAction(new UserAction("User Clicked Verify"));
            } catch (IOException ex) {
            }
        });

        solveBtn = new JButton("Solve");
        solveBtn.setEnabled(false); // Enable only if 5 cells left

        undoBtn = new JButton("Undo");

        controlPanel.add(verifyBtn);
        controlPanel.add(solveBtn);
        controlPanel.add(undoBtn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SudokuMainFrame().setVisible(true);
        });
    }
}
