package Backend;


public class Flyweightcell {
	private final int row;
	private final int col;
	
	public Flyweightcell(int row, int col) {
		this.row=row;
		this.col=col;
		
	}
	
	public int getrow() {
		return row;
	}
	
	public int getcol() {
		return col ;
	}
	
}

