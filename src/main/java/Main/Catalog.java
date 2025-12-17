package Main;

public class Catalog {
    // True if there is a game in progress, False otherwise.
    public boolean current;
    // True if there is atleast one game available
    // for each difficulty, False otherwise.
    public boolean allModesExist;

    public Catalog(boolean current, boolean allModesExist) {
        this.current = current;
        this.allModesExist = allModesExist;
    }
}
