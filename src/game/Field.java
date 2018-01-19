package game;

/**
 * A single field on a board.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.2
 */
public class Field {
	
	public static final int DIM = 5; // dit is het aantal ringen, niet echt een dimensie
	private Ring[] rings;
	private Color owner;
	
	/**
	 * Creates an empty <code>Field</code> which has 5 slots:
	 * The 4 <code>Ring</code> sizes and
	 * a 5th slot which determines if the <code>Field</code> is occupied by a base.
	 * Each <code>Field</code> starts empty and null.
	 */
	public Field() {
		rings = new Ring[DIM];
		reset();
	}

	/**
	 * Places a <code>Ring</code> in a certain slot in this <code>Field</code> if the slot is free.
	 * Otherwise does nothing.
	 * @param color: The <code>Color</code> of the <code>Ring</code> to place
	 * @param size: The <code>Size</code> of the <code>Ring</code> to place
	 * @param player: The <code>Player</code> that wants to play a <code>Ring</code>
	 */
	public void placeRing(Color color, Size size, Player player) {
		if (!hasBase() && isEmptySlot(size)) {
			rings[size.toInt()] = new Ring(color, size);
			player.removeRing(color, size); // LET OP, gamelogic hoeft geen ring te verwijderen
		}
	}
	
	/**
	 * Returns the <code>Ring</code> in a certain slot of this <code>Field</code>.
	 * @param size: The <code>Size</code> needed to check
	 * @return the <code>Ring</code> of the given <code>Size</code> or null if empty
	 */
	public Ring getRing(Size size) {
		return rings[size.toInt()];
	}
	
	/**
	 * Checks if the whole <code>Field</code> is empty and a base can be placed.
	 * @return true if there are no <code>Ring</code>s on this <code>Field</code>
	 */
	public boolean isEmpty() {
		for (int i = 0; i < DIM; i++) {
			if (rings[i] != null) {
				return false;
			}
		}
		return true;
    }
	
	/**
	 * Checks if the <code>Field</code> has a base.
	 * @return true if the <code>Field</code> has a base on it
	 */
	public boolean hasBase() {
		if (rings[4] == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the given size is empty.
	 * @param size The <code>Size</code> needed to check
	 * @return true if the slot of the given size is empty
	 */
	public boolean isEmptySlot(Size size) {
		if (getRing(size) == null && !hasBase()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether the whole field is full.
	 * @return true if you cannot play on this field anymore
	 */
	public boolean isFull() {
		if (!hasBase()) {
			for (int i = 0; i < DIM - 1; i++) {
		    	if (isEmptySlot(Size.toEnum(i))) {
		    		return false;
		    	}
			}
		}
		return true;
    }

	/**
	 * Checks if a color holds a majority on this field.
	 * @return the color which holds a majority, returns null on a tie or if the field has a base
	 */
	public Color owns() {
		if (hasBase() || isEmpty()) {
			return null;
		} else {
			int red = 0;
			int purple = 0;
			int green = 0;
			int yellow = 0;
			owner = null;
			for (int i = 0; i < DIM - 1; i++) {
				if (rings[i].getColor().equals(Color.RED)) {  // Maakt nu nullpointerexceptions naar niet bestaande ringen
					red++;
				} else if (rings[i].getColor().equals(Color.PURPLE)) {
					purple++;
				} else if (rings[i].getColor().equals(Color.GREEN)) {
					green++;
				} else if (rings[i].getColor().equals(Color.YELLOW)) {
					yellow++;
				}
			}
			if (red > purple && red > green && red > yellow) {
				owner = Color.RED;
			} else if (purple > red && purple > green && purple > yellow) {
				owner = Color.PURPLE;
			} else if (green > red && green > purple && green > yellow) {
				owner = Color.GREEN;
			} else if (yellow > red && yellow > purple && yellow > green) {
				owner = Color.YELLOW;
			}
		}
		return owner;
	}
	
	/**
	 * Resets the field and makes all rings null again.
	 */
	public void reset() {
		for (int i = 0; i < DIM; i++) {
			rings[i] = null;
		}
	}
}
