package game;

/**
 * A single ring size, each ring has 5 possible states.
 * It has one of four possible sizes:
 * Tiny, small, medium and large
 * The fifth option is used for bases
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.2
 */
public enum Size {
	TINY, SMALL, MEDIUM, LARGE, BASE;
	
	/**
	 * Changes the size into an integer representing that size.
	 * The smallest size has the smallest number.
	 * @return 0 if tiny, 1 if small, 2 if medium, 3 if large and 4 if base
	 */
	public int toInt() {
		int number = -1;
		switch (this) {
			case TINY: number = 0; break;
			case SMALL: number = 1; break;
			case MEDIUM: number = 2; break;
			case LARGE: number = 3; break;
			case BASE: number = 4; break;
		}
		return number;
	}

	/**
	 * Changes a number into the appropriate size.
	 * The smallest number returns the smallest base.
	 * This method returns null if the number isn't between 0 and 4.
	 * @param a number representing the size
	 * @return TINY if 0, SMALL if 1, MEDIUM if 2, LARGE if 3, BASE if 4 else null
	 */
	public Size toEnum(int number) {
		Size size;
		switch (number) {
			case 0: size = TINY; break;
			case 1: size = SMALL; break;
			case 2: size = MEDIUM; break;
			case 3: size = LARGE; break;
			case 4: size = BASE; break;
			default: size = null; break; // Make sure to check for this!
		}
		return size;
	}
}
