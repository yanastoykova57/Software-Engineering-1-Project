package exceptions;

public class InvalidTerrainTypeException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public  InvalidTerrainTypeException () {
		super("Terrain type not found!");
	}
}
