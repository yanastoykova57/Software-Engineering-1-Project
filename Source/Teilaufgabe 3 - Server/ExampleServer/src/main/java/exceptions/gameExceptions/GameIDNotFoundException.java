package exceptions.gameExceptions;

import exceptions.GenericExampleException;

public class GameIDNotFoundException extends GenericExampleException{

	private static final long serialVersionUID = 1L;

	public GameIDNotFoundException(String name, String message) {
		super(name, message);
	}
}
