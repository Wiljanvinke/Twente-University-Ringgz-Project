package game;

/**
 * A single field on a board.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.3
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
	 * Checks whether the whole <code>Field</code> is full.
	 * @return True if you cannot play on this <code>Field</code> anymore
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
		owner = null;
		if (!hasBase() && !isEmpty()) {
			// {red, purple, green, yellow}
			int[] colors = new int[] {0, 0, 0, 0};
			for (int i = 0; i < DIM - 1; i++) {
				if (rings[i] != null) {
					switch (rings[i].getColor()) {
						case RED: colors[0]++; break;
						case PURPLE: colors[1]++; break;
						case GREEN: colors[2]++; break;
						case YELLOW: colors[3]++; break;
					}
				}
			}
			int highest = 0;
			int highestIndex = -1;
			for (int i = 0; i < 4; i++) {
				if (colors[i] == highest) {
					highestIndex = -1;
				} else if (colors[i] > highest) {
					highest = colors[i];
					highestIndex = i;
				}
			}
			switch (highestIndex) { // if we ever need to do this again we better add this method to the color class
				case 0: owner = Color.RED; break;
				case 1: owner = Color.PURPLE; break;
				case 2: owner = Color.GREEN; break;
				case 3: owner = Color.YELLOW; break;
				default: owner = null;
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
