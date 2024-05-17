package move;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import exceptions.MissingCurrentNodeException;
import game.GameData;
import map.FullMapNodeClient;
import move.enumeration.EMoveClient;

public class MoveGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(MoveGenerator.class);
	
	public static Queue<EMoveClient> generateMoves(GameData gameData, Set<FullMapNodeClient> visitedNodes) {

		Optional<FullMapNodeClient> myFortNode = gameData.getMyFortNode();
		myFortNode.ifPresent(visitedNodes::add);

		FullMapNodeClient targetNode = TargetFinder.getTargetNode(gameData, visitedNodes);
		visitedNodes.add(targetNode);
		
		
		Optional<FullMapNodeClient> optionalCurrentNode = gameData.getCurrentNode();
		
		if(optionalCurrentNode.isPresent()) {
		FullMapNodeClient currentNode = optionalCurrentNode.get();
		List<FullMapNodeClient> path = PathFinder.calculateShortestPath(targetNode, currentNode,gameData.getMap());
		logger.info("Path calculated.");

		return MoveTranslator.translateMovesForServer(path);
		} else {
			throw new MissingCurrentNodeException();
		}
	}
}



