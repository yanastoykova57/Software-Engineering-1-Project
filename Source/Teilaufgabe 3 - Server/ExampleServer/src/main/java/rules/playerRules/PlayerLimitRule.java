package rules.playerRules;

import org.springframework.stereotype.Component;

import exceptions.GenericRuleViolationException;
import exceptions.gameExceptions.GameIDNotFoundException;
import exceptions.playerExceptions.OverPlayerLimitException;
import exceptions.playerExceptions.PlayerRegistrationException;
import game.GameDepot;
import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import rules.IGameLogicRule;
import rules.gameRules.GameExistsRule;

@Component
public class PlayerLimitRule implements IGameLogicRule{
	
	private GameDepot games;
	private IGameLogicRule gameExistsRule;
	
	public PlayerLimitRule(GameDepot games) {
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
		
		if(games.getGame(gameID).get().getPlayerCount() > 2) {
			throw new OverPlayerLimitException("OverPlayerLimit ", "Player registration limit of 2 is reached! Can't register more players!");
		} 
		
	}

	@Override
	public void validateGame(UniqueGameIdentifier gameID) throws GameIDNotFoundException {}

}
