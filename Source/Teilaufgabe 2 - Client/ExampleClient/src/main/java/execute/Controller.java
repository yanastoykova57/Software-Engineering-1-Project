package execute;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.FailedExecutionException;
import game.GameData;
import game.GameModel;
import map.FullMapNodeClient;
import map.HalfMap;
import map.HalfMapGenerator;
import move.MoveGenerator;
import move.enumeration.EMoveClient;
import network.ClientNetwork;

public class Controller {
	
		private static final Logger logger = LoggerFactory.getLogger(Controller.class);

		private final ClientNetwork network;
		private final GameModel model;
		private GameData currentGameData;
		private final Set<FullMapNodeClient> visitedNodes;
		
		public Controller(ClientNetwork network, GameModel model) {
			this.network = network;
			this.model = model;
			this.currentGameData = new GameData();
			this.visitedNodes = new HashSet<>();
		}
		
		public void executeGame() throws FailedExecutionException {
			
			try {
			// 1. Register the player.
			registerPlayer();
			logger.info("Player is registered.");
			
			// 2. Generate the HalfMap and send it.
			sendHalfMap();
			logger.info("HalfMap is sent.");
			
			// 3. Receive the FullMap
			receiveMap();
			logger.info("FullMap is received.");
			
			// 4. Move	
			logger.info("Starting to make moves.");
			processMoves(); 
			
			//5. Terminate game
			logger.info("Terminating game.");
			terminateGame();
			
			} catch (Exception e) {
				throw new FailedExecutionException("Error during game execution.");
			}
			
		}
		
		private void processMoves() {
			Queue<EMoveClient> moves = new LinkedList<>();
			while(isGameUndetermined()) {
				
				while(!network.mustAct() && isGameUndetermined()) {}
				updateModel();
				
				if(!isGameUndetermined()) { 
					logger.debug("Breaking out of loop...");
					break;
					}
				
				if(moves.isEmpty()) {
					moves.addAll(MoveGenerator.generateMoves(currentGameData, visitedNodes));
				}
				EMoveClient moveSent = moves.poll();
	
				network.sendMove(moveSent);
				updateModel();
			}
		}

		public void registerPlayer() {
			network.registerPlayer();
		}
		
		private void sendHalfMap() {
			HalfMap newHalfMap = HalfMapGenerator.generateHalfMap();
			while(!network.mustAct()) {}
			network.sendHalfMap(newHalfMap);
		}
		
		private void updateModel() {
			this.currentGameData = network.getGameData();
			model.setGameData(currentGameData);
			logger.debug("Model updated.");
		}
		
		private void receiveMap() {
			network.receiveFullMap();
		}
		
		private void terminateGame() {
			if(currentGameData.playerHasWon()) {
				logger.info("Player has won.");
				System.exit(0);
			} else if (currentGameData.playerHasLost()) {
				logger.info("Player has lost.");
				System.exit(0);
			} else {
				logger.debug("Something is wrong. Need to figure out why.");
				System.exit(1);
			}
		}
		
		private boolean isGameUndetermined() {
			return !network.hasLost() && !network.hasWon();
		}
	
}