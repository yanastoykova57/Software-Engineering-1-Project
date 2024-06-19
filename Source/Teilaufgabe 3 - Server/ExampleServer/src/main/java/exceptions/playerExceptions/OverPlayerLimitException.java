package exceptions.playerExceptions;

public class OverPlayerLimitException extends PlayerRegistrationException{

	private static final long serialVersionUID = 1L;

	public OverPlayerLimitException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
