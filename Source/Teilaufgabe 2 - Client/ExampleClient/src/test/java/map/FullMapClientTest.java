package map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import map.enumerations.EFortStateClient;
import map.enumerations.EPlayerPositionStateClient;
import map.enumerations.ETerrainClient;

class FullMapClientTest {

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
	
	static Map<Position, FullMapNodeClient> generateTerrain (int width, int height) {
		Map<Position, FullMapNodeClient> mockTerrain = new HashMap<>();
		for(int y=0; y < height; y++) {  
			for(int x=0; x < width; x++) {
		mockTerrain.put(new Position(x,y), new FullMapNodeClient(new Position(x,y), EFortStateClient.NoOrUnknownFortState,
				EPlayerPositionStateClient.NoPlayerPresent, false, ETerrainClient.Grass));
			}
		}
		return mockTerrain;
	}
	
	static Stream<Arguments> fullMapTerrainHeights() {
		return Stream.of(
				Arguments.of(generateTerrain(10,10), 9),
				Arguments.of(generateTerrain(20,5), 4)
				);
	}
	
	static Stream<Arguments> fullMapTerrainWidths() {
		return Stream.of(
				Arguments.of(generateTerrain(10,10), 9),
				Arguments.of(generateTerrain(20,5), 19)
				);
	}

	@ParameterizedTest
	@MethodSource("fullMapTerrainHeights")
	void fullMap_WhenCalculateHeight_ThenReturnCorrectHeight(Map<Position, FullMapNodeClient> mockTerrain, int height) {
		
		FullMapClient mockMap = new FullMapClient(mockTerrain);
		
		assertEquals(height, mockMap.getHeight());
	}
	
	@ParameterizedTest
	@MethodSource("fullMapTerrainWidths")
	void fullMap_WhenCalculateWidth_ThenReturnCorrectWidth(Map<Position, FullMapNodeClient> mockTerrain, int width) {
		
		FullMapClient mockMap = new FullMapClient(mockTerrain);
		
		assertEquals(width, mockMap.getWidth());
	}
	

}
