package FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class SudokuMainFrame extends JFrame {

    private final Controllable controller;
    private SudokuPanel sudokuPanel;
    private JPanel controlPanel;
    private JButton verifyBtn;
    private JButton solveBtn;
    private JButton showSolutionBtn;
    private JButton undoBtn;
    private JButton backBtn;

    public SudokuMainFrame() {
        super("Sudoku Game - Lab 10");

        // Wire up Facade
        SudokuController realController = new SudokuController();
        this.controller = new SudokuAdapter(realController);

        // Initial Startup Check
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveGameAndExit();
            }
        });
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Show main menu instead of dialogs
        showMainMenu();
    }

    private void showMainMenu() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Create main menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        gbc.ipady = 20;

        // Title
        JLabel titleLabel = new JLabel("Sudoku Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 10, 30, 10);
        menuPanel.add(titleLabel, gbc);

        // Reset insets for buttons
        gbc.insets = new Insets(10, 10, 10, 10);

        // Easy Button
        gbc.gridy = 1;
        JButton easyBtn = new JButton("Easy");
        easyBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        easyBtn.addActionListener(e -> loadGame('e'));
        menuPanel.add(easyBtn, gbc);

        // Medium Button
        gbc.gridy = 2;
        JButton mediumBtn = new JButton("Medium");
        mediumBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        mediumBtn.addActionListener(e -> loadGame('m'));
        menuPanel.add(mediumBtn, gbc);

        // Hard Button
        gbc.gridy = 3;
        JButton hardBtn = new JButton("Hard");
        hardBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        hardBtn.addActionListener(e -> loadGame('h'));
        menuPanel.add(hardBtn, gbc);

        // Continue Button
        gbc.gridy = 4;
        JButton continueBtn = new JButton("Continue");
        continueBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        continueBtn.addActionListener(e -> {
            boolean[] catalogue = controller.getCatalog();
            boolean hasUnfinished = catalogue[0];

            if (hasUnfinished) {
                loadGame('c');
            } else {
                JOptionPane.showMessageDialog(this,
                        "No saved game found!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        menuPanel.add(continueBtn, gbc);

        add(menuPanel, BorderLayout.CENTER);

        setVisible(true);
        revalidate();
        repaint();
    }

    private void loadGame(char level) {
        try {
            // Clear Current folder when starting a new game (not resuming)
            if (level != 'c' && level != 'C') {
                controller.clearCurrentGame();
                int[][] board = controller.getGame(level);
                setupGameUI(board);
            } else {
                // Continuing a saved game - load both current and initial boards
                int[][] currentBoard = controller.getGame(level);
                int[][] initialBoard = controller.getInitialBoard();
                setupGameUI(currentBoard);
                if (initialBoard != null && sudokuPanel != null) {
                    sudokuPanel.setInitialBoard(initialBoard);
                    // Re-render to apply correct styling based on initial board
                    sudokuPanel.updateBoard(currentBoard);
                }
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Game not found: " + e.getMessage());
            showMainMenu(); // Return to menu if game load fails
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
            int emptyCount = sudokuPanel.countEmptyCells();

            // Check if exactly 5 empty cells
            if (emptyCount != 5) {
                JOptionPane.showMessageDialog(this,
                        "Solver requires exactly 5 empty cells!\nCurrent empty cells: " + emptyCount,
                        "Cannot Solve",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int[][] solved = controller.solveGame(b);
            if (solved.length == 9) {
                sudokuPanel.updateBoard(solved);
            } else {
                JOptionPane.showMessageDialog(this, "Could not solve configuration!");
            }
        });

        showSolutionBtn = new JButton("Show Solution");
        showSolutionBtn.addActionListener(e -> {
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

        backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> returnToMainMenu());

        controlPanel.add(verifyBtn);
        controlPanel.add(solveBtn);
        controlPanel.add(showSolutionBtn);
        controlPanel.add(undoBtn);
        controlPanel.add(backBtn);
    }

    private void returnToMainMenu() {
        // Save current game before returning to menu
        if (sudokuPanel != null) {
            int[][] currentBoard = sudokuPanel.getBoard();
            int[][] initialBoard = sudokuPanel.getInitialBoard();
            controller.saveCurrentGameWithInitial(currentBoard, initialBoard);
        }

        // Clear game UI and show main menu
        showMainMenu();
    }

    private void saveGameAndExit() {
        if (sudokuPanel != null) {
            int[][] currentBoard = sudokuPanel.getBoard();
            int[][] initialBoard = sudokuPanel.getInitialBoard();
            controller.saveCurrentGameWithInitial(currentBoard, initialBoard);
        }
        dispose();
        System.exit(0);
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
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SudokuMainFrame().setVisible(true);
        });
    }
}
