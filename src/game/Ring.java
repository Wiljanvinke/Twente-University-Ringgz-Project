package game;

/**
 * A ring object. Each object has 2 variables:
 * Size and color
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class Ring {
	// variables
	final private Color color;
	final private Size size;
	
	/*@ requires color.equals(Color.RED) || color.equals(Color.PURPLE) || color.equals(Color.GREEN)
	 	|| color.equals(Color.YELLOW) || color.equals(Color.START);
	 @ requires size.equals(Size.TINY) || size.equals(Size.SMALL) || size.equals(Size.MEDIUM)
	 	|| size.equals(Size.LARGE) || size.equals(Size.BASE);
	 @ ensures getColor() == color;
	 @ ensures getSize() == size;
	 */
	public Ring(Color color, Size size) {
		this.color = color;
		this.size = size;
	}
	
	//@ pure
	public Color getColor() {
		return color;
	}
	
	//@ pure
	public Size getSize() {
		return size;
	}
	
}
