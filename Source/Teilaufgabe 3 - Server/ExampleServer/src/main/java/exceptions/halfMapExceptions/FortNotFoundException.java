package exceptions.halfMapExceptions;

public class FortNotFoundException extends InvalidHalfMapException{

	private static final long serialVersionUID = 1L;

	public FortNotFoundException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
