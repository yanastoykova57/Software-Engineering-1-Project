package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import map.enumerations.EFortStateClient;
import map.enumerations.EPlayerPositionStateClient;
import map.enumerations.ETerrainClient;

public class FullMapGeneratorTesting {
	
	public static FullMapClient generateFullMap(boolean toChooseCurrentPosition) {
		Map<Position, FullMapNodeClient> terrain = new HashMap<>();
		
		for(int y=0; y < 10; y++) {  
			for(int x=0; x < 10; x++) {
				
			Position position = new Position(x,y);
			terrain.put(position, new FullMapNodeClient(position, EFortStateClient.NoOrUnknownFortState, 
					EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
			}
		}
		
		if(toChooseCurrentPosition) {
		Position currentPosition = new Position(3,0);
		terrain.put(currentPosition, new FullMapNodeClient(currentPosition, EFortStateClient.NoOrUnknownFortState, 
				EPlayerPositionStateClient.MyPlayerPosition, false, ETerrainClient.Grass));
		} else {
		Position currentPosition = new Position(0,3);
		terrain.put(currentPosition, new FullMapNodeClient(currentPosition, EFortStateClient.NoOrUnknownFortState, 
					EPlayerPositionStateClient.MyPlayerPosition, false, ETerrainClient.Grass));	
		}
		
		addForts(terrain);
		addWater(terrain);
		addMountain(terrain);
		
		return new FullMapClient(terrain);
	}

	private static void addForts(Map<Position, FullMapNodeClient> terrain) {
		Position myFort = new Position(3,1);
		terrain.put(myFort, new FullMapNodeClient(myFort, EFortStateClient.MyFortPresent, 
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
		
		Position enemyFort = new Position(7,9);
		terrain.put(enemyFort, new FullMapNodeClient(enemyFort, EFortStateClient.EnemyFortPresent, 
				EPlayerPositionStateClient.EnemyPlayerPosition, false, ETerrainClient.Grass));
		
	}

	private static void addMountain(Map<Position, FullMapNodeClient> terrain) {
		List<Position> mountainPositions = new ArrayList<>();
		mountainPositions.add(new Position(1,0));
		mountainPositions.add(new Position(2,0));
		mountainPositions.add(new Position(8,0));
		mountainPositions.add(new Position(1,4));
		mountainPositions.add(new Position(8,4));
		mountainPositions.add(new Position(1,5));
		mountainPositions.add(new Position(1,9));
		mountainPositions.add(new Position(2,9));
		mountainPositions.add(new Position(8,5));
		mountainPositions.add(new Position(8,9));

		
		for(Position position: mountainPositions) {
			terrain.put(position, new FullMapNodeClient(position, EFortStateClient.NoOrUnknownFortState, 
					EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Water));
		}
		
	}

	private static void addWater(Map<Position, FullMapNodeClient> terrain) {
		List<Position> waterPositions = new ArrayList<>();
		waterPositions.add(new Position(0,0));
		waterPositions.add(new Position(0,4));
		waterPositions.add(new Position(9,0));
		waterPositions.add(new Position(9,4));
		waterPositions.add(new Position(0,5));
		waterPositions.add(new Position(0,9));
		waterPositions.add(new Position(9,5));
		waterPositions.add(new Position(9,9));
		waterPositions.add(new Position(3,6));
		waterPositions.add(new Position(6,8));
		waterPositions.add(new Position(5,6));
		waterPositions.add(new Position(2,2));
		waterPositions.add(new Position(5,1));
		waterPositions.add(new Position(7,3));
		
		for(Position position: waterPositions) {
			terrain.put(position, new FullMapNodeClient(position, EFortStateClient.NoOrUnknownFortState, 
					EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Water));
		}
	}

}
