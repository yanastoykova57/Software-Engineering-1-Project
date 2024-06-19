package exceptions;

public class GenericRuleViolationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private final String name;
	
	public GenericRuleViolationException(String name, String message) {
		super(message);
		this.name = name;
	}

	public String getErrorName() {
		// TODO Auto-generated method stub
		return name;
	}

}
