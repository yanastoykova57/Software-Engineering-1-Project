package exceptions.halfMapExceptions;

public class WaterEdgesRuleException extends InvalidHalfMapException{

	private static final long serialVersionUID = 1L;

	public WaterEdgesRuleException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
