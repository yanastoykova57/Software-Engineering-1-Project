package move;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import exceptions.UndeterminedMoveDirectionException;
import map.FullMapNodeClient;
import move.enumeration.EMoveClient;

public class MoveTranslator {

	static Queue<EMoveClient> translateMovesForServer(List<FullMapNodeClient> path) {
		Queue<EMoveClient> translatedMoves = new LinkedList<>();
		for(int index = 0; index < path.size()-1; index++) {
			EMoveClient	eMove = determineMoveDirection(path.get(index), path.get(index+1));
			translatedMoves.addAll(Collections.nCopies(getMovementCost(path.get(index), path.get(index+1)), eMove));
		}
		return translatedMoves;
	}
	
	
	private static int getMovementCost(FullMapNodeClient firstNode, FullMapNodeClient secondNode) {
		return firstNode.getTerrainCost() + secondNode.getTerrainCost();
	}
	

	private static EMoveClient determineMoveDirection(FullMapNodeClient currentPosition, FullMapNodeClient nextNode) {
		if(nextNode.getNodeX() == currentPosition.getNodeX()+1) {return EMoveClient.Right;}
		if(nextNode.getNodeX() == currentPosition.getNodeX()-1) {return EMoveClient.Left;}
		if(nextNode.getNodeY() == currentPosition.getNodeY()-1) {return EMoveClient.Up;}
		if(nextNode.getNodeY() == currentPosition.getNodeY()+1) {return EMoveClient.Down;}
		
		throw new UndeterminedMoveDirectionException("Unexpected direction. Current Node: " 
				+ currentPosition.getPositionString() + ". Trying to reach node: " + nextNode.getPositionString());
	}
	
}

