package exceptions;

public class AdjacentBaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Error: There is a base of the same color in an adjacent field";
	}
}
