package map.validation;

import java.util.Map;
import java.util.Optional;

import map.Position;
import map.enumerations.ETerrainClient;

public class DimensionsValidation implements IHalfMapValidation{
	
	private static final int MAX_X = 9;
	private static final int MAX_Y = 4;

	@Override
	public boolean isValidated(Map<Position, ETerrainClient> terrain) {

		Optional<Integer> currentX = terrain.keySet().stream().map(Position::getX).max(Integer::compare);
		Optional<Integer> currentY = terrain.keySet().stream().map(Position::getY).max(Integer::compare);
		
		if(MAX_X != currentX.orElse(-1) || MAX_Y != currentY.orElse(-1)) return false;
		
		return true;
	}

}
