package exceptions.halfMapExceptions;

public class FortNotOnGrassException extends InvalidHalfMapException{

	private static final long serialVersionUID = 1L;

	public FortNotOnGrassException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
