package move;

import java.util.ArrayList;
import java.util.List;
import map.FullMapClient;
import map.FullMapNodeClient;
import map.Position;

public class NeighborFinder {

	static List<FullMapNodeClient> getAdjacentNodes(FullMapNodeClient currentNode, FullMapClient map) {
		List<FullMapNodeClient> neighbours = new ArrayList<>();
		int currentX = currentNode.getNodeX();
		int currentY = currentNode.getNodeY();
		
		checkPresenceAndAddNode(neighbours, map, currentX-1, currentY);
		checkPresenceAndAddNode(neighbours, map, currentX+1, currentY);
		checkPresenceAndAddNode(neighbours, map, currentX, currentY-1);
		checkPresenceAndAddNode(neighbours, map, currentX, currentY+1);
		
		return neighbours;
	}
	
	
	private static void checkPresenceAndAddNode(List<FullMapNodeClient> neighbors, FullMapClient  map,
			int x, int y) {
		Position position = new Position(x,y);
		
		if (map.getMap().containsKey(position)) {
			neighbors.add(map.getNode(position));
		}
	}
}

