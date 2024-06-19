package exceptions.fullMapExceptions;

public class MissingPlayerPositionException extends InvalidFullMapException{

	private static final long serialVersionUID = 1L;

	public MissingPlayerPositionException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
