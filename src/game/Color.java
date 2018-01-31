package game;

/**
 * A single color, each ring has one of five possible colors.
 * Red, purple, green and yellow.
 * The color start is used for the starting base.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 1.0
 */
public enum Color {
	RED, PURPLE, GREEN, YELLOW, START;
	
	/**
	 * Changes the color to a string representing that color.
	 * The string is equal to the first character in the name.
	 * @return R if RED, P if PURPLE, G if GREEN, Y if YELLOW or RGYP for the starting base
	 */
	/*@ ensures \result != null; 
	 */
	public String toChar() {
		String string = "";
		switch (this) {
			case RED: string = "R"; break;
			case PURPLE: string = "P"; break;
			case GREEN: string = "G"; break;
			case YELLOW: string = "Y"; break;
			case START: string = "RGYP"; break;
		}
		return string;
	}
	
	/**
	 * Changes the string into an appropriate color.
	 * The string should be the first character of that colors name.
	 * This method returns null if the string isn't a correct color!
	 * @param A string of the first character of the color or RGYP for starting base
	 * @return RED if R, PURPLE if P, GREEN if G, YELLOW if Y, START if RGYP else null
	 */
	/*@ requires string != null;
	 	requires string == "R" || string == "P" || string == "G" || string == "Y";
	 	ensures \result != null; 
	 */
	static public Color toEnum(String string) {
		Color color;
		switch (string) {
			case "R": color = RED; break;
			case "P": color = PURPLE; break;
			case "G": color = GREEN; break;
			case "Y": color = YELLOW; break;
			case "RGYP": color = START; break;
			default: color = null; break;
		}
		return color;
	}
	
	/**
	 * Changes the <code>Color</code> into an integer representing that <code>Color</code>.
	 * @return 0 if RED, 1 if PURPLE, 2 if GREEN, 3 if YELLOW, 4 if START
	 */
	//@ pure
	public int toInt() {
		int i = 0;
		switch (this) {
			case RED: i = 0; break;
			case PURPLE: i = 1; break;
			case GREEN: i = 2; break;
			case YELLOW: i = 3; break;
			case START: i = 4; break;
		}
		return i;
	}
	
	static public Color toEnum(int i) {
		Color color;
		switch (i) {
			case 0: color = RED; break;
			case 1: color = PURPLE; break;
			case 2: color = GREEN; break;
			case 3: color = YELLOW; break;
			case 4: color = START; break;
			default: color = null; break;
		}
		return color;
	}
}
