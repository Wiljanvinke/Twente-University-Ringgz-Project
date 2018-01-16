package game;

/**
 * Game board for the game Ringgz
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class Board {
	//variables
	private Field[] fields;
	public static final int DIM = 5;
	
	//constructor
	public Board() {
		fields = new Field[DIM * DIM];
		for (int i = 0; i < DIM * DIM; i++) {
			fields[i] = new Field();
		}
	}
	
	public int index(int row, int col) {
    	return DIM * row + col;
	}
	
	public boolean isField(int index) {
    	if (index >= 0 && index < DIM * DIM) {
    		return true;
    	} else {
    		return false;
    	}
    }
}
