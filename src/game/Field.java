package game;

/**
 * A single field on a board
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class Field {
	
	// variables
	private Ring[] rings;
	
	/**
	 * Creates an empty field.
	 * Fields have 5 variables: the 4 ring sizes and a 5th field which determines if the field is occupied by a base
	 */
	public Field() {
		rings = new Ring[5];
		for (int i = 0; i < 5; i++) {
			rings[i] = Ring.EMPTY;
		}
	}
	
}
