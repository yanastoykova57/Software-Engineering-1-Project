package map.validation;

import java.util.Map;
import map.Position;
import map.enumerations.ETerrainClient;

public class TerrainFieldTypesValidation implements IHalfMapValidation{
	
	private static final int MIN_GRASS_AMOUNT = 24;
	private static final int MIN_MOUNTAIN_AMOUNT = 5;
	private static final int MIN_WATER_AMOUNT = 7;

	@Override
	public boolean isValidated(Map<Position, ETerrainClient> terrain) {
		
		int grassNodes = (int) terrain.values().stream().filter(t -> t == ETerrainClient.Grass).count();
		int mountNodes = (int) terrain.values().stream().filter(t -> t == ETerrainClient.Mountain).count();
		int waterNodes = (int) terrain.values().stream().filter(t -> t.equals(ETerrainClient.Water)).count();
		
		return grassNodes >= MIN_GRASS_AMOUNT &&
				mountNodes >= MIN_MOUNTAIN_AMOUNT &&
				waterNodes >= MIN_WATER_AMOUNT;
	}

}

