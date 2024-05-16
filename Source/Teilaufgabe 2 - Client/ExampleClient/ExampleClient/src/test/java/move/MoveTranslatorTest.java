package move;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.UndeterminedMoveDirectionException;
import map.FullMapNodeClient;
import map.Position;
import map.enumerations.EFortStateClient;
import map.enumerations.EPlayerPositionStateClient;
import map.enumerations.ETerrainClient;
import move.enumeration.EMoveClient;

class MoveTranslatorTest {

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

	@Test
	void translateMovesForServer_WhenTranslate_ThenDetermineCorrectly() {
		List<FullMapNodeClient> testList = new ArrayList<>();
		testList.add(new FullMapNodeClient(new Position(0,0), EFortStateClient.NoOrUnknownFortState,
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
		testList.add(new FullMapNodeClient(new Position(1,0), EFortStateClient.NoOrUnknownFortState,
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
		testList.add(new FullMapNodeClient(new Position(1,1), EFortStateClient.NoOrUnknownFortState,
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
		testList.add(new FullMapNodeClient(new Position(1,2), EFortStateClient.NoOrUnknownFortState,
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
		testList.add(new FullMapNodeClient(new Position(2,2), EFortStateClient.NoOrUnknownFortState,
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Mountain));
		testList.add(new FullMapNodeClient(new Position(1,2), EFortStateClient.NoOrUnknownFortState,
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
		testList.add(new FullMapNodeClient(new Position(1,1), EFortStateClient.NoOrUnknownFortState,
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
		
		
		Queue<EMoveClient> assertionResult = new LinkedList<>();
		assertionResult.add(EMoveClient.Right);
		assertionResult.add(EMoveClient.Right);
		assertionResult.add(EMoveClient.Down);
		assertionResult.add(EMoveClient.Down);
		assertionResult.add(EMoveClient.Down);
		assertionResult.add(EMoveClient.Down);
		assertionResult.add(EMoveClient.Right);
		assertionResult.add(EMoveClient.Right);
		assertionResult.add(EMoveClient.Right);	
		assertionResult.add(EMoveClient.Left);
		assertionResult.add(EMoveClient.Left);
		assertionResult.add(EMoveClient.Left);	
		assertionResult.add(EMoveClient.Up);
		assertionResult.add(EMoveClient.Up);
		
		Queue<EMoveClient> mockTranslateMovesForServer = MoveTranslator.translateMovesForServer(testList);
		
		assertEquals(assertionResult,mockTranslateMovesForServer);
	}
	
	@Test
	void translateMovesForServer_WhenWrongMove_ThenCatchException() {
		List<FullMapNodeClient> testList = new ArrayList<>();
		testList.add(new FullMapNodeClient(new Position(0,0), EFortStateClient.NoOrUnknownFortState,
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
		testList.add(new FullMapNodeClient(new Position(3,5), EFortStateClient.NoOrUnknownFortState,
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
		
		
		assertThrows(UndeterminedMoveDirectionException.class, () -> {
			MoveTranslator.translateMovesForServer(testList);
		});
		
		
	}
	

}
