package exceptions;

public class InvalidMoveArgumentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return "The move argument is not correct. Format as \"int boardRow int BoardColumn"
				+ "String ringColor int ringSize\"";
	}

}
