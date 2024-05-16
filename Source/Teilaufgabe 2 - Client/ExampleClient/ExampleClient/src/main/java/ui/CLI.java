package ui;

import java.beans.PropertyChangeListener;

import game.GameData;
import game.GameModel;

public class CLI {
	
	private GameModel model;

	public CLI(GameModel model) {
		this.model = model;
		
		PropertyChangeListener listener = event -> {
			if("gameData".equals(event.getPropertyName())) {
				this.displayGameData((GameData) event.getNewValue());
			}
		};
		this.model.addPropertyChangeListener(listener);
	}

	private void displayGameData(GameData gameData) {
		
		String output = "\n";
		
		output += "Treasure State: ";
		
		if(gameData.getHasCollectedTreasure()) output += "You took your treasure! Congrats!\n\n";
		else output += "Treasure is yet to be found. Good luck on your quest!\n\n";
		
		output += "Current Position(Coordinates, Terrain type): " + gameData.getCurrentPositionCoordinates() + ", " +
				gameData.getCurrentPositionTerrain()+ "\n\n";
		
		if(gameData.playerHasWon()) output += "YAY! You just WON!\n";
		if(gameData.playerHasLost()) output += "Sadly, you lost! Good luck on your next try!\n";
		if(!gameData.playerHasWon() && !gameData.playerHasLost()) output += "Winner and loser are undetermined. Let's keep going!\n\n";
			
		output += "----------------Current Map state----------------\n";
		System.out.println(output);
		showMapKey();
		simulateCurrentMapState(gameData);
	}
	
	private void showMapKey() {
		String output = "";
		
		output += "G                                     GRASS FIELDS\n";
		output += "W                                     WATER FIELDS\n";
		output += "M                                  MOUNTAIN FIELDS\n";
		output += "*P*                             MY PLAYER POSITION\n";
		output += "*E*                                 ENEMY POSITION\n";
		output += "*EP*                          BOTH PLAYER POSITION\n";
		output += "FP                            MY FORTRESS POSITION\n";
		
		System.out.println(output);
	}
	
	private void simulateCurrentMapState(GameData gameData) {
		int widthDimension = gameData.getMap().getWidth()+1;
		String output = "\n";
		
		int count = 0;
		for(String terrainField: gameData.getFormattedTerrain()) {
			output += terrainField;
			count++;
			if(count % widthDimension == 0) {
				output += "\n\n";
			}
		}

		System.out.println(output);
	}

}
