package map;

import java.util.Objects;

import map.enumerations.EFortStateClient;
import map.enumerations.EPlayerPositionStateClient;
import map.enumerations.ETerrainClient;

public class FullMapNodeClient {
	
	private final Position position;
	private final EFortStateClient fortState;
	private final EPlayerPositionStateClient positionState;
	private final boolean isTreasureFound;
	private final ETerrainClient nodeTerrain;
	private FullMapNodeClient parentNode;	
	///Dijkstra
	private int distance;

	public FullMapNodeClient(Position position, EFortStateClient fortState,
			EPlayerPositionStateClient positionState, boolean isTreasureFound, ETerrainClient nodeTerrain) {
		this.position = position;
		this.fortState = fortState;
		this.positionState = positionState;
		this.isTreasureFound = isTreasureFound;
		this.nodeTerrain = nodeTerrain;
		//Dijkstra
		this.distance = Integer.MAX_VALUE;
		this.parentNode = null;
	}
	
	
	public ETerrainClient getNodeTerrain() {
		return nodeTerrain;
	}

	 public Position getFullMapPosition() {
		return position;
	}
	 
	 public int getNodeX() {
		 return position.getX();
	 }
	 
	 public int getNodeY() {
		 return position.getY();
	 }

	public EFortStateClient getFortState() {
		return fortState;
	}


	public boolean isTreasureFound() {
		return isTreasureFound;
	}


	EPlayerPositionStateClient getPositionState() {
		return positionState;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public void setParentNode(FullMapNodeClient parentNode) {
		this.parentNode = parentNode;
	}
	
	public FullMapNodeClient getParentNode() {
		return parentNode;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public String getPositionString() {
		return getFullMapPosition().formatPosition();
	}
	
	public String getTerrainString() {
		return getNodeTerrain().formatTerrain();
	}
	
	public int getTerrainCost() {
		return getNodeTerrain().getCost();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fortState, isTreasureFound, position, positionState);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FullMapNodeClient other = (FullMapNodeClient) obj;
		return fortState == other.fortState && isTreasureFound == other.isTreasureFound
				&& Objects.equals(position, other.position) && positionState == other.positionState;
	}


	public void updateDistance(Integer newDistance) {
		if (newDistance < this.distance) {
			setDistance(newDistance);
		}
		
	}
	
}
