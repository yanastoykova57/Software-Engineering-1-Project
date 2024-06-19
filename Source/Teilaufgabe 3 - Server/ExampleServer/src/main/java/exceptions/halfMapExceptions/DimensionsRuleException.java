package exceptions.halfMapExceptions;

public class DimensionsRuleException extends InvalidHalfMapException{

	private static final long serialVersionUID = 1L;

	public DimensionsRuleException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
