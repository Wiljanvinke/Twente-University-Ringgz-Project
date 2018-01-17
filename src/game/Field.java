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

	/**
	 * Set a <code>Ring</code> to a certain slot in this field if the <code>Player</code>
	 * still has one such <code>Ring</code> and the slot is free.
	 * Otherwise does nothing.
	 * Also removes the <code>Ring</code> from the <code>Player</code>s inventory.
	 * @param ring the <code>Ring</code> the <code>Player</code> wants to place in this field
	 * @param player the <code>Player</code> that wants to play a <code>Ring</code>
	 */
	public void setRing(Ring ring, Player player) {
		if (player.hasRing(ring)) {
			if(isEmptySlot(ring.getSize().toInt())) {
				rings[ring.getSize().toInt()] = ring;
				player.removeRing(ring);
			}
		}
	}
	
	/**
	 * Returns the <code>Ring</code> in a certain slot of this <code>Field</code>.
	 * @param i the slot needed to check, where i is the size of the <code>Ring</code>
	 * @return the <code>Ring</code> in the slot
	 */
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
	
	/**
	 * Checks the size of the biggest <code>Ring</code> in this <code>Field</code> as an integer.
	 * @return the size of the biggest <code>Ring</code> in this <code>Field</code> as an integer.
	 * Returns -1 if the <code>Field</code> is empty.
	 */
	public int getBiggestInt() {
		for (int i = DIM - 1; i >= 0; i--) {
			if(!isEmptySlot(i)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Checks the size of the biggest <code>Ring</code> in this <code>Field</code> as a <code>Size</code>-object.
	 * @return the size of the biggest <code>Ring</code> in this <code>Field</code> as a <code>Size</code>-object.
	 * Returns null if the <code>Field</code> is empty.
	 */
	public Size getBiggest() {
		if (getBiggestInt() == -1) {
			return null;
		}
		return Size.toEnum(getBiggestInt());
	}
	
}
