package exceptions;

public class WrongSizeException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Error: That is not a valid Size";
	}
}
