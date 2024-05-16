package move;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import game.GameData;
import game.gameEnumerations.EPlayerGameStateClient;
import map.FullMapClient;
import map.FullMapGeneratorTesting;
import map.FullMapNodeClient;
import move.enumeration.EMoveClient;

class MoveGeneratorTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void generateMoves_WhenGivenParameters_ThenGenerateCorrectMoves() {
		FullMapClient map = FullMapGeneratorTesting.generateFullMap(false);
		GameData gameData = new GameData("abcd", map, false, EPlayerGameStateClient.MustAct);
		Set<FullMapNodeClient> visitedNodes = new HashSet<>();
		Queue<EMoveClient> realMoves = MoveGenerator.generateMoves(gameData, visitedNodes);
		Queue<EMoveClient> expectedMoves = new LinkedList<>();
		expectedMoves.add(EMoveClient.Right);
		expectedMoves.add(EMoveClient.Right);
		expectedMoves.add(EMoveClient.Right);
		expectedMoves.add(EMoveClient.Right);
		expectedMoves.add(EMoveClient.Right);
		expectedMoves.add(EMoveClient.Right);
		
		assertIterableEquals(expectedMoves, realMoves);
	}

}
