package exceptions.halfMapExceptions;

public class MapCanOnlyBeSentOnceException extends InvalidHalfMapException{

	private static final long serialVersionUID = 1L;

	public MapCanOnlyBeSentOnceException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
		
	}

}
