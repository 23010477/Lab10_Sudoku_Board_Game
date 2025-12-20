package Backend;

public class Catalog {
    private boolean currentGame;
    private boolean modes;

public Catalog(boolean currentGame, boolean modes){
    this.currentGame=currentGame;
    this.modes=modes;
}

public boolean hasCurrentGame(){
    return currentGame;
}
public boolean hasModes(){
    return modes;
}
}
