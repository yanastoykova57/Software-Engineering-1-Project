package rules.playerRules;

import org.springframework.stereotype.Component;

import exceptions.GenericRuleViolationException;
import exceptions.gameExceptions.GameIDNotFoundException;
import exceptions.playerExceptions.PlayerIDNotFoundException;
import exceptions.playerExceptions.PlayerRegistrationException;
import game.Game;
import game.GameDepot;
import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import rules.IGameLogicRule;
import rules.gameRules.GameExistsRule;

@Component
public class PlayerRegisteredRule implements IGameLogicRule{
	
	private GameDepot games;
	private IGameLogicRule gameExistsRule;
	
	public PlayerRegisteredRule(GameDepot games) {
		this.games = games;
		this.gameExistsRule = new GameExistsRule(games);
	}

	@Override
	public void validatePlayerRegistration(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID)
			throws PlayerRegistrationException {
		try {
			gameExistsRule.validateGame(gameID);
		} catch (GameIDNotFoundException e) {
			throw new GenericRuleViolationException(e.getErrorName(), e.getMessage());
		}
		
		Game game = games.getGame(gameID).get();
		game.getPlayer(playerID).orElseThrow(() -> new PlayerIDNotFoundException("PlayerIDNotFound", "No players with playerID: " + playerID));
		
	}

	@Override
	public void validateGame(UniqueGameIdentifier gameID) throws GameIDNotFoundException {}

}
