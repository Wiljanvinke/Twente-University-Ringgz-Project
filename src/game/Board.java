package game;

import java.util.HashSet;
import java.util.Set;

import players.*;

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
	 * Gives a Set of <code>Field</code>s that are adjacent to the given <code>Field</code>.
	 * @param row The row of the <code>Field</code>
	 * @param col The column of the <code>Field</code>
	 * @return A Set of <code>Field</code>s adjacent to the given <code>Field</code>
	 */
	public Set<Field> adjacent(int row, int col) {
		Set<Field> result = new HashSet<>();
		if (row - 1 >= 0) {
			result.add(getField(row - 1, col));
		}
		if (row + 1 < DIM) {
			result.add(getField(row + 1, col));
		}
		if (col - 1 >= 0) {
			result.add(getField(row, col - 1));
		}
		if (col + 1 < DIM) {
			result.add(getField(row, col + 1));
		}
		return result;
	}
	
	// value hangt af van het aantal spelers en welke kleuren die spelers hebben
	// is argument player voldoende om voor die spelers kleuren values te berekenen?
	// Player een hasColor method nodig of hier handmatig berekenen?
	public void calculateValue(Player player) {
		// for each field of the board
		for (int i = 0; i < 25; i++) {
			// voor alle adjacent playable
			// zet hier de expansion variables
			// eentje voor elke kleur?
			
			
			
			
			// for each color this player has
			for (int j = 0; j < player.getColors().length; j++) {
				double play;
				if (getField(i).playable(player.getColors()[j])) {
					play = 1;
				} else {
					play = 0;
				}
				// for each ring on this field
				for (int w = 0; w < 4; w++) {
					double valueF = 0;
					if (this.getField(i).isEmpty()) {
						valueF = 1;
					}
					// check OWNS
					
					//
					
					getField(i).setValue(player.getColors()[j], Size.toEnum(w), play * valueF);
					// value[color(4)][size(5)] 
				}
				
				// set BASE value hier los van de ringen 
			}
		}
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
						if (getField(i, j).isEmptySlot(Size.toEnum(w))) { // geen nullpointers hier omdat base al gechecked is.
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
