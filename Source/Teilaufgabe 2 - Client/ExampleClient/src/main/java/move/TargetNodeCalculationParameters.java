package move;

import java.util.List;
import java.util.Optional;
import game.GameData;
import map.FullMapClient;
import map.FullMapNodeClient;

public class TargetNodeCalculationParameters {
	private List<FullMapNodeClient> halfMap;
	private Optional<FullMapNodeClient> currentNode;
	private FullMapClient map;

	public TargetNodeCalculationParameters(GameData gameData) {
		this.halfMap = gameData.getHasCollectedTreasure() ? gameData.getEnemyHalfMap() : gameData.getMyHalfMap();
		this.currentNode = gameData.getCurrentNode();
		this.map = gameData.getMap();
	}

	public List<FullMapNodeClient> getHalfMap() {
		return halfMap;
	}

	public Optional<FullMapNodeClient> getCurrentNode() {
		return currentNode;
	}

	public FullMapClient getMap() {
		return map;
	}

}
