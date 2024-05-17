package map.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import map.Position;
import map.enumerations.ETerrainClient;

public class IslandValidation implements IHalfMapValidation{

	@Override
	public boolean isValidated(Map<Position, ETerrainClient> terrain) {
		Map<Position, ETerrainClient> terrainToCheck = new HashMap<>(terrain); 
		Position startPosition = new Position(0,0);
		floodFill(startPosition, terrainToCheck);
		
		return terrainToCheck.isEmpty() 
				|| terrainToCheck.values().stream().allMatch(terrainType -> terrainType == ETerrainClient.Water);
	}

	private void floodFill(Position startPos, Map<Position, ETerrainClient> mapTerrain) { 
		Queue<Position> positions = new LinkedList<>(); 
		positions.add(startPos);
		
		while(!positions.isEmpty()) {
			Position currentPosition = positions.poll();
			if(mapTerrain.containsKey(currentPosition) && mapTerrain.get(currentPosition) != ETerrainClient.Water) {
				mapTerrain.remove(currentPosition);
				positions.addAll(getNeighbors(currentPosition, mapTerrain));
			}
		}
		
	}

	private Collection<Position> getNeighbors(Position currentPosition, Map<Position, ETerrainClient> mapTerrain) {
		int x = currentPosition.getX();
		int y = currentPosition.getY();
		List<Position> neighbors = new ArrayList<>();
		
		if(y-1 >= 0 && mapTerrain.containsKey(new Position(x,y-1))) {
			neighbors.add(new Position(x,y-1));
		}
		
		if(y+1 <= 4 && mapTerrain.containsKey(new Position(x,y+1))) {
			neighbors.add(new Position(x,y+1));
		}
		
		if(x-1 >= 0 && mapTerrain.containsKey(new Position(x-1,y))) {
			neighbors.add(new Position(x-1,y));
		}
		
		if(x+1 <= 9 && mapTerrain.containsKey(new Position(x+1,y))) {
			neighbors.add(new Position(x+1,y));
		}
		
		return neighbors;
	}

}
