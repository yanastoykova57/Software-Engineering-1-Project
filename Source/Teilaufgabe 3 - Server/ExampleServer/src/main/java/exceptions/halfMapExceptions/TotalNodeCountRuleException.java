package exceptions.halfMapExceptions;

public class TotalNodeCountRuleException extends InvalidHalfMapException{

	private static final long serialVersionUID = 1L;

	public TotalNodeCountRuleException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
