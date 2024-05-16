package network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import game.GameData;
import game.gameEnumerations.EPlayerGameStateClient;
import map.FullMapClient;
import map.FullMapNodeClient;
import map.HalfMap;
import map.Position;
import map.enumerations.EFortStateClient;
import map.enumerations.EPlayerPositionStateClient;
import map.enumerations.ETerrainClient;
import messagesbase.messagesfromclient.EMove;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromserver.EFortState;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.EPlayerPositionState;
import messagesbase.messagesfromserver.ETreasureState;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.FullMapNode;
import messagesbase.messagesfromserver.GameState;
import messagesbase.messagesfromserver.PlayerState;
import move.enumeration.EMoveClient;

public class ClientNetworkConverter {	
	
	/*
	 * TAKEN FROM <1>
	 *  Using valueOf() to convert enumerations: 
	 *  https://www.baeldung.com/java-convert-enums (3.4 Using Enum Name)
	 *  I wanted less code and better readability. This is a solution I found online.
	*/ 
	
	private static <T extends Enum<T>> T convertEnum(Class<T> enumClass, Enum<?> value) {
		return Optional.ofNullable(value).map(Enum::name)
				.map(enumName -> Enum.valueOf(enumClass, enumName)).orElse(null);
	}
	
	private static ETerrain convertClientETerrainToServerETerrain(ETerrainClient terrain) {
		return convertEnum(ETerrain.class, terrain);
	}
	
	private static ETerrainClient convertServerETerrainToClientETerrain(ETerrain terrain) {
		return convertEnum(ETerrainClient.class, terrain);
	}
	
	private static EFortStateClient convertServerEFortStateToClientEFortState(EFortState fortState) {
		return convertEnum(EFortStateClient.class, fortState);
	}
	
	private static EPlayerPositionStateClient convertServerEPositionStateToClientEPositionState(EPlayerPositionState positionState) {
		return convertEnum(EPlayerPositionStateClient.class, positionState);
	}
	
	private static EPlayerGameStateClient convertToClientEPlayerGameState(EPlayerGameState playerState) {
		return convertEnum(EPlayerGameStateClient.class, playerState);
	}
	
	EMove convertClientEMoveToServerEMove(EMoveClient move) {
		return convertEnum(EMove.class, move);
	}
	
	/*
	 * TAKEN FROM END <1>
	 */
	
	private static boolean convertToClientTreasureState(ETreasureState treasureState) {
		switch(treasureState) {
		case MyTreasureIsPresent:
			return true;
		case NoOrUnknownTreasureState:
			return false;
		default:
			return false;
		}
	}	
	
	private static Collection<? extends PlayerHalfMapNode> convertClientHalfMapNodesToServerHalfMapNodes (HalfMap halfMap) {
		
		List<PlayerHalfMapNode> nodeList= new ArrayList<PlayerHalfMapNode>();
		
		Map<Position, ETerrainClient> terrain = halfMap.getTerrain();
		
		for(Map.Entry<Position, ETerrainClient> n: terrain.entrySet()) {
			Position posNode = n.getKey();
	        messagesbase.messagesfromclient.ETerrain terrainNode = convertClientETerrainToServerETerrain(n.getValue());
			
			boolean fortPresent = halfMap.getFortress().equals(posNode); //??
			PlayerHalfMapNode nodeEntity = new PlayerHalfMapNode(posNode.getX(), posNode.getY(), fortPresent, terrainNode);
			nodeList.add(nodeEntity);
		}
		
		return nodeList;
		
	}
	
	GameData convertServerGameStateToGameData(GameState gameState, String playerID)  {
		//map
		FullMap fullMapServer = gameState.getMap();
		FullMapClient fullMap = convertServerFullMapToClientFullMap(fullMapServer);
		
		//gameStateID
		String gameStateID = gameState.getGameStateId();
		
		//playerState & collectedTreasure
		EPlayerGameState playerGameState = null;
		boolean collectedTreasure = false;
		for(PlayerState state: gameState.getPlayers()) {
			 if(state.getUniquePlayerID().equals(playerID)) {
				 playerGameState = state.getState();
				 collectedTreasure = state.hasCollectedTreasure();
			 }
		 }
		
		EPlayerGameStateClient playerState = convertToClientEPlayerGameState(playerGameState);
		
		return new GameData(gameStateID, fullMap, collectedTreasure, playerState);
	}
	
	
	PlayerHalfMap convertClientHalfMaptoServerHalfMap(final HalfMap halfMap, String playerID) {
		Set<PlayerHalfMapNode> nodes = new HashSet<PlayerHalfMapNode>(convertClientHalfMapNodesToServerHalfMapNodes(halfMap));
		return new PlayerHalfMap(playerID, nodes);
	}

	
	private static FullMapClient convertServerFullMapToClientFullMap (FullMap fullMap){
		Collection<FullMapNode> nodes = fullMap.getMapNodes();
		Map<Position, FullMapNodeClient> terrain = new HashMap<>();
		
		for(FullMapNode node: nodes) {
			ETerrainClient nodeTerrain = convertServerETerrainToClientETerrain(node.getTerrain());
			EPlayerPositionStateClient positionState = convertServerEPositionStateToClientEPositionState(node.getPlayerPositionState());
			EFortStateClient fortState = convertServerEFortStateToClientEFortState(node.getFortState());
			boolean treasureState = convertToClientTreasureState(node.getTreasureState());
			Position nodePosition = new Position(node.getX(), node.getY());
			
			FullMapNodeClient convertedNode = new FullMapNodeClient(nodePosition, fortState, positionState, treasureState, nodeTerrain);
			
			terrain.put(nodePosition, convertedNode);
		}
		
		return new FullMapClient(terrain);
	}
	
}
