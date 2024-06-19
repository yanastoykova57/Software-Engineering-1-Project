package rules.fullMapRules;

import org.springframework.stereotype.Component;

import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.fullMapExceptions.MissingPlayerPositionException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import messagesbase.UniqueGameIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromserver.EPlayerPositionState;
import messagesbase.messagesfromserver.FullMap;
import rules.IMapRule;
@Component
public class PlayerPositionsCountRule implements IMapRule {
	
	private static final int PLAYER_POSITION_COUNT = 1;
	private static final int ENEMY_POSITION_COUNT = 1;
	private static final int BOTH_PLAYER_POSITION_COUNT = 1;


	@Override
	public void validateHalfMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) throws InvalidHalfMapException {}


	@Override
	public void validateFullMap(FullMap fullMap) throws InvalidFullMapException {
		int playerPositionCount = (int) fullMap.getMapNodes().stream()
				.filter(node -> node.getPlayerPositionState().equals(EPlayerPositionState.MyPlayerPosition)).count();
		
		int enemyPositionCount = (int) fullMap.getMapNodes().stream()
				.filter(node -> node.getPlayerPositionState().equals(EPlayerPositionState.EnemyPlayerPosition)).count();
		
		int bothPlayerPositionCount = (int) fullMap.getMapNodes().stream()
				.filter(node -> node.getPlayerPositionState().equals(EPlayerPositionState.BothPlayerPosition)).count();
		
		
		if(bothPlayerPositionCount != BOTH_PLAYER_POSITION_COUNT &&
				((playerPositionCount != PLAYER_POSITION_COUNT) || (enemyPositionCount != ENEMY_POSITION_COUNT))) {
			throw new MissingPlayerPositionException("MissingPlayerPosition ", "Player or enemy position is missing!");
			
		}
		
	}

}
