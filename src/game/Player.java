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
	 * The second color is not used and set to the same color.
	 * @param color: The color the <code>Player</code> gets
	 */
	public Player(Color color) {
		this.color1 = color;
		this.color2 = color; // let hier op!
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
		rings1 = new int[] {3, 3, 3, 3, 3};
		switch (numberPlayers) {
			case 2: rings2 = new int[] {3, 3, 3, 3, 3}; break;
			case 3: rings2 = new int[] {1, 1, 1, 1, 1}; break;
			case 4: rings2 = new int[] {0, 0, 0, 0, 0}; break;
			default: rings2 = new int[] {0, 0, 0, 0, 0}; break; // not the correct number of players
		}
	}
	
	/**
	 * Gives you an array of <code>Color</code>s this <code>Player</code> has.
	 * @return an array of length 1 or 2 containing the color of the <code>Player</code>
	 */
	public Color[] getColors() {
		// misschien moet deze beter een String[R, P] ofzo returnen
		// die kan gelijk gebruiken op server?
		Color[] colors;
		if (color1 == color2) {
			colors = new Color[] {color1};
		} else {
			colors = new Color[] {color1, color2};
		}
		return colors;
	}

	
	/**
	 * Returns an array of length 5 that contains how many <code>Ring</code>s 
	 * this <code>Player</code> has of the given <code>Color</code>.
	 * @param color: The <code>Color</code> of the <code>Ring</code>s
	 * @return array of 5 integers, first value represents the number of tiny <code>Ring</code>s. 
	 * 		Returns null if this <code>Player</code> does not play the given <code>Color</code>
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
	 * Tells you how many <code>Ring</code>s and bases the <code>Player</code> has.
	 * @return the number of remaining <code>Ring</code>s and bases
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
		// gebruikt twee if statements om het leesbaar te houden
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
			getRings(color)[size.toInt()] -= 1;
			/* Deze code haalt twee ringen weg als beide kleuren gelijk zijn
			if (color == color1) {
				rings1[size.toInt()] -= 1;
			} else if (color == color2) {
				rings2[size.toInt()] -= 1;
			}
			*/
		}
	}
}
