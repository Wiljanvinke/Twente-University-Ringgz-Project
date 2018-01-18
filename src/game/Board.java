package game;

/**
 * Game board for the game Ringgz.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class Board {
	private Field[] fields;
	public static final int DIM = 5;
	
	/**
	 * Creates a new Board.
	 */
	public Board() {
		fields = new Field[DIM * DIM];
		for (int i = 0; i < DIM * DIM; i++) {
			fields[i] = new Field();
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public int index(int row, int col) {
    	return DIM * row + col;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public boolean isField(int index) {
    	if (index >= 0 && index < DIM * DIM) {
    		return true;
    	} else {
    		return false;
    	}

    }
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isField(int row, int col) {
    	if (0 <= row && row < DIM && 0 <= col && col < DIM) {
    		return true;
    	} else {
    		return false;
    	}
    }
	
	/**
	 * Returns the field object at the given index of this board.
	 * @param i: The index of the field
	 * @return The field object at the given index
	 */
	public Field getField(int i) {
    	return fields[i];
    }
	
	/**
	 * Returns the index of a field with the given row and column.
	 * @param row: The column of the field
	 * @param col: The ring of othe field
	 * @return
	 */
	public Field getField(int row, int col) {
    	return getField(index(row, col));
    }
	
	/**
	 * Places a ring on the given field.
	 * @param index: The index of the field
	 * @param ring: The Ring object to be placed
	 */
	public void setField(int index, Ring ring) {
    	
    }
	
	/**
	 * Places a ring on the given field.
	 * @param row: The row where the ring will be placed
	 * @param col: The column where the ring will be placed
	 * @param ring: The Ring object to be placed
	 */
	public void setField(int row, int col, Ring ring) {
    	
    }
	
	/**
	 * Resets the board and empties all the fields.
	 */
	public void reset() {
		
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

	/* Voorbeeld van een toString output
        0     1     2     3     4
	0 00000 00000 00000 00000 00000
	1 RRRRR RRRRR RRRRR RRRRR RRRRR 
	2 PPPPP PPPPP PPPPP PPPPP PPPPP 
	3 GGGGG GGGGG GGGGG GGGGG GGGGG 
	4 YYYYY YYYYY YYYYY YYYYY YYYYY 
	
	startingbase: RPGY0
	*/
	
}
