package client.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.FailedExecutionException;
import execute.Controller;
import game.GameData;
import game.GameModel;
import network.*;
import ui.CLI;

public class MainClient {
	
	private static final Logger logger = LoggerFactory.getLogger(MainClient.class);
	
	public static void main(String[] args) {

		String serverBaseUrl = args[1];
		String gameId = args[2];
		
		ClientNetworkConverter converter = new ClientNetworkConverter();
		ClientNetwork network = new ClientNetwork(serverBaseUrl, gameId, converter);
		GameData gameData = new GameData();
		GameModel model = new GameModel(gameData);
		Controller controller = new Controller(network, model);
		CLI view = new CLI(model);
		
		try {
		controller.executeGame();
		} catch (FailedExecutionException e) {
			logger.error("Game execution failed.");
			System.exit(1);
		}
		
		logger.info("Game executed.");
	
	}
	
}
