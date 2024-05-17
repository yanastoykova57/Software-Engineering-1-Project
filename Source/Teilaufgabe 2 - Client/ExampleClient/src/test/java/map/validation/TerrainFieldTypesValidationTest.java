package map.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import map.Position;
import map.enumerations.ETerrainClient;

class TerrainFieldTypesValidationTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void isValidated_TerrainTypeCount_True() {
		int grassFields = 24;
		int mountainFields = 5;
		int waterFields = 7;
		
		TerrainFieldTypesValidation validation = new TerrainFieldTypesValidation();
		Map<Position, ETerrainClient> terrain = new HashMap<>();
		
		//Correctly distributing terrain types.
		for(int y=0; y < 5; y++) {  
			for(int x=0; x < 10; x++) {
				if (grassFields > 0) {
				terrain.put(new Position(x,y), ETerrainClient.Grass);
				grassFields--;
				}
				else if (mountainFields > 0) {
					terrain.put(new Position(x,y), ETerrainClient.Mountain);
					mountainFields--;	
				}
				else if (waterFields > 0) {
					terrain.put(new Position(x,y), ETerrainClient.Water);
					waterFields--;	
				}
			}
		}
		
		boolean isValidated = validation.isValidated(terrain);
		assertTrue(isValidated);
	}
	
	
	@Test
	void isValidated_TerrainTypeCount_False() {
		int grassFields = 20;
		int mountainFields = 3;
		int waterFields = 5;
		
		TerrainFieldTypesValidation validation = new TerrainFieldTypesValidation();
		Map<Position, ETerrainClient> terrain = new HashMap<>();
		
		//Correctly distributing terrain types.
		for(int y=0; y < 5; y++) {  
			for(int x=0; x < 10; x++) {
				if (grassFields > 0) {
				terrain.put(new Position(x,y), ETerrainClient.Grass);
				grassFields--;
				}
				else if (mountainFields > 0) {
					terrain.put(new Position(x,y), ETerrainClient.Mountain);
					mountainFields--;	
				}
				else if (waterFields > 0) {
					terrain.put(new Position(x,y), ETerrainClient.Water);
					waterFields--;	
				}
			}
		}
		
		boolean isValidated = validation.isValidated(terrain);
		assertTrue(isValidated);
	}

}
