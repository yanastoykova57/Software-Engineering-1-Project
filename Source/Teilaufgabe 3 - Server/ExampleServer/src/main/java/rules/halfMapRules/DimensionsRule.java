package rules.halfMapRules;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Component;

import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.halfMapExceptions.DimensionsRuleException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import messagesbase.UniqueGameIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromserver.FullMap;
import rules.IMapRule;

@Component
public class DimensionsRule implements IMapRule {
	
	private static final int DIMENSION_X = 9;
	private static final int DIMENSION_Y = 4;

	@Override
	public void validateHalfMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) throws InvalidHalfMapException {
		Collection<PlayerHalfMapNode> nodes = halfMap.getMapNodes();
		
		Optional<Integer> maxX = nodes.stream().map(PlayerHalfMapNode::getX).max(Integer::compare);
		Optional<Integer> maxY = nodes.stream().map(PlayerHalfMapNode::getY).max(Integer::compare);
		
		if(DIMENSION_X != maxX.orElse(-1) || DIMENSION_Y != maxY.orElse(-1)) {
			//return false;
			throw new DimensionsRuleException("DimensionsRuleException: ", "Map dimensions [" + maxX + ", " + maxY 
					+ "] don't match documentation requirements.");
		}
		return;
	}

	@Override
	public void validateFullMap(FullMap fullMap) throws InvalidFullMapException {}

}
