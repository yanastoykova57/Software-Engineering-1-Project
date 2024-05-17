package move;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import map.FullMapClient;
import map.FullMapNodeClient;
import map.enumerations.ETerrainClient;

/*
 * TAKEN FROM <2>
 *  I heavily relied on this implementation of the Dijkstra algorithm: 
 *  https://www.baeldung.com/java-dijkstra
 *  I will provide detailed information about it in my ReadMe document.
*/ 

public class PathFinder {

	private static Logger logger = LoggerFactory.getLogger(PathFinder.class);
	
	public static List<FullMapNodeClient> calculateShortestPath(FullMapNodeClient targetNode,
			FullMapNodeClient source, FullMapClient map) {
		
		source.setDistance(0);
		Set<FullMapNodeClient> settledNodes = new HashSet<>();
		PriorityQueue<FullMapNodeClient> unsettledNodes = new PriorityQueue<>(Comparator.comparingInt(FullMapNodeClient::getDistance));
		
		unsettledNodes.add(source);
		
		while(!unsettledNodes.isEmpty()) {
			
			FullMapNodeClient currentNode = unsettledNodes.poll();
			
			if(currentNode.equals(targetNode)) {
				logger.trace("Target node reached. Building path.");
				return buildPathFromSourceToTarget(source, targetNode);
			}
			
			for(FullMapNodeClient neighbour: NeighborFinder.getAdjacentNodes(currentNode, map)) {
				if(neighbour.getNodeTerrain() != ETerrainClient.Water && !settledNodes.contains(neighbour)) { 
					calculateMinimumDistance(neighbour, currentNode, unsettledNodes);
					unsettledNodes.add(neighbour);
			}
		}
			settledNodes.add(currentNode);	
	}
		
		return new ArrayList<>();
	}
	

	private static List<FullMapNodeClient> buildPathFromSourceToTarget(FullMapNodeClient source, FullMapNodeClient targetNode) {
		List<FullMapNodeClient> path = new ArrayList<>();
		FullMapNodeClient currentNode = targetNode;
		
		while(!currentNode.equals(source)) {
			path.add(0,currentNode);
			currentNode = currentNode.getParentNode();
		}
		path.add(0, source);
		return path;
	}

	
	private static void calculateMinimumDistance(FullMapNodeClient neighbour, FullMapNodeClient currentNode, PriorityQueue<FullMapNodeClient> unsettledNodes) {
		int cost = neighbour.getTerrainCost();
		int newDistance = currentNode.getDistance()+cost;
		if(newDistance < neighbour.getDistance()) { 
			neighbour.updateDistance(newDistance);
			neighbour.setParentNode(currentNode);
		}
	}
	
}

