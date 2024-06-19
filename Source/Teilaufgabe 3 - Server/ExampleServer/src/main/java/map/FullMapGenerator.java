package map;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromserver.EFortState;
import messagesbase.messagesfromserver.EPlayerPositionState;
import messagesbase.messagesfromserver.ETreasureState;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.FullMapNode;

public class FullMapGenerator {
	//private final static Logger logger = LoggerFactory.getLogger(FullMapGenerator.class);
	
	private Set<FullMapNode> mapNodes; 
	private static Random rand = new Random();
	
	private static final int X_SHIFT_NODE_COUNT = 10; // represents with how many nodes X shifts when combining maps
	private static final int Y_SHIFT_NODE_COUNT = 5;  // represents with how many nodes Y shifts when combining maps
	private static final int MAX_ENEMY_X = 9;
	private static final int MAX_ENEMY_Y = 4;
	
	public FullMapGenerator() {
		this.mapNodes = new HashSet<FullMapNode>();
	}
	
	
	public FullMap convertToFullMap(Set<PlayerHalfMapNode> playerNodes) {
		this.mapNodes = convertNodes(playerNodes); 
		return new FullMap(new HashSet<>(this.mapNodes));
	}
	
	public FullMap getFullMap() {
		return new FullMap(new HashSet<>(this.mapNodes));
	}

	private static Set<FullMapNode> convertNodes(Set<PlayerHalfMapNode> playerNodes) {
		Set<FullMapNode> fullMapNodes = new HashSet<>();
		
		EFortState fortState = EFortState.NoOrUnknownFortState;
		EPlayerPositionState positionState = EPlayerPositionState.NoPlayerPresent;
		ETreasureState treasure = ETreasureState.NoOrUnknownTreasureState;
		
		for(PlayerHalfMapNode node: playerNodes) {
			
				if(node.isFortPresent()) {
					fullMapNodes.add(new FullMapNode(node.getTerrain(), EPlayerPositionState.MyPlayerPosition, 
							treasure, EFortState.MyFortPresent, node.getX(), node.getY()));
				} else {
			
				fullMapNodes.add(new FullMapNode(node.getTerrain(), positionState, 
						treasure, fortState, node.getX(), node.getY()));
				}
				
			}
			
		return fullMapNodes;
	}


	public static FullMap combineMaps(FullMap firstMap, FullMap secondMap) {
		boolean combineHorizontal = rand.nextBoolean();
		Set<FullMapNode> combinedNodes = new HashSet<>(firstMap.getMapNodes());
		
		
		EFortState fortState = EFortState.NoOrUnknownFortState;
		EPlayerPositionState positionState = EPlayerPositionState.NoPlayerPresent;
		ETreasureState treasure = ETreasureState.NoOrUnknownTreasureState;
		
		int randomEnemyPositionX = rand.nextInt(MAX_ENEMY_X);
		int randomEnemyPositionY = rand.nextInt(MAX_ENEMY_Y);
		
		if(combineHorizontal) {

			for(FullMapNode node: secondMap.getMapNodes()) {
				
				if(node.getX() == randomEnemyPositionX && node.getY() == randomEnemyPositionY) {
					combinedNodes.add(new FullMapNode(node.getTerrain(), EPlayerPositionState.EnemyPlayerPosition, 
							treasure, fortState, node.getX()+X_SHIFT_NODE_COUNT, node.getY()));
				} else {
			
				combinedNodes.add(new FullMapNode(node.getTerrain(), positionState, 
						treasure, fortState, node.getX()+X_SHIFT_NODE_COUNT, node.getY()));
				}
			}
			
		} else {
			
			for(FullMapNode node: secondMap.getMapNodes()) {
				
				if(node.getX() == randomEnemyPositionX && node.getY() == randomEnemyPositionY) {
					combinedNodes.add(new FullMapNode(node.getTerrain(), EPlayerPositionState.EnemyPlayerPosition, 
							treasure, fortState, node.getX(), node.getY()+Y_SHIFT_NODE_COUNT));
				} else {
			
				combinedNodes.add(new FullMapNode(node.getTerrain(), positionState, 
						treasure, fortState, node.getX(), node.getY()+Y_SHIFT_NODE_COUNT));
				}
			}
		}
		return new FullMap(combinedNodes);
	}

}
