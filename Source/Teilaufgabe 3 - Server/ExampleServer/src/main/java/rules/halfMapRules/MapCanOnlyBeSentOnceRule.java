package rules.halfMapRules;

import org.springframework.stereotype.Component;

import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import exceptions.halfMapExceptions.MapCanOnlyBeSentOnceException;
import game.GameDepot;
import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.FullMap;
import player.Player;
import rules.IMapRule;

@Component
public class MapCanOnlyBeSentOnceRule implements IMapRule{
	
	private GameDepot games;
	
	public MapCanOnlyBeSentOnceRule(GameDepot games) {
		this.games = games;
	}

	@Override
	public void validateHalfMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) throws InvalidHalfMapException {
		
		Player player = games.getGamePlayer(gameID, UniquePlayerIdentifier.of(halfMap.getUniquePlayerID())).get();
		
		if(player.getState().equals(EPlayerGameState.MustAct) && player.hasSentHalfMap()) {
			throw new MapCanOnlyBeSentOnceException("MapCanOnlyBeSentOnce: ", "Player is trying to send map again!");
		}	
	}

	@Override
	public void validateFullMap(FullMap fullMap) throws InvalidFullMapException {}

}
