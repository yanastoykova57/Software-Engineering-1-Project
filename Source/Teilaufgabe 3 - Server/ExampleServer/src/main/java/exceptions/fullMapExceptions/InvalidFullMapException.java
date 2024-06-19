package exceptions.fullMapExceptions;

import exceptions.GenericExampleException;

public class InvalidFullMapException extends GenericExampleException{

	private static final long serialVersionUID = 1L;

	public InvalidFullMapException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
