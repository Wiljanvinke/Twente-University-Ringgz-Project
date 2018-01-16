package game;

/**
 * Game board for the game Ringgz
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class Board {
	// variables
	private Field[] fields;
	public static final int DIM = 5;
	
	// constructor
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
<<<<<<< HEAD
    }
	
	public boolean isField(int row, int col) {
    	if (0 <= row && row < DIM && 0 <= col && col < DIM) {
    		return true;
    	} else {
    		return false;
    	}
    }
	
	public Field getField(int i) {
    	return fields[i];
    }
	
	public Field getField(int row, int col) {
    	return getField(index(row, col));
    }
	
	public boolean isEmptyField(int i) {
    	/*if (getField(i) == Mark.EMPTY) {
    		return true;
    	} else {
    		return false;
    	}*/
		return false;
    }
	
	public boolean isEmptyField(int row, int col) {
    	return isEmptyField(index(row, col));
    }
	
	public boolean isFull() {
    	for (int i = 0; i < DIM * DIM; i++) {
    		if (isEmptyField(i) == true) {
    			return false;
    		}
    	}
    	return true;
    }
	
	public void setField(int index, int size, Ring ring) {
    	
    }
	
	public void setField(int row, int col, int size, Ring ring) {
    	
    }
	
	public void reset() {
		
	}
	
	public String toString() {
		return "";
	}
	
=======
    }
>>>>>>> refs/heads/master
}
