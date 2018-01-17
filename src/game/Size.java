package game;

/**
 * A single ring size, each ring has 5 possible states:
 * It has one of four possible sizes:
 * Tiny, small, medium and large
 * The fifth option is used for bases
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public enum Size {
	TINY, SMALL, MEDIUM, LARGE, BASE;
	
	public int toInt() {
		int number;
		switch (this) {
			case TINY: number = 0; break;
			case SMALL: number = 1; break;
			case MEDIUM: number = 2; break;
			case LARGE: number = 3; break;
			case BASE: number = 4; break;
			default: number = -1; break;
		}
		return number;
	}

	public Size toEnum(int number) {
		Size size;
		switch (number) {
			case 0: size = TINY; break;
			case 1: size = SMALL; break;
			case 2: size = MEDIUM; break;
			case 3: size = LARGE; break;
			case 4: size = BASE; break;
			default: size = null; break;
		}
		return size;
	}
}
