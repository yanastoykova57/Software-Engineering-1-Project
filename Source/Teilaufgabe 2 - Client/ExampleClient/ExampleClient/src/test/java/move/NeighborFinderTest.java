package move;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import map.*;
import map.enumerations.EFortStateClient;
import map.enumerations.EPlayerPositionStateClient;
import map.enumerations.ETerrainClient;
class NeighborFinderTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	private FullMapClient createMap() {
		Map<Position, FullMapNodeClient> terrain = new HashMap<>();
		for(int y=0; y < 10; y++) {  
			for(int x=0; x < 10; x++) {
				terrain.put(new Position(x,y), new FullMapNodeClient(new Position(x,y),
				EFortStateClient.NoOrUnknownFortState,EPlayerPositionStateClient.NoPlayerPresent, 
				false, ETerrainClient.Grass));
			}
		}
		

		return new FullMapClient(terrain);
	}

	@Test
	void getAdjacentNodes_WhenCurrentNodeProvided_ThenReturnCorrectNeighbours() {
		FullMapNodeClient currentNode = new FullMapNodeClient(new Position(5,5),
				EFortStateClient.NoOrUnknownFortState,EPlayerPositionStateClient.NoPlayerPresent, 
				false, ETerrainClient.Grass);
		
		FullMapClient map = createMap();
		
		List<FullMapNodeClient> assertionNodes = Arrays.asList(
		new FullMapNodeClient(new Position(4,5),EFortStateClient.NoOrUnknownFortState,EPlayerPositionStateClient.NoPlayerPresent, 
			false, ETerrainClient.Grass),
		new FullMapNodeClient(new Position(6,5),EFortStateClient.NoOrUnknownFortState,EPlayerPositionStateClient.NoPlayerPresent, 
				false, ETerrainClient.Grass),
		new FullMapNodeClient(new Position(5,4),EFortStateClient.NoOrUnknownFortState,EPlayerPositionStateClient.NoPlayerPresent, 
				false, ETerrainClient.Grass),
		new FullMapNodeClient(new Position(5,6),EFortStateClient.NoOrUnknownFortState,EPlayerPositionStateClient.NoPlayerPresent, 
				false, ETerrainClient.Grass)
				);
		
		List<FullMapNodeClient> adjacentNodes = NeighborFinder.getAdjacentNodes(currentNode, map);
		
		assertIterableEquals(assertionNodes, adjacentNodes);
		
	}

}
