package exceptions.playerExceptions;

import exceptions.GenericExampleException;

public class PlayerRegistrationException extends GenericExampleException{

	private static final long serialVersionUID = 1L;

	public PlayerRegistrationException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
