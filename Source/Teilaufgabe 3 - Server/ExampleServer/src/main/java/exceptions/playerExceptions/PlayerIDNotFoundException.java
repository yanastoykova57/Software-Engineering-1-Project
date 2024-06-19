package exceptions.playerExceptions;

public class PlayerIDNotFoundException extends PlayerRegistrationException{

	private static final long serialVersionUID = 1L;

	public PlayerIDNotFoundException(String name, String message) {
		super(name, message);
	}
}
