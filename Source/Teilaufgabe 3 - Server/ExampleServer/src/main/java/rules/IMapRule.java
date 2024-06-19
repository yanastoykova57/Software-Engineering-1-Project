package rules;

import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import messagesbase.UniqueGameIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromserver.FullMap;

public interface IMapRule {
	void validateHalfMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) throws InvalidHalfMapException;
	void validateFullMap(FullMap fullMap) throws InvalidFullMapException;
}
