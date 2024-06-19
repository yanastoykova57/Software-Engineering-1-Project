package server.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exceptions.GenericRuleViolationException;
import game.Game;
import game.GameDepot;
import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerRegistration;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.PlayerState;
import player.Player;
import rules.RuleHandler;

@Service
public class ServerLogic {
	private final static Logger logger = LoggerFactory.getLogger(ServerLogic.class);
	private GameDepot games;
	private RuleHandler handleRule;
	
	@Autowired
	public ServerLogic(GameDepot games, RuleHandler handleRule) {
		this.games = games;
		this.handleRule = handleRule;
	}
		
	UniqueGameIdentifier startNewGame() {
			Game newGame = new Game(); 
			games.addGame(newGame);
			UniqueGameIdentifier gameID = newGame.getGameID();
			logger.info("Starting new Game with gameID: " + gameID);
			return gameID;
	}
	
	private Game getGame(UniqueGameIdentifier gameID) {
			handleRule.validateGame(gameID);
			return games.getGame(gameID).get();	
	}
	
	private Player getPlayer(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) { 
			handleRule.validatePlayerRegistration(gameID, playerID);
			return games.getGamePlayer(gameID,playerID).get();
	}
	
	UniquePlayerIdentifier registerPlayer(UniqueGameIdentifier gameID, PlayerRegistration playerRegistration) {
		Game game = getGame(gameID);
		Player newPlayer = new Player(playerRegistration);
		game.registerPlayer(newPlayer);
		
		if(game.getPlayerCount() == 2) {
			game.setFirstPlayer();
		}
		
		UniquePlayerIdentifier playerID = newPlayer.getPlayerID();
		logger.info("Player with playerID: "+ playerID + " registered.");
		return playerID;
		
	}
	
	void receiveHalfMap(UniqueGameIdentifier gameID, PlayerHalfMap halfMap)  {
		Game game = getGame(gameID);
		Player player = getPlayer(gameID, UniquePlayerIdentifier.of(halfMap.getUniquePlayerID()));
		
		Optional<String> possibleValidationMessage = handleRule.validateMap(halfMap, gameID);
		if(possibleValidationMessage.isPresent()) {
			game.setLoser(player);
			throw new GenericRuleViolationException("ReceivingHalfMapError", possibleValidationMessage.get());
		}
		
		game.receiveHalfMap(halfMap, player);	
		logger.info("Half Map for player: " + player.getPlayerID() + " is received.");
	}
	
	
	List<PlayerState> getPlayerStates(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {		
		Game game = getGame(gameID);
		List<PlayerState> playerStates = new ArrayList<>();
			
		Player player = getPlayer(gameID, playerID); 
		PlayerState playerState = player.getPlayerState();
		playerStates.add(playerState);
					
		Optional<Player> possiblePlayer2 = game.getSecondPlayer(playerID);
		if(possiblePlayer2.isPresent()) {
			PlayerState playerState2 = possiblePlayer2.get().getHiddenPlayerState();
			playerStates.add(playerState2);
		}
		
		return playerStates;		
					
	}
	
	String getGameStateID(UniqueGameIdentifier gameID) {
		return getGame(gameID).getGameStateId();
	}
	
	Optional<FullMap> getFullMap(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		Player player = getPlayer(gameID, playerID);
		Optional<FullMap> possibleFullMap = getGame(gameID).getMap(player);
		if(possibleFullMap.isPresent()) {
			handleRule.validateFullMap(possibleFullMap.get());
		}
		return possibleFullMap;
	}

}
