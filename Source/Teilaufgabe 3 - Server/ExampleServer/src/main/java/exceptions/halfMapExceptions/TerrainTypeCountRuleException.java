package exceptions.halfMapExceptions;

public class TerrainTypeCountRuleException extends InvalidHalfMapException{

	private static final long serialVersionUID = 1L;

	public TerrainTypeCountRuleException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
