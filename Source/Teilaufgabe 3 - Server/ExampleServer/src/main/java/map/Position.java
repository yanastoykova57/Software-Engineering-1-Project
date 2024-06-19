package map;

import java.util.Objects;

public class Position {
	private final int x;
	private final int y;
	
	public Position(int x, int y) { 
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }

	public int getY() { return y; }
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "< " + x + ", " + y + " >";
	}
}
