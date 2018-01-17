package game;

/**
 * A single color, each ring has one of five possible colors.
 * Red, purple, green and yellow.
 * The color start is used for the starting base.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.2
 */
public enum Color {
	RED, PURPLE, GREEN, YELLOW, START;
	
	/**
	 * Changes the color to a string representing that color.
	 * The string is equal to the first character in the name.
	 * @return R if RED, P if PURPLE, G if GREEN, Y if YELLOW or RGYP for the starting base
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
	public Color toEnum(String string) {
		Color color;
		switch (string) {
			case "R": color = RED; break;
			case "P": color = PURPLE; break;
			case "G": color = GREEN; break;
			case "Y": color = YELLOW; break;
			case "RGYP": color = START; break;
			default: color = null; break; // Make sure to check for this!
		}
		return color;
	}
	
}
