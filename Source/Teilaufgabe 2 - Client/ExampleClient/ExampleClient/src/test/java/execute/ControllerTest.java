package execute;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import game.GameModel;
import messagesbase.UniquePlayerIdentifier;
import network.ClientNetwork;

class ControllerTest {
	
	private GameModel model;
	private ClientNetwork network;
	private Controller controller;

	@BeforeAll
	static void setUpBeforeClass() {
	}

	@AfterAll
	static void tearDownAfterClass() {
	}
	
	@BeforeEach
	void setUp() {
		model = Mockito.mock(GameModel.class);
		network = Mockito.mock(ClientNetwork.class);
		controller = new Controller(network, model);
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void registerPlayer_WhenInvoked_ThenReceivePlayerIdentifier() {
		
		Mockito.when(network.registerPlayer()).thenReturn(new UniquePlayerIdentifier("1a2b"));
		controller.registerPlayer();
		Mockito.verify(network).registerPlayer();
	}

}
