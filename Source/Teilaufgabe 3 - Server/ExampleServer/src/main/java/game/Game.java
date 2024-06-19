package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.FullMap;
import player.Player;
import map.FullMapGenerator;

public class Game {
	
	private final static Logger logger = LoggerFactory.getLogger(Game.class);
	
	private UniqueGameIdentifier gameID;
	private String gameStateId;
	private FullMap map; 
	private final List<Player> players;
	
	/*
	 * TAKEN FROM <1> 
	 * https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
	 * 
	 * I was interested in a way to generate gameStateIDs in a more efficient way than manually, 
	 * so i found this post online on RandomStringUtils and thought it was helpful.
	 */
	
	public Game() {
		this.gameStateId = RandomStringUtils.randomAlphanumeric(5);
		this.gameID = new UniqueGameIdentifier(gameStateId);
		this.map = null;
		this.players = new ArrayList<Player>();
	}

	public UniqueGameIdentifier getGameID() {
		return gameID;
	}
	
	public void registerPlayer(Player newPlayer) { 
		players.add(newPlayer);
		updateGameStateID();
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public int getPlayerCount() {
		return players.size();
	}
	
	public Optional<Player> getPlayer (UniquePlayerIdentifier playerID) {
		return players.stream().filter(player -> player.getPlayerID().equals(playerID)).findFirst();
		
	}

	public String getGameStateId() {
		return gameStateId;
	}

	public Optional<FullMap> getMap(Player firstPlayer) {
		if(players.size() == 2 && players.stream().allMatch(Player::hasSentHalfMap)) {
			
			Optional<FullMap> possibleFirstMap = firstPlayer.getMap();
			Optional<Player> possibleSecondPlayer = getSecondPlayer(firstPlayer.getPlayerID());
			
			if(possibleSecondPlayer.isPresent() && possibleFirstMap.isPresent()) {
				Player secondPlayer = possibleSecondPlayer.get();
				Optional<FullMap> possibleSecondMap = secondPlayer.getMap();
				
				if(possibleSecondMap.isPresent()) {
					FullMap combinedMap = FullMapGenerator.combineMaps(possibleFirstMap.get(), possibleSecondMap.get());
					this.setMap(combinedMap);
					return Optional.of(combinedMap);
				}
			}
			
		}
		
		logger.warn("Conditions were not met when trying to combine maps. Returning an empty map!");
		return Optional.ofNullable(map);
	}
	

	public FullMap getMap() {
		return map;
	}
	
	public void setMap(FullMap map) {
		this.map = map;
	}

	public void setFirstPlayer() {
		List<Player> playerSelection = new ArrayList<>(getPlayers());
		Random rand = new Random();
		Player firstPlayer = playerSelection.get(rand.nextInt(playerSelection.size()));
		firstPlayer.setPlayerState(EPlayerGameState.MustAct);
	}

	public void setLoser(Player loser)  {
		Optional<Player> possibleWinner = getSecondPlayer(loser.getPlayerID());
		if(possibleWinner.isPresent()) {
			loser.setPlayerState(EPlayerGameState.Lost);
			possibleWinner.get().setPlayerState(EPlayerGameState.Won);
		}
	}
	
	public Optional<Player> getSecondPlayer(UniquePlayerIdentifier firstPlayerID) {
		return players.stream().filter(player -> !player.getPlayerID().equals(firstPlayerID)).findFirst();
	}
	
	private void updateGameStateID() {
		gameStateId = RandomStringUtils.randomAlphanumeric(5);
		//gameStateId = UUID.randomUUID().toString();
	}

	private void changePlayerTurn() { 
		for(Player player: getPlayers()) {
			if(player.getState().equals(EPlayerGameState.MustAct)) {
				player.setPlayerState(EPlayerGameState.MustWait);
			}
			else if(player.getState().equals(EPlayerGameState.MustWait)) {
				player.setPlayerState(EPlayerGameState.MustAct);
			}
		}	
	}

	public void receiveHalfMap(PlayerHalfMap halfMap, Player mapOwner) {
			
			if(mapOwner.getState().equals(EPlayerGameState.MustAct)) {
				
			Set<PlayerHalfMapNode> nodes = (Set<PlayerHalfMapNode>) halfMap.getMapNodes();
			
			mapOwner.setMap(nodes);
			mapOwner.setSentHalfMap(true);
			changePlayerTurn();
			
			}	
			updateGameStateID();
		
	}

}
