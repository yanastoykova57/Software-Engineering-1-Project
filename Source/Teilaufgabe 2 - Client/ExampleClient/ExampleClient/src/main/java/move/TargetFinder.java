package move;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import exceptions.MissingCurrentNodeException;
import game.GameData;
import map.FullMapClient;
import map.FullMapNodeClient;
import map.enumerations.ETerrainClient;


public class TargetFinder {

public static FullMapNodeClient getTargetNode(GameData gameData, Set<FullMapNodeClient> visitedNodes) {
	TargetNodeCalculationParameters params = new TargetNodeCalculationParameters(gameData);			
	FullMapNodeClient targetNode = calculateTargetNode(params, visitedNodes);
	return targetNode;	
	}


private static FullMapNodeClient calculateTargetNode(TargetNodeCalculationParameters params, Set<FullMapNodeClient> visitedNodes) {
	
	List<FullMapNodeClient> halfMap = params.getHalfMap();
	FullMapClient map = params.getMap();
	Optional<FullMapNodeClient> optionalCurrentNode = params.getCurrentNode();
	
	FullMapNodeClient currentNode = optionalCurrentNode.orElseThrow(
			() -> new MissingCurrentNodeException());
	
	Set<FullMapNodeClient> unvisitedNodes = new HashSet<>(halfMap);
	unvisitedNodes.removeAll(visitedNodes);
	
	if(unvisitedNodes.isEmpty()) {
		unvisitedNodes.addAll(visitedNodes);
	}

	Set<FullMapNodeClient> grassNodeNeighbours = NeighborFinder.getAdjacentNodes(currentNode, map).stream()
			.filter(node -> node.getNodeTerrain() == ETerrainClient.Grass && unvisitedNodes.contains(node))
			.collect(Collectors.toSet());
	
	//1. scenario
	if(grassNodeNeighbours.size() == 1) {
		return grassNodeNeighbours.iterator().next();
	}
	
	//2. scenario
	
	Set<FullMapNodeClient> grassNodesWithMostGrassNeighbours = getGrassNodesWithMostGrassNeighbours(map, currentNode,
					unvisitedNodes);		
	return determineClosestNode(currentNode, grassNodesWithMostGrassNeighbours, map);
			
	
}
 
/*
 * TAKEN FROM <2>
 * See PathFinder and ReadMe document for further information.
*/ 
private static FullMapNodeClient determineClosestNode(FullMapNodeClient currentPosition,
		Set<FullMapNodeClient> grassNodesWithMostGrassNeighbours, FullMapClient map) {
	FullMapNodeClient closestNode = null;
	double minDistance = Double.MAX_VALUE;
	
	Map<FullMapNodeClient, Integer> grassDistances = new HashMap<>();
	for(FullMapNodeClient grassNode: grassNodesWithMostGrassNeighbours) {
		PathFinder.calculateShortestPath(grassNode, currentPosition, map);
		grassDistances.put(grassNode, grassNode.getDistance());
	}
	for(Map.Entry<FullMapNodeClient, Integer> grassEntry: grassDistances.entrySet()) {
		FullMapNodeClient grassNode = grassEntry.getKey();	
		int distance = grassEntry.getValue();
		if (distance < minDistance) {
			minDistance = distance;
			closestNode = grassNode;
		}
	}
	return closestNode;
}

/*
 * TAKEN FROM END <2>
*/ 

private static Set<FullMapNodeClient> getGrassNodesWithMostGrassNeighbours(FullMapClient map, FullMapNodeClient currentPosition,
		Set<FullMapNodeClient> unvisitedNodes) {
	
	Set<FullMapNodeClient> maxGrassNodes = new HashSet<>();
	int maxGrassNeighbours = 0;
	
	for(FullMapNodeClient node: unvisitedNodes) {
		
		if(node != currentPosition && node.getNodeTerrain() == ETerrainClient.Grass) {
			int grassNeighbours = countUnvisitedGrassNeighbours(node, map, unvisitedNodes);
			if(grassNeighbours >= maxGrassNeighbours) {
				maxGrassNeighbours = grassNeighbours;
				maxGrassNodes.add(node);
			} 
		}
	}
	return maxGrassNodes;
}

private static int countUnvisitedGrassNeighbours(FullMapNodeClient node, FullMapClient map,
		Set<FullMapNodeClient> unvisitedNodes) {
	int count = (int) NeighborFinder.getAdjacentNodes(node, map).stream()
			.filter(n -> n.getNodeTerrain() == ETerrainClient.Grass && unvisitedNodes.contains(n))
			.count();
	
	return count;
}

}
