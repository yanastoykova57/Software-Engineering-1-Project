package rules.fullMapRules;

import java.util.Collection;

import org.springframework.stereotype.Component;

import exceptions.fullMapExceptions.FullMapDimensionsException;
import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import messagesbase.UniqueGameIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.FullMapNode;
import rules.IMapRule;

@Component
public class FullMapDimensionsRule implements IMapRule{
	//private final static Logger logger = LoggerFactory.getLogger(FullMapDimensionsRule.class);
	
	private static final int RECTANGLE_MAP_X = 19;
	private static final int RECTANGLE_MAP_Y = 4;
	private static final int SQUARE_MAP_X_Y = 9;

	@Override
	public void validateHalfMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) throws InvalidHalfMapException {}

	@Override
	public void validateFullMap(FullMap fullMap) throws InvalidFullMapException {
		Collection<FullMapNode> nodes = fullMap.getMapNodes();
		
		Integer maxX = nodes.stream().map(FullMapNode::getX).max(Integer::compare).orElse(-1);
		Integer maxY = nodes.stream().map(FullMapNode::getY).max(Integer::compare).orElse(-1);
		
		if(!((maxX == RECTANGLE_MAP_X && maxY == RECTANGLE_MAP_Y) || (maxX == SQUARE_MAP_X_Y && maxY == SQUARE_MAP_X_Y))) {
			throw new FullMapDimensionsException("FullMapDimensionsException ", "Full Map dimensions don't match documentation!");
		}
		
	}

}
