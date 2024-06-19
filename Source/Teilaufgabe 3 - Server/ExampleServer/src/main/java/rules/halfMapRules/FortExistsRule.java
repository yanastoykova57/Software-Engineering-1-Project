package rules.halfMapRules;

import org.springframework.stereotype.Component;

import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.halfMapExceptions.FortNotFoundException;
import exceptions.halfMapExceptions.FortNotOnGrassException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import messagesbase.UniqueGameIdentifier;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromserver.FullMap;
import rules.IMapRule;

@Component
public class FortExistsRule implements IMapRule{
	
	private static final int EXPECTED_FORT_COUNT = 1;

	@Override
	public void validateHalfMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) throws InvalidHalfMapException {
		int castleCount = 0;
		
		for(PlayerHalfMapNode node: halfMap.getMapNodes()) {
			if(node.isFortPresent()) {
				castleCount++;
				if(node.getTerrain() != ETerrain.Grass) {
					throw new FortNotOnGrassException("FortNotOnGrass ", "Player didn't place his fort on a grass node.");
				}
			}
		}
		
		if(castleCount != EXPECTED_FORT_COUNT) {
			throw new FortNotFoundException("FortNotFound ", "Player's fort is missing.");
		}
		
	}

	@Override
	public void validateFullMap(FullMap fullMap) throws InvalidFullMapException {}

}
