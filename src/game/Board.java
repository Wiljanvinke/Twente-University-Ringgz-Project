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
	
	/**
	 * Gives the whole array of <code>Field</code>s on this <code>Board</code>.
	 * @return An array of 25 <code>Field</code>s
	 */
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
	 * Checks if there is a base of the given <code>Color</code> 
	 * adjacent to the given <code>Field</code>.
	 * @param index The index of the <code>Field</code>
	 * @param color The <code>Color</code> to check for
	 * @return True if there is a given <code>Color</code> base on an adjacent <code>Field</code>
	 */
	public boolean adjacentBase(int index, Color color) {
		Iterator<Field> iterator = getField(index).getAdjacent().iterator();
		while (iterator.hasNext()) {
			Field temp = iterator.next();
			if (temp.hasBase() && temp.getRing(Size.BASE).getColor() == color) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Calculates a value for playing on each <code>Field</code>.
	 * The value is based on whether you get a majority on the <code>Field</code>,
	 * if it allows you to play on new <code>Field</code>s around it and if a base
	 * will prevent the opponent from accessing new <code>Field</code>s.
	 * @param player The <code>Player</code> for which the values gets calculated
	 */
	// letters gebruikt: i j k m n p q w
	public void calculateValue(Player player) {
		double weightE = 1; // valueE = value expansion voor elke kleur
		double weightF = 1; // valueF = value Field om meerderheid te krijgen
		double weightB = 2; // valueB = value Base om andere spelers playable te verlagen
		if (player instanceof ComputerPlayer) {
			weightE = ((ComputerPlayer) player).getWeights()[0]; 
			weightF = ((ComputerPlayer) player).getWeights()[1]; 
			weightB = ((ComputerPlayer) player).getWeights()[2]; 	
		}
		// for each field of the board
		for (int i = 0; i < 25; i++) {
			double[] valueE = new double[] {0, 0, 0, 0};
			// higher value to play on fields adjacent to ones you can't play on yet
			Iterator<Field> iterator = getField(i).getAdjacent().iterator();
			while (iterator.hasNext()) {
				Field temp = iterator.next();
				for (int k = 0; k < 4; k++) {
					if (!temp.playable(Color.toEnum(k))) {
						valueE[k] += 1;
					}
				}
			}
			double valueF = 0;
			// for each color
			for (int n = 0; n < 4; n++) {
				if (getField(i).playable(Color.toEnum(n)) && !player.hasColor(Color.toEnum(n))) {
					valueF += 0.7;
					// Increases value of playing on fields the opponent can play on as well
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
					if (this.getField(i).owns() == null) {
						valueF += 1;
					} else if (player.hasColor(getField(i).owns())) {
						valueF += 0.1;
						// als je al bezit hebt is er weinig value, kan negatief zijn.
						// gebruik deepcopy Field om te checken of een zet je meerderheid verliest?
						// als de zet je meerderheid verliest, negatieve value!
					} else {
						valueF += 0.5;
						// nu heeft tegenstander meerderheid
						// check deepcopy of je zet een meerderheid kan geven
					}
					
					// prefer to use this color if your other color has fewer rings left
					if (player.getColors().length == 2) {
						int[] theseRings = player.getRings(player.getColors()[j]);
						int[] otherRings = player.getRings(player.getColors()[(j + 1) % 2]);
						int resultThese = 0;
						for (int p = 0; p < 5; p++) {
							resultThese += theseRings[p];
						}
						int resultOther = 0;
						for (int q = 0; q < 5; q++) {
							resultOther += otherRings[q];
						}
						if (resultOther < resultThese) {
							valueF += 0.2;
						}
					}
					// prefer to use this size if this color has fewer other sizes left

					// speel de size waar de tegenstander het meeste van heeft
					// kan dit nu niet checken zonder andere player object

					double totalValueR = playR * 
							(weightF * valueF  + weightE * valueE[player.getColors()[j].toInt()]);
					getField(i).setValue(player.getColors()[j], Size.toEnum(w), totalValueR);
				}
				double playB = 1;
				if (!getField(i).isEmpty() || adjacentBase(i, player.getColors()[j])) {
					playB = 0; // this field has no value if you can't play on it
				}
				double valueB = 1;
				// value of base determined by the number of other unreachable fields around it
				for (int m = 0; m < valueE.length; m++) {
					if (!player.hasColor(Color.toEnum(m))) {
						valueB += valueE[m];
					}
				}
				double totalValueB = playB * playR * 
						(weightB * valueB + weightE * valueE[player.getColors()[j].toInt()]);

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
