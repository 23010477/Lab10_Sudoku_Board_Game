package Backend;

public final class FlyweightcellFactory {

    private static final Flyweightcell[] emptyCells = new Flyweightcell[5];
    private static int index = 0;

    public static Flyweightcell getCell(int row, int col) {
        if (index >= 5) {
            throw new IllegalStateException("Only 5 flyweights allowed");
        }
        
        emptyCells[index] = new Flyweightcell(row, col);
        return emptyCells[index++];
    }
    
}