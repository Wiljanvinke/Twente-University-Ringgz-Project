package game;

import players.Player;

/**
 * Game board for the game Ringgz.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.2
 */
public class Board {
	private Field[] fields;
	public static final int DIM = 5;
	
	/**
	 * Create a new <code>Board</code>.
	 */
	public Board() {
		fields = new Field[DIM * DIM];
		for (int i = 0; i < DIM * DIM; i++) {
			fields[i] = new Field();
		}
	}

	/**
	 * Calculates the index of a given <code>Field</code> from their row and column number.
	 * @param row The row of the <code>Field</code>
	 * @param col The column of the <code>Field</code>
	 * @return The index belonging to the (row, column)-<code>Field</code>
	 */
	public int index(int row, int col) {
    	return DIM * row + col;
	}
	
	/**
	 * Checks if a <code>Field</code> with the given index exists on this <code>Board</code>.
	 * @param index The index of the <code>Field</code>
	 * @return true if 0 <= index < 25
	 */
	public boolean isField(int index) {
    	if (index >= 0 && index < DIM * DIM) {
    		return true;
    	} else {
    		return false;
    	}
    }
	
	/**
	 * Checks if a <code>Field</code> with the given 
	 * (row, column) exists on this <code>Board</code>.
	 * @param row The row of the <code>Field</code>
	 * @param col The column of the <code>Field</code>
	 * @return True if 0 <= row < 5 && 0 <= col < 5
	 */
	public boolean isField(int row, int col) {
    	if (0 <= row && row < DIM && 0 <= col && col < DIM) {
    		return true;
    	} else {
    		return false;
    	}
    }
	
	/**
	 * Returns the <code>Field</code> at the given index of this <code>Board</code>.
	 * @param i The index of the <code>Field</code>
	 * @return The <code>Field</code> at the given index
	 */
	public Field getField(int i) {
    	return fields[i];
    }
	
	/**
	 * Returns the index of a <code>Field</code> with the given row and column.
	 * @param row The row of the <code>Field</code>
	 * @param col The column of the <code>Field</code>
	 * @return The field at the given index
	 */
	public Field getField(int row, int col) {
    	return getField(index(row, col));
    }
	
	/**
	 * Places a <code>Ring</code> on the given <code>Field</code>.
	 * @param index The index of the <code>Field</code>
	 * @param color The <code>Color</code> of the <code>Ring</code> to be placed
	 * @param size The <code>Size</code> of the <code>Ring</code> to be placed
	 * @param player The <code>Player</code> placing the <code>Ring</code>
	 */
	public void setField(int index, Color color, Size size, Player player) {
		getField(index).placeRing(color, size, player);
    }
	
	/**
	 * Places a <code>Ring</code> on the given <code>Field</code>.
	 * @param row The row where the <code>Ring</code> will be placed
	 * @param col The column where the <code>Ring</code> will be placed
	 * @param color The <code>Color</code> of the <code>Ring</code> to be placed
	 * @param size The <code>Size</code> of the <code>Ring</code> to be placed
	 * @param player The <code>Player</code> placing the <code>Ring</code>
	 */
	public void setField(int row, int col, Color color, Size size, Player player) {
		setField(index(row, col), color, size, player);
    }
	
	/**
	 * Resets the board and empties all the fields.
	 */
	public void reset() {
		for (int i = 0; i < DIM * DIM; i++) {
			fields[i].reset();
		}
	}
	
	/**
	 * Returns a string representation of the board state.
	 * Also includes numbers on the columns and rows.
	 */
	public String toString() {
		String c;
		String s = "    0     1     2     3     4\n";
		// for each row
		for (int i = 0; i < DIM; i++) {
			s += i + " ";
			// for each column
			for (int j = 0; j < DIM; j++) {
				if (getField(i, j).isEmpty()) {
					s += "00000";
				} else if (getField(i, j).hasBase()) {
					if (getField(i, j).getRing(Size.BASE).getColor() == Color.START) {
						s += "RPGY0";
					} else {
						c = getField(i, j).getRing(Size.BASE).getColor().toChar();
						s += c + c + c + c + c;
					}
				} else {
					// for each ring on this field
					for (int w = 0; w < DIM; w++) {
						if (getField(i, j).isEmptySlot(Size.toEnum(w))) {
							s += "0";
						} else {
							c = getField(i, j).getRing(Size.toEnum(w)).getColor().toChar();
							s += c;
						}
					}
				}
				s += " ";
			}
			s += "\n";
		}
		return s;
	}
}
