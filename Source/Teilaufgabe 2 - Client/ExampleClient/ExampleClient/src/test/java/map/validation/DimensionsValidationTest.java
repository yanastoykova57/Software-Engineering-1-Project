package map.validation;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import map.enumerations.*;
import map.Position;
import map.validation.DimensionsValidation;

class DimensionsValidationTest {

		@BeforeAll
		public static void setUpBeforeClass() {
			System.out.println("setUpBeforeClass");
		}

		// MUST BE STATIC!
		@AfterAll
		public static void tearDownAfterClass() {
			System.out.println("tearDownAfterClass");
		}
		

		@Test
		public void isValidated_ValidDimensions_True() {
			
			DimensionsValidation validation = new DimensionsValidation();
			Map<Position, ETerrainClient> terrain = new HashMap<>();
			
			for(int y=0; y < 5; y++) {  
				for(int x=0; x < 10; x++) {
					terrain.put(new Position(x,y), ETerrainClient.Grass);
				}
			}
			
			boolean isValidated = validation.isValidated(terrain);
			assertTrue(isValidated);
		}
		
		
		@Test
		public void isValidated_ValidDimensions_False() {
			
			DimensionsValidation validation = new DimensionsValidation();
			Map<Position, ETerrainClient> terrain = new HashMap<>();
			
			for(int y=0; y < 3; y++) {  
				for(int x=0; x < 9; x++) {
					terrain.put(new Position(x,y), ETerrainClient.Grass);
				}
			}
			
			boolean isValidated = validation.isValidated(terrain);
			assertTrue(isValidated);
		}


}
