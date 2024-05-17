package exceptions;

public class MissingCurrentNodeException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public MissingCurrentNodeException () {
		super("Current node cannot be found!");
	}
}
