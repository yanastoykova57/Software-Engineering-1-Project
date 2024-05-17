package map.validation;

import java.util.Map;
import map.Position;
import map.enumerations.ETerrainClient;

public class WaterEdgesValidation implements IHalfMapValidation{
	private static final int MAX_WATER_ON_EDGE_AMOUNT = 2;

	@Override
	public boolean isValidated(Map<Position, ETerrainClient> terrain) {
		
		int leftEdge = (int) terrain.entrySet().stream().filter(f -> f.getKey().getY() == 0 && f.getValue() == ETerrainClient.Water).count();
		int rightEdge = (int) terrain.entrySet().stream().filter(f -> f.getKey().getY() == 4 && f.getValue() == ETerrainClient.Water).count();
		int upperEdge = (int) terrain.entrySet().stream().filter(f -> f.getKey().getX() == 0 && f.getValue() == ETerrainClient.Water).count();
		int lowerEdge = (int) terrain.entrySet().stream().filter(f -> f.getKey().getX() == 9 && f.getValue() == ETerrainClient.Water).count();
		
		return leftEdge <= MAX_WATER_ON_EDGE_AMOUNT 
				&& rightEdge <= MAX_WATER_ON_EDGE_AMOUNT 
				&& upperEdge <= MAX_WATER_ON_EDGE_AMOUNT 
				&& lowerEdge <= MAX_WATER_ON_EDGE_AMOUNT;
	}
	
}

