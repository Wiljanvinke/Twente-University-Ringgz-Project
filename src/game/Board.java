package game;

import java.util.HashSet;
import java.util.Iterator;
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
		for (int k = 0; k < DIM * DIM; k++) {
			fields[k] = new Field();
		}
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				getField(i, j).setAdjacent(this.adjacent(i, j));
			}
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
	 * Returns the <code>Field</code> with the given row and column.
	 * @param row The row of the <code>Field</code>
	 * @param col The column of the <code>Field</code>
	 * @return The <code>Field</code> at the given row and column
	 */
	public Field getField(int row, int col) {
    	return getField(index(row, col));
    }
	
	public Field[] getFields() {
		return fields;
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
	
	/**
	 * Calculates a value for playing on each <code>Field</code>.
	 * The value is based on whether you get a majority on the <code>Field</code>,
	 * if it allows you to play on new <code>Field</code>s around it and if a base
	 * will prevent the opponent from accessing new <code>Field</code>s.
	 * @param player The <code>Player</code> for which the values gets calculated
	 */
	// letters gebruikt: i j k m w
	public void calculateValue(Player player) {
		double weightE = 1; // valueE = value expansion voor elke kleur
		double weightF = 1; // valueF = value Field om meerderheid te krijgen
		double weightB = 1; // valueB = value Base om andere spelers playable te verlagen
		if (player instanceof ComputerPlayer) {
			weightE = ((ComputerPlayer) player).getWeights()[0]; 
			weightF = ((ComputerPlayer) player).getWeights()[1]; 
			weightB = ((ComputerPlayer) player).getWeights()[2]; 	
		}
		// for each field of the board
		for (int i = 0; i < 25; i++) {
			double[] valueE = new double[] {0, 0, 0, 0};
			// for all adjacent fields
			Iterator<Field> iterator = getField(i).getAdjacent().iterator();
			while (iterator.hasNext()) {
				Field temp = iterator.next();
				for (int k = 0; k < 4; k++) {
					if (!temp.playable(Color.toEnum(k))) {
						valueE[k] += 1;
					}
				}
			}
			// for each color this player has
			for (int j = 0; j < player.getColors().length; j++) {
				double playR = 1;
				if (!getField(i).playable(player.getColors()[j]) || getField(i).isFull()) {
					playR = 0; // this field has no value if you can't play on it
				}
				// for each ring on this field
				for (int w = 0; w < 4; w++) {
					double valueF = 0;
					if (this.getField(i).owns() == null) {
						valueF += 1;
					} else if (player.hasColor(getField(i).owns())) {
						valueF += 0.1; // tijdelijk waarde
						// als je al bezit hebt is er weinig value, kan negatief zijn.
						// gebruik deepcopy Field om te checken of een zet je meerderheid verliest?
						// als de zet je meerderheid verliest, negatieve value!
					} else {
						valueF += 0.5; // tijdelijk waarde
						// nu heeft tegenstander meerderheid
						// check deepcopy of je zet een meerderheid kan geven
					}
					
					// check aantal ringen in bezit
					// speel de kleur waar je het meeste van hebt:
					if (player.getColors().length == 2) {
						//(j + 1) % 2 // the other color index?
					}
					
					
					// speel de size waar de tegenstander het meeste van heeft
					// kan dit nu niet checken zonder andere player object

					double totalValueR = playR * (weightF * valueF  + weightE * valueE[j]);
					getField(i).setValue(player.getColors()[j], Size.toEnum(w), totalValueR);
					// moet ik bijhouden wat de hoogst opgeslagen value is?
					// maakt het mogelijk gelijk een zet terug te geven
				}
				double playB = 1;
				if (!getField(i).isEmpty()) {
					playB = 0; // this field has no value if you can't play on it
				}
				double valueB = 1;
				for (int m = 0; m < valueE.length; m++) {
					if (!player.hasColor(Color.toEnum(m))) {
						valueB += valueE[m];
					}
				}
				// totalValueB moet valueB hoger waarderen dan valueE?
				double totalValueB = playB * playR * (weightB * valueB + weightE * valueE[j]);
				getField(i).setValue(player.getColors()[j], Size.BASE, totalValueB);
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
