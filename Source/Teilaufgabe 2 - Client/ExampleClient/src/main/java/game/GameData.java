package game;

import java.util.List;
import java.util.Optional;

import game.gameEnumerations.EPlayerGameStateClient;
import map.FullMapClient;
import map.FullMapNodeClient;
import map.enumerations.EFortStateClient;

public class GameData {
	
	private final String gameStateID;
	private final FullMapClient fullMap;
	private final boolean hasCollectedTreasure;
	private final EPlayerGameStateClient playerGameState;
	
	public GameData() {
		this.gameStateID = "";
		this.fullMap = null;
		this.hasCollectedTreasure = false;
		this.playerGameState = null;
	}
	
	public GameData(String gameStateID, FullMapClient fullMap, boolean collectedTreasure,
			EPlayerGameStateClient playerState) {
		this.gameStateID = gameStateID;
		this.fullMap = fullMap;
		this.hasCollectedTreasure = collectedTreasure;
		this.playerGameState = playerState;
	}

	public String getGameStateID() {
		return gameStateID;
	}

	public FullMapClient getMap() {
		return fullMap;
	}

	public boolean getHasCollectedTreasure() {
		return hasCollectedTreasure;
	}

	public EPlayerGameStateClient getPlayerGameState() {
		return playerGameState;
	}
	
	public boolean playerHasWon() {
		return playerGameState.equals(EPlayerGameStateClient.Won);
	}
	
	public boolean playerHasLost() {
		return playerGameState.equals(EPlayerGameStateClient.Lost);
	}
	
	
	public Optional<FullMapNodeClient> getCurrentNode() {
		return fullMap.getCurrentPosition();
	}
	
	public Optional<FullMapNodeClient> getMyFortNode() {
		for(FullMapNodeClient node: getMyHalfMap()) {
			if(node.getFortState().equals(EFortStateClient.MyFortPresent)) {
				return Optional.of(node);
			}
		}
		return Optional.empty();
	}
	
	public String getCurrentPositionCoordinates() {
		Optional<FullMapNodeClient> optionalCurrentNode = getCurrentNode();
		return optionalCurrentNode.map(FullMapNodeClient::getPositionString).orElse("Null position."); 
	}
	
	public String getCurrentPositionTerrain() {
		Optional<FullMapNodeClient> optionalCurrentNode = getCurrentNode();
		return optionalCurrentNode.map(FullMapNodeClient::getTerrainString).orElse("Null terrain."); 
	}
	
	public List<String> getFormattedTerrain() {
		return fullMap.formatTerrain();
	}
	
	public List<FullMapNodeClient> getMyHalfMap() {
		return fullMap.getMyHalfMap();
	}
	
	public List<FullMapNodeClient> getEnemyHalfMap() {
		return fullMap.getEnemysHalfMap();
	}

}
