package move;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.GameData;
import game.gameEnumerations.EPlayerGameStateClient;
import map.FullMapClient;
import map.FullMapGeneratorTesting;
import map.FullMapNodeClient;
import map.Position;
import map.enumerations.EFortStateClient;
import map.enumerations.EPlayerPositionStateClient;
import map.enumerations.ETerrainClient;

class TargetFinderTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	
	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void getTargetNode_WhenOneUnvisitedGrassNeighbour_ThenReturnIt() {
		FullMapClient map = FullMapGeneratorTesting.generateFullMap(true);
		GameData gameData = new GameData("abcd", map, false, EPlayerGameStateClient.MustAct);
		Set<FullMapNodeClient> visitedNodes = new HashSet<>();
		visitedNodes.add(new FullMapNodeClient(new Position(3,1), EFortStateClient.MyFortPresent, 
					EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
		
		FullMapNodeClient target = TargetFinder.getTargetNode(gameData, visitedNodes);
		
		FullMapNodeClient expectedNode = new FullMapNodeClient(new Position(4,0), EFortStateClient.NoOrUnknownFortState, 
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass);
		
		assertEquals(expectedNode, target);
	}
	
	@Test
	void getTargetNode_WhenMoreThanOneUnvisitedGrassNeighbour_ThenReturnClosestGrassThatUncoversMost() {
		FullMapClient map = FullMapGeneratorTesting.generateFullMap(false);
		GameData gameData = new GameData("abcd", map, false, EPlayerGameStateClient.MustAct);
		Set<FullMapNodeClient> visitedNodes = new HashSet<>();
		
		FullMapNodeClient target = TargetFinder.getTargetNode(gameData, visitedNodes);
		
		FullMapNodeClient expectedNode = new FullMapNodeClient(new Position(2,3), EFortStateClient.NoOrUnknownFortState, 
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass);
		
		assertEquals(expectedNode, target);
	}

}
