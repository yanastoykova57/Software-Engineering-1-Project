package rules;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import exceptions.GenericRuleViolationException;
import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.gameExceptions.GameIDNotFoundException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import exceptions.playerExceptions.PlayerRegistrationException;
import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromserver.FullMap;

@Service
public class RuleHandler {
	private final static Logger logger = LoggerFactory.getLogger(RuleHandler.class);
	
	private List<IMapRule> mapRules;
	private List<IGameLogicRule> gameRules;
	
	@Autowired
	public RuleHandler(List<IMapRule> mapRules, List<IGameLogicRule> gameRules) {
		this.mapRules = mapRules;
		this.gameRules = gameRules;
	}
	
	public void validateGame(UniqueGameIdentifier gameID) {
		for(IGameLogicRule rule: gameRules) {
			try {
				rule.validateGame(gameID);
			} catch (GameIDNotFoundException e) {
				logger.error("Game with gameID: " + gameID + " not found!");
				throw new GenericRuleViolationException(e.getErrorName(), e.getMessage());
			}
		}
	}
	
	public void validatePlayerRegistration(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		for(IGameLogicRule rule: gameRules) {
			try {
				rule.validatePlayerRegistration(gameID, playerID);
			} catch (PlayerRegistrationException e) {
				logger.error("Player with playerID: " + playerID + " is not registered!");
				throw new GenericRuleViolationException(e.getErrorName(), e.getMessage());
			}
		
	}
	}

	public Optional<String> validateMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) {
		for(IMapRule rule: mapRules) {
				try {
					rule.validateHalfMap(halfMap, gameID);
				} catch (InvalidHalfMapException e) {
					logger.error("Player with playerID: " + halfMap.getUniquePlayerID() + " has an issue sending halfMap: " + e.getMessage());
					return Optional.of(e.getMessage());
				}	
		}
		return Optional.empty();
	}

	public void validateFullMap(FullMap fullMap) {
		for(IMapRule rule: mapRules) {
			try {
				rule.validateFullMap(fullMap);
			} catch (InvalidFullMapException e) {
				logger.error("Full map does not match documentation!");
				throw new GenericRuleViolationException(e.getErrorName(), e.getMessage());
			}
		}	
	}
	
}
