package map.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import map.Position;
import map.enumerations.ETerrainClient;

class WaterEdgesValidationTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void isValidated_WaterEdges_True() {
		
		WaterEdgesValidation validation = new WaterEdgesValidation();
		Map<Position, ETerrainClient> terrain = new HashMap<>();
		
		//Setting up water edges correctly.
		terrain.put(new Position(0,0), ETerrainClient.Water);
		terrain.put(new Position(0,4), ETerrainClient.Water);
		terrain.put(new Position(9,0), ETerrainClient.Water);
		terrain.put(new Position(9,4), ETerrainClient.Water);
		
		boolean isValidated = validation.isValidated(terrain);
		assertTrue(isValidated);
	}
	
	@Test
	void isValidated_WaterEdges_False() {
		
		WaterEdgesValidation validation = new WaterEdgesValidation();
		Map<Position, ETerrainClient> terrain = new HashMap<>();
		
		//Setting up water edges incorrectly.
		terrain.put(new Position(0,0), ETerrainClient.Water);
		terrain.put(new Position(0,1), ETerrainClient.Water);
		terrain.put(new Position(0,2), ETerrainClient.Water);
		terrain.put(new Position(0,4), ETerrainClient.Water);
		terrain.put(new Position(9,0), ETerrainClient.Water);
		terrain.put(new Position(9,4), ETerrainClient.Water);
		
		boolean isValidated = validation.isValidated(terrain);
		assertTrue(isValidated);
	}

}
