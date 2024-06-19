package player;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import map.FullMapGenerator;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromclient.PlayerRegistration;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.PlayerState;

public class Player {
	private final UniquePlayerIdentifier playerID;
	private final String firstName;
	private final String lastName;
	private final String uaccount;
	private EPlayerGameState state;
	private final boolean collectedTreasure;
	private boolean sentHalfMap = false;
	private FullMapGenerator map; 
	
	public Player(PlayerRegistration playerRegistration) {
		this.firstName = playerRegistration.getStudentFirstName();
		this.lastName = playerRegistration.getStudentLastName();
		this.uaccount = playerRegistration.getStudentUAccount();
		this.state = EPlayerGameState.MustWait;
		this.playerID = new UniquePlayerIdentifier(UUID.randomUUID().toString());
		this.collectedTreasure = false;
		this.map = new FullMapGenerator();
	}

	public UniquePlayerIdentifier getPlayerID() {
		return playerID;
	}
	
	public void setPlayerState(EPlayerGameState state) {
		this.state = state;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUaccount() {
		return uaccount;
	}

	public boolean isCollectedTreasure() {
		return collectedTreasure;
	}

	public EPlayerGameState getState() {
		return state;
	}
	
	public PlayerState getPlayerState() {
		return new PlayerState(firstName, lastName, uaccount, state, playerID, collectedTreasure);
	}
	
	public PlayerState getHiddenPlayerState() {
		return new PlayerState(firstName, lastName, uaccount, state, new UniquePlayerIdentifier(UUID.randomUUID().toString()),
				collectedTreasure);
	}

	public boolean hasSentHalfMap() {
		return sentHalfMap;
	}

	public void setSentHalfMap(boolean sentHalfMap) {
		this.sentHalfMap = sentHalfMap;
	}

	public Optional<FullMap> getMap() {
		return Optional.ofNullable(map.getFullMap());
	}

	public void setMap(Set<PlayerHalfMapNode> nodes) {
		this.map.convertToFullMap(nodes);
	}

	
}
