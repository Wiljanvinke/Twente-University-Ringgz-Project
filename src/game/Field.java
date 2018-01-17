package game;

/**
 * A single field on a board.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class Field {
	
	// variables
	private Ring[] rings;
	public static final int DIM = 5;
	
	/**
	 * Creates an empty field.
	 * Fields have 5 variables: 
	 * the 4 ring sizes and a 5th field which determines if the field is occupied by a base
	 */
	public Field() {
		rings = new Ring[5];
		for (int i = 0; i < 5; i++) {
			rings[i] = null;
		}
	}

	public void setRing(Ring ring) {
		
	}
	
	public Ring getRing (int i) {
		return rings[i];
	}
	
	// checks if base can be placed
	public boolean isEmptyField() {
		for (int i = 0; i < DIM; i++) {
			if (rings[i] != null) {
				return false;
			}
		}
		return true;
    }
	
	public boolean isEmptySlot(int i) {
		if (rings[i] == null) {
			return true;
		}
		return false;
	}
	
	public boolean isFull() {
		for (int i = 0; i < DIM; i++) {
    		if (isEmptySlot(i)) {
    			return false;
    		}
		}
		return true;
    }
	
	
	
}
