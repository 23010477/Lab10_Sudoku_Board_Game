package Backend;

public class GameDriver {
    private final GameStorage gameStorage;

    public GameDriver(){
        this.gameStorage=new GameStorage();
    }
    public void gameDrive(SudokuGame sudokuGame) {
        if(GameStateVerifier.getBoardState(sudokuGame)!=GameStateVerifier.BoardState.VALID) {
            throw new IllegalArgumentException("Solved Sudoku is invalid or incomplete");
        }
            gameStorage.generateAndStore(sudokuGame.arrayBoardCopy());


    }
}
