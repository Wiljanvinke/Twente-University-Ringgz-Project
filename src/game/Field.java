package game;

import java.util.Iterator;
import java.util.Set;

import players.*;

/**
 * A single field on a board.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 1.0
 */
public class Field {
	
	public static final int DIM = 5;
	private Ring[] rings;
	private Color owner;
	private boolean[] playable;
	private Set<Field> adjacent;
	private double[][] values;
	
	/**
	 * Creates an empty <code>Field</code> which has 5 slots:
	 * The 4 <code>Ring</code> sizes and
	 * a 5th slot which determines if the <code>Field</code> is occupied by a base.
	 * Each <code>Field</code> starts empty and null.
	 * Also keeps track of a boolean for each color which shows if they can play on this field.
	 * Has a 4x5 matrix of doubles which show the value of playing 
	 * on this field for each color and size ring.
	 */
	public Field() {
		rings = new Ring[DIM];
		playable = new boolean[4];
		values = new double[4][5];
		reset();
	}

	/**
	 * Places a <code>Ring</code> in a certain slot in this <code>Field</code>.
	 * Checks whether the <code>Field</code> is free, the <code>Player</code> has that 
	 * <code>Ring</code> and there is no base of a same <code>Color</code> in an adjacent 
	 * <code>Field</code>. After the move is successful the <code>Ring</code> gets removed 
	 * from the <code>Player</code> and all neighboring <code>Field</code>s are set to be 
	 * playable for this <code>Color</code>. 
	 * @param color The <code>Color</code> of the <code>Ring</code> to place
	 * @param size The <code>Size</code> of the <code>Ring</code> to place
	 * @param player The <code>Player</code> that wants to play a <code>Ring</code>
	 */
	public void placeRing(Color color, Size size, Player player) {
		if (isLegal(color, size, player)) {
			rings[size.toInt()] = new Ring(color, size);
			player.removeRing(color, size);
			Iterator<Field> iterator2 = adjacent.iterator();
			while (iterator2.hasNext()) {
				Field temp = iterator2.next();
				temp.setPlayable(color);
			}
		}
	}
	
	/**
	 * Places the starting base on this field and makes all adjacent fields playable for all colors.
	 */
	public void placeStart() {
		rings[4] = new Ring(Color.START, Size.BASE);
		Iterator<Field> iterator = adjacent.iterator();
		while (iterator.hasNext()) {
			Field temp = iterator.next();
			for (int i = 0; i < 4; i++) {
				temp.setPlayable(Color.toEnum(i));
			}
		}
	}
	
	/**
	 * Check if the given move is allowed by the game rules.
	 * @param color The <code>Color</code> of the <code>Ring</code> to place
	 * @param size The <code>Size</code> of the <code>Ring</code> to place
	 * @param player The <code>Player</code> that wants to play a <code>Ring</code>
	 * @return True if the move is allowed
	 */
	/*@ pure */
	public boolean isLegal(Color color, Size size, Player player) {
		if (isEmptySlot(size) && playable[color.toInt()] && player.hasRing(color, size)) {
			if (size == Size.BASE) {
				Iterator<Field> iterator1 = adjacent.iterator();
				while (iterator1.hasNext()) {
					Field temp = iterator1.next();
					if (temp.hasBase() && 
							temp.getRing(Size.BASE).getColor() == color) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns the <code>Ring</code> in a certain slot of this <code>Field</code>.
	 * @param size The <code>Size</code> needed to check
	 * @return The <code>Ring</code> of the given <code>Size</code> or null if empty
	 */
	/*@ pure */
	public Ring getRing(Size size) {
		return rings[size.toInt()];
	}
	
	/**
	 * Checks whether the given <code>Color</code> is allowed to play on this <code>Field</code>.
	 * @return True if the given <code>Color</code> 
	 * <code>Ring</code>s can be placed on this <code>Field</code>
	 */
	/*@ pure */
	public boolean playable(Color color) {
		return playable[color.toInt()];
	}
	
	/**
	 * Sets the <code>Field</code> to be playable for the given <code>Color</code>.
	 * @param color The color which will now be playable on this <code>Field</code> 
	 */
	public void setPlayable(Color color) {
		playable[color.toInt()] = true;
	}
	
	/**
	 * Checks what the value for playing on this <code>Field</code> is for the 
	 * given <code>Color</code> and <code>Size</code> <code>Ring</code>.
	 * @param color The <code>Color</code> of the spot
	 * @param size The <code>Size</code> of the spot
	 * @return The value for playing on this spot
	 */
	/*@ pure */
	public double getValue(Color color, Size size) {
		return values[color.toInt()][size.toInt()];
	}
	
	/**
	 * Sets a value for playing on this <code>Field</code> for the given 
	 * <code>Color</code> and <code>Size</code>.
	 * @param color The <code>Color</code> of the spot
	 * @param size The <code>Size</code> of the spot
	 * @param value The value for playing on this <code>Field</code>
	 */
	public void setValue(Color color, Size size, Double value) {
		values[color.toInt()][size.toInt()] = value;
	}
	
	/**
	 * Checks which <code>Field</code>s are adjacent to this <code>Field</code>.
	 * @return A set of <code>Field</code>s who are next to this one
	 */
	public Set<Field> getAdjacent() {
		return adjacent;
	}
	
	/**
	 * Sets which <code>Field</code>s are adjacent to this <code>Field</code>.
	 * @param fields A set of <code>Field</code>s
	 */
	public void setAdjacent(Set<Field> fields) {
		adjacent = fields;
	}
	
	/**
	 * Checks if the whole <code>Field</code> is empty and a base can be placed.
	 * @return true if there are no <code>Ring</code>s on this <code>Field</code>
	 */
	/*@ pure */
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
	/*@ pure */
	public boolean hasBase() {
		if (rings[4] == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the given size can be played here.
	 * @param size The <code>Size</code> needed to check
	 * @return true if the slot of the given size is empty
	 */
	/*@ pure */
	public boolean isEmptySlot(Size size) {
		if (size == Size.BASE && !hasBase() && isEmpty()) {
			return true;
		} else if (getRing(size) == null && !hasBase()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether the whole <code>Field</code> is full.
	 * @return True if you cannot play on this <code>Field</code> anymore
	 */
	/*@ pure */
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
	/*@ pure */
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
						case START: break;
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
			switch (highestIndex) {
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
		for (int i = 0; i < DIM - 1; i++) {
			playable[i] = false;
			rings[i] = null;
			for (int j = 0; j < DIM; j++) {
				values[i][j] = 0;
			}
		}
		rings[4] = null;
	}
}
