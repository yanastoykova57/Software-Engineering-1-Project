package map.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import map.Position;
import map.enumerations.ETerrainClient;

class IslandValidationTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void isValidated_Islands_False() {
		
		IslandValidation validation = new IslandValidation();
		Map<Position, ETerrainClient> terrain = new HashMap<>();
		
		for(int y=0; y < 5; y++) {  
			for(int x=0; x < 10; x++) {
				terrain.put(new Position(x,y), ETerrainClient.Grass);
			}
		}
		
		terrain.put(new Position(3,0), ETerrainClient.Water);
		terrain.put(new Position(5,0), ETerrainClient.Water);
		terrain.put(new Position(4,1), ETerrainClient.Water);
		
		boolean isValidated = validation.isValidated(terrain);
		assertTrue(isValidated);
		
	}

}
