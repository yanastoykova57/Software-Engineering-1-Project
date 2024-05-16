package map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import map.enumerations.EFortStateClient;
import map.enumerations.EPlayerPositionStateClient;
import map.enumerations.ETerrainClient;


public class FullMapClient {
	private static final Logger logger = LoggerFactory.getLogger(FullMapClient.class);

	private final Map<Position, FullMapNodeClient> fullMapTerrain;
	private final int width;
	private final int height;

	private Optional<List<FullMapNodeClient>> myHalfMap = Optional.empty(); 
	private Optional<List<FullMapNodeClient>> enemysHalfMap = Optional.empty(); 
	
	public FullMapClient(Map<Position, FullMapNodeClient> fullMapTerrain) {
		this.fullMapTerrain = fullMapTerrain;
		this.width = calculateWidth();
		this.height = calculateHeight();
		if(height > 9 || width > 19) {
			logger.error("FullMap dimesions don't match documentation.");
		}
	}

	private int calculateHeight() {
		return fullMapTerrain.values().stream()
				.mapToInt(FullMapNodeClient::getNodeY).max()
				.orElse(0);
	}

	private int calculateWidth() {
		return fullMapTerrain.values().stream()
				.mapToInt(FullMapNodeClient::getNodeX).max()
				.orElse(0);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Map<Position, FullMapNodeClient> getMap() {
		return fullMapTerrain;
	}
	
	public List<FullMapNodeClient> getNodes() {
		return new ArrayList<>(fullMapTerrain.values());
	}
	
	public FullMapNodeClient getNode(Position position) {
		return fullMapTerrain.get(position);
	}
	
	public List<FullMapNodeClient> getMyHalfMap() { 
		return myHalfMap.orElseGet(() -> {
			List<FullMapNodeClient> calculatedHalfMap = calculateMyHalfMap();
			myHalfMap = Optional.of(calculatedHalfMap);
			return calculatedHalfMap;
		});
	}
	
	public List<FullMapNodeClient> getEnemysHalfMap() { 
		return enemysHalfMap.orElseGet(() -> {
			List<FullMapNodeClient> calculatedHalfMap = calculateEnemysHalfMap();
			enemysHalfMap = Optional.of(calculatedHalfMap);
			return calculatedHalfMap;
		});
	}
	
	
	private List<FullMapNodeClient> calculateMyHalfMap() { 
		
		Optional<FullMapNodeClient> optionalMyFort = getMyFort();
		List<FullMapNodeClient> myHalfMapNodes = new ArrayList<>();
		
		if(optionalMyFort.isPresent()) {
			FullMapNodeClient myFort = optionalMyFort.get();
			int sourceX = (myFort.getNodeX() < 10) ? 0 : 10;
			int limitX = (sourceX == 0) ? 10 : 20;
			int sourceY = (myFort.getNodeY() < 5) ? 0 : 5;
			int limitY = (sourceY == 0) ? 5 : 10;
			
			for (int y = sourceY; y < limitY; y++ ) {
				for (int x = sourceX; x < limitX; x++ ) {
					myHalfMapNodes.add(getNode(new Position(x,y)));
				}
			}
		}
		
		return myHalfMapNodes;
	}
	
	private List<FullMapNodeClient> calculateEnemysHalfMap() { 
		List<FullMapNodeClient> enemysHalfMapNodes = new ArrayList<>(getNodes());
		enemysHalfMapNodes.removeAll(getMyHalfMap());
		return enemysHalfMapNodes;
	}
	
	Optional<FullMapNodeClient> getMyFort() {
		for(FullMapNodeClient node: fullMapTerrain.values()) {
			if(node.getFortState() == EFortStateClient.MyFortPresent) return Optional.of(node);
		}
		logger.warn("Player's fort node not found!");
		return Optional.empty();
	}
	
	public Optional<FullMapNodeClient> getCurrentPosition() {
		for(FullMapNodeClient node: fullMapTerrain.values()) {
			if(node.getPositionState() == EPlayerPositionStateClient.BothPlayerPosition ||
					node.getPositionState() == EPlayerPositionStateClient.MyPlayerPosition) 
				return Optional.of(node);
		}
		logger.warn("Current position node not found!");
		return Optional.empty();
	}

	public List<String> formatTerrain() {
		List<String> formattedTerrain = new ArrayList<>();
		
		for(int y = 0; y < getHeight()+1; y++) {
			for(int x = 0; x < getWidth()+1; x++) {
				FullMapNodeClient node = getNode(new Position(x,y));
				if(node.getPositionState().equals(EPlayerPositionStateClient.NoPlayerPresent)) {
					if(node.getNodeTerrain().equals(ETerrainClient.Grass)) formattedTerrain.add("    G    ");
					if(node.getNodeTerrain().equals(ETerrainClient.Mountain)) formattedTerrain.add("    M    ");
					if(node.getNodeTerrain().equals(ETerrainClient.Water)) formattedTerrain.add("    W    ");
				} else {
					if(node.getPositionState().equals(EPlayerPositionStateClient.MyPlayerPosition)) {
						formattedTerrain.add("   *P*   ");
					}
					if(node.getPositionState().equals(EPlayerPositionStateClient.EnemyPlayerPosition)) {
						formattedTerrain.add("   *E*   ");
					}
					if(node.getPositionState().equals(EPlayerPositionStateClient.BothPlayerPosition)) {
						formattedTerrain.add("   *EP*   ");
					}
				}		
			}
		}
		
		return formattedTerrain;
	}
}
