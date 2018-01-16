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
	final Color color;
	final Size size;
	
	public Ring(Color color, Size size) {
		this.color = color;
		this.size = size;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Size getSize() {
		return size;
	}
	
}
