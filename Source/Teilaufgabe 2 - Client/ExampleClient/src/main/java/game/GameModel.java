package game;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameModel {
	
	private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
	private GameData gameData;
	
	public GameModel(GameData gameData) {
		this.gameData = gameData;
	}
	
	public void setGameData(GameData newGameData) {
		GameData beforeChangeGameData = this.gameData;
		this.gameData = newGameData;
		
		//inform all interested parties about changes
		changes.firePropertyChange("gameData", beforeChangeGameData, newGameData);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		//enables to register new listeners
		changes.addPropertyChangeListener(listener);
	}
}
