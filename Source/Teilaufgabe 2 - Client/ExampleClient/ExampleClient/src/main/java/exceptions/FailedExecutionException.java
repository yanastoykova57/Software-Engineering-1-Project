package exceptions;

public class FailedExecutionException extends Exception{
	private static final long serialVersionUID = 1L;

	public FailedExecutionException(String errorText) {
		super(errorText);
	}
}
