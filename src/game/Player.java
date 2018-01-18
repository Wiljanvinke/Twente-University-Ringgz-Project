package game;

/**
 * A class to keep players in the game.
 * There are different players: human and computer.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.2
 */
public class Player {

	private Color color1;
	private Color color2;
	private int[] rings1;
	private int[] rings2;

	/**
	 * Constructs a <code>Player</code> for a four-player game.
	 * @param color The color the <code>Player</code> gets
	 */
	public Player(Color color) {
		this.color1 = color;
		rings1 = new int[] {3, 3, 3, 3, 3};
		rings2 = new int[] {0, 0, 0, 0, 0};
	}
	
	/**
	 * Constructs a new <code>Player</code>. 
	 * If less than 4 <code>Player</code>s have been specified, an extra set
	 * of <code>Ring</code>s will be given with a different <code>Color</code>. 
	 * The size of the extra set depends on the number of <code>Player</code>s.
	 * @param color1: The <code>Color</code> of the first set of <code>Ring</code>s
	 * @param color2: The <code>Color</code> of the second set of <code>Ring</code>s
	 * @param numberPlayers: The number of total <code>Player</code>s who will be playing the game
	 */
	public Player(Color color1, Color color2, int numberPlayers) {
		this.color1 = color1;
		this.color2 = color2;
		switch (numberPlayers) {
			case 2: 
				rings1 = new int[] {3, 3, 3, 3, 3};
				rings2 = new int[] {3, 3, 3, 3, 3}; break;
			case 3: 
				rings1 = new int[] {3, 3, 3, 3, 3}; 
				rings2 = new int[] {1, 1, 1, 1, 1}; break;
			case 4: 
				rings1 = new int[] {3, 3, 3, 3, 3};
				rings2 = new int[] {0, 0, 0, 0, 0}; break;
			default: 
		}
	}
	
	/**
	 * Returns an array of length 5 that contains how many rings this player has of the given color.
	 * @param color: The color of the rings
	 * @return array of 5 integers, first value represents the number of smallest rings. 
	 * 				Returns null if this player does not play the given color
	 */
	public int[] getRings(Color color) {
		if (color == color1) {
			return rings1;
		} else if (color == color2) {
			return rings2;
		}
		return null;
	}
	
	/**
	 * Tells you how many rings and bases the player has.
	 * @return the number of remaining rings and bases
	 */
	public int remainingRings() {
		int result = 0;
		for (int i = 0; i < 5; i++) {
			result += rings1[i] + rings2[i];
		}
		return result;
	}
	
	/**
	 * Checks if a this <code>Player</code> has a <code>Ring</code> 
	 * with a specific <code>Color</code> and <code>Size</code>.
	 * @param color: The <code>Color</code> of the <code>Ring</code> to check
	 * @param size: The <code>Size</code> of the <code>Ring</code> to check
	 * @return true if the <code>Player</code> has this Ring else false
	 */
	public boolean hasRing(Color color, Size size) {
		if (color == color1 && rings1[size.toInt()] > 1) {
			return true;
		} else if (color == color2 && rings2[size.toInt()] > 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a this <code>Player</code> has a specific <code>Ring</code> <code>Size</code>.
	 * @param ring: The <code>Ring</code> to check
	 * @return true if the <code>Player</code> has this Ring else false
	 */
	public boolean hasRing(Size size) {
		if (rings1[size.toInt()] > 1 || rings1[size.toInt()] > 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a <code>Ring</code> from the <code>Player</code>.
	 * @param ring: The <code>Ring</code> the method needs to remove
	 */
	public void removeRing(Color color, Size size) {
		// this could use the getRings(Color color) method to find rings1 or rings2
		if (hasRing(color, size)) {
			if (color == color1) {
				rings1[size.toInt()] -= 1;
			} else if (color == color2) {
				rings2[size.toInt()] -= 1;
			}
		}
	}
}
