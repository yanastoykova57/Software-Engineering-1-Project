package rules.halfMapRules;

import org.springframework.stereotype.Component;

import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import exceptions.halfMapExceptions.WaterEdgesRuleException;
import messagesbase.UniqueGameIdentifier;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromserver.FullMap;
import rules.IMapRule;

@Component
public class WaterEdgesRule implements IMapRule{
	
	private static final int MAX_WATER_ON_EDGE = 4;
	private static final int EDGE_TOP = 0;
	private static final int EDGE_LEFT = 0;
	private static final int EDGE_RIGHT = 4;
	private static final int EDGE_BOTTOM = 9;

	@Override
	public void validateHalfMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) throws InvalidHalfMapException {
		int leftEdge = (int) halfMap.getMapNodes().stream().filter(f -> f.getY() == EDGE_LEFT && f.getTerrain() == ETerrain.Water).count();
		int rightEdge = (int) halfMap.getMapNodes().stream().filter(f -> f.getY() == EDGE_RIGHT && f.getTerrain() == ETerrain.Water).count();
		int upperEdge = (int) halfMap.getMapNodes().stream().filter(f -> f.getX() == EDGE_TOP && f.getTerrain() == ETerrain.Water).count();
		int lowerEdge = (int) halfMap.getMapNodes().stream().filter(f -> f.getX() == EDGE_BOTTOM && f.getTerrain() == ETerrain.Water).count();
		
		if(leftEdge <= MAX_WATER_ON_EDGE && rightEdge <= MAX_WATER_ON_EDGE
				&& upperEdge <= MAX_WATER_ON_EDGE && lowerEdge <= MAX_WATER_ON_EDGE) return;
		else throw new WaterEdgesRuleException("WaterEdgesRuleException: ", "There are more than + " + MAX_WATER_ON_EDGE 
				+ " water fields on one or more edges of player's map.");
	}

	@Override
	public void validateFullMap(FullMap fullMap) throws InvalidFullMapException {}

}
