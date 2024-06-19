package rules;

import exceptions.gameExceptions.GameIDNotFoundException;
import exceptions.playerExceptions.PlayerRegistrationException;
import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;

public interface IGameLogicRule {

	void validatePlayerRegistration(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) throws PlayerRegistrationException;

	void validateGame(UniqueGameIdentifier gameID) throws GameIDNotFoundException;

}
