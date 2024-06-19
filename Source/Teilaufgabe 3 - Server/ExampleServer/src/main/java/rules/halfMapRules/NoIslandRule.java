package rules.halfMapRules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Component;

import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import exceptions.halfMapExceptions.IslandRuleException;
import map.Position;
import messagesbase.UniqueGameIdentifier;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromserver.FullMap;
import rules.IMapRule;

@Component
public class NoIslandRule implements IMapRule{

	@Override
	public void validateHalfMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) throws InvalidHalfMapException {
		Map<Position, ETerrain> terrainToCheck = new HashMap<>(); 
		Position startPosition = new Position(0,0);
		for(PlayerHalfMapNode node: halfMap.getMapNodes()) {
			terrainToCheck.put(new Position(node.getX(), node.getY()), node.getTerrain());
		}
		
		for(Map.Entry<Position, ETerrain> entry: terrainToCheck.entrySet()) {
			if(entry.getValue() != ETerrain.Water) {
				startPosition = entry.getKey();
				break;
				}
		}
		
		floodFill(startPosition, terrainToCheck);
		
		if(terrainToCheck.isEmpty() || terrainToCheck.values().stream().allMatch(terrainType -> terrainType == ETerrain.Water)) {
			return;
		} else throw new IslandRuleException("IslandRuleException: ", "Player's map must not have islands on it!");
	}

	private static void floodFill(Position startPos, Map<Position, ETerrain> mapTerrain) { 
		Queue<Position> positions = new LinkedList<>(); 
		positions.add(startPos);
		
		while(!positions.isEmpty()) {
			Position currentPosition = positions.poll();
			if(mapTerrain.containsKey(currentPosition) && mapTerrain.get(currentPosition) != ETerrain.Water) {
				mapTerrain.remove(currentPosition);
				positions.addAll(getNeighbors(currentPosition, mapTerrain));
			}
		}
		
	}

	private static Collection<Position> getNeighbors(Position currentPosition, Map<Position, ETerrain> mapTerrain) {
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
		

	@Override
	public void validateFullMap(FullMap fullMap) throws InvalidFullMapException {}

}
