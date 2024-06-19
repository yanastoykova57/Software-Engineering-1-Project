package exceptions.halfMapExceptions;

public class IslandRuleException extends InvalidHalfMapException{

	private static final long serialVersionUID = 1L;

	public IslandRuleException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
