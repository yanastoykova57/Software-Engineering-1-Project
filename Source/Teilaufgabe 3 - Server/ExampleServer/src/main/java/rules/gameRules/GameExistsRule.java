package rules.gameRules;

import org.springframework.stereotype.Component;

import exceptions.gameExceptions.GameIDNotFoundException;
import exceptions.playerExceptions.PlayerIDNotFoundException;
import game.GameDepot;
import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import rules.IGameLogicRule;

@Component
public class GameExistsRule implements IGameLogicRule{

	private GameDepot games;
	
	public GameExistsRule(GameDepot games) {
		this.games = games;
	}
	
	@Override
	public void validatePlayerRegistration(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID)
			throws PlayerIDNotFoundException {}

	@Override
	public void validateGame(UniqueGameIdentifier gameID) throws GameIDNotFoundException {
		games.getGame(gameID)
				.orElseThrow(() -> new GameIDNotFoundException("GameIDNotFound ", "Game with gameID " + gameID + " doesn't exist."));
	}

}
