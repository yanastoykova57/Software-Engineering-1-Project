package map.enumerations;

import exceptions.InvalidTerrainTypeException;

public enum ETerrainClient {
	Grass(1),
	Mountain(2),
	Water(9999);
	

	private final int cost;
	
	ETerrainClient(int cost) {
		this.cost = cost;
	}
	
	public int getCost() {
		return cost;
	}
	
	public String formatTerrain() {
		switch(this) {
		case Grass:
			return "GRASS";
		case Mountain: 
			return "MOUNTAIN";
		case Water:
			return "WATER";
		default: 
			throw new InvalidTerrainTypeException();
		}
	}
	
	
}

