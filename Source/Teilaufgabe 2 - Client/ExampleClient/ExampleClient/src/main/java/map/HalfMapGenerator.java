package map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import map.enumerations.ETerrainClient;
import map.validation.DimensionsValidation;
import map.validation.HalfMapValidator;
import map.validation.IHalfMapValidation;
import map.validation.IslandValidation;
import map.validation.TerrainFieldTypesValidation;
import map.validation.WaterEdgesValidation;

public class HalfMapGenerator {
	
	private static List<Position> waterFields = new ArrayList<>();

	private static Map<Position, ETerrainClient> setTerrain() {
		
		List<Position> setPositions = new ArrayList<Position>();
		Map<Position, ETerrainClient> terrain = new HashMap<>();
		
		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < 10; x++){
				setPositions.add(new Position(x,y));
			}
		}
		
		for (Position position: setPositions) {
			terrain.put(position, ETerrainClient.Grass);
		}
		
		placeWater(terrain);
		placeMountains(terrain);
		return terrain;
	}

	private static Position placeFortress(Map<Position, ETerrainClient> terrain) {
		Position fortress;
		List<Position> grassPositions = terrain.entrySet().stream().
				filter(position->position.getValue() == ETerrainClient.Grass).
				map(Map.Entry::getKey).collect(Collectors.toList());
		
		Collections.shuffle(grassPositions);
		fortress = grassPositions.get(0);
		
		return fortress;
	}

	private static void placeMountains(Map<Position, ETerrainClient> terrain) {
		int minMountain = 7;

		Random random = new Random();
		while(minMountain > 0) {
			int randomX = random.nextInt(10);
			int randomY = random.nextInt(5);
			Position randomPosition = new Position(randomX, randomY);

			if(terrain.get(randomPosition) != ETerrainClient.Mountain
					&& !waterFields.contains(randomPosition)) {		
				terrain.put(randomPosition, ETerrainClient.Mountain);
				minMountain--;
				
			}
		}
	}

	private static void placeWater(Map<Position, ETerrainClient> terrain) {
		
		Set<Position> waterPositions = new HashSet<>(Arrays.asList(
				new Position(0,0),
				new Position(9,0),
				new Position(0,4),
				new Position(9,4)
		));
		
		for(Position position: waterPositions) {
			terrain.put(position, ETerrainClient.Water);
		}
		
		int remainingWater = 8 - waterPositions.size();
		Random random = new Random();
		while(remainingWater > 0) {
			int randomX = 1 + random.nextInt(8);
			int randomY = 1 + random.nextInt(3);
			Position randomPosition = new Position(randomX, randomY);
			
			if(terrain.get(randomPosition) != ETerrainClient.Water) {
				terrain.put(randomPosition, ETerrainClient.Water);
				waterFields.add(randomPosition);
				remainingWater--;
			}
		}
}
	
	public static HalfMap generateHalfMap() {
		
		List<IHalfMapValidation> validations = new ArrayList<>();
		validations.add(new TerrainFieldTypesValidation());
		validations.add(new DimensionsValidation());
		validations.add(new WaterEdgesValidation());
		validations.add(new IslandValidation());
		
		HalfMapValidator validator = new HalfMapValidator(validations);
		
		Map<Position, ETerrainClient> terrain = setTerrain();
		Position fortress = placeFortress(terrain);
		HalfMap halfMap;
		if(!validator.isValidated(terrain)) {
			halfMap = generateHalfMap();
			//logger.debug("Regenerating Half Map!");
		} else {
			halfMap = new HalfMap(terrain, fortress);
		}
		return halfMap;
	}
	
		
}





