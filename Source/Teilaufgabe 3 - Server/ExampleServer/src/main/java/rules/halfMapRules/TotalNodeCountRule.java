package rules.halfMapRules;

import org.springframework.stereotype.Component;

import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import exceptions.halfMapExceptions.TotalNodeCountRuleException;
import messagesbase.UniqueGameIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromserver.FullMap;
import rules.IMapRule;

@Component
public class TotalNodeCountRule implements IMapRule{
	
	private static final int TOTAL_NODE_COUNT = 50;

	@Override
	public void validateHalfMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) throws InvalidHalfMapException {
		if(halfMap.getMapNodes().size() != TOTAL_NODE_COUNT) {
			throw new TotalNodeCountRuleException("TotalNodeCountRuleException: ", "Total half map node count must be 50!");
		} else return;
		
	}

	@Override
	public void validateFullMap(FullMap fullMap) throws InvalidFullMapException {}

}
