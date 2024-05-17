package network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import game.GameData;
import game.gameEnumerations.EPlayerGameStateClient;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerMove;
import map.HalfMap;
import move.enumeration.EMoveClient;
import messagesbase.ResponseEnvelope;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.ERequestState;
import messagesbase.messagesfromclient.PlayerRegistration;
import messagesbase.messagesfromserver.GameState;
import reactor.core.publisher.Mono;


public class ClientNetwork {
	
	private static Logger logger = LoggerFactory.getLogger(ClientNetwork.class);
	
	private static final String PLAYER_FIRST_NAME = "Yana";
	private static final String PLAYER_LAST_NAME = "Stoykova";
	private static final String PLAYER_UACCOUNT = "yanas19";
	
	private final String gameId;
	private final WebClient baseWebClient;
	private String playerID;
	private final ClientNetworkConverter convert;
	
	public ClientNetwork(String serverBaseUrl, String gameId, ClientNetworkConverter converter) {
		this.gameId = gameId;
		this.baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE).defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();
		this.convert = converter;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UniquePlayerIdentifier registerPlayer() { //consider an interface.
		PlayerRegistration playerReg = new PlayerRegistration(PLAYER_FIRST_NAME, PLAYER_LAST_NAME, PLAYER_UACCOUNT);
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/players")
				.body(BodyInserters.fromValue(playerReg)) 
				.retrieve().bodyToMono(ResponseEnvelope.class); 

		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

		if (resultReg.getState() == ERequestState.Error) {
			logger.error("PlayerRegistration: Client error, errormessage: {}", resultReg.getExceptionMessage());
		} 
		UniquePlayerIdentifier uniqueID = resultReg.getData().get();
		playerID = uniqueID.getUniquePlayerID();
		System.out.println("My Player ID: " + uniqueID.getUniquePlayerID());
		
		return uniqueID;
	}
	
	
	@SuppressWarnings({ "rawtypes" })
	private GameState getGameState() {
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET).uri("/" + gameId + "/states/" + playerID)
				.retrieve().bodyToMono(ResponseEnvelope.class);
	 
	 @SuppressWarnings("unchecked")
	ResponseEnvelope<GameState> requestResult = webAccess.block();
    	
    	if (requestResult.getState() == ERequestState.Error) {
    		logger.error("getGameState: Client error, errormessage: {}", requestResult.getExceptionMessage());
    	} 
		return requestResult.getData().get();
	}
	
	public GameData getGameData() {
		GameState gameState = getGameState();
		return convert.convertServerGameStateToGameData(gameState, playerID);
	}
	
	public void receiveFullMap() {
		getGameState().getMap();
	}
	
	public boolean mustAct() {
		 GameData gameData = getGameData();
		 return gameData.getPlayerGameState().equals(EPlayerGameStateClient.MustAct);
	 }
	
	public boolean hasLost() {
		 GameData gameData = getGameData();
		 return gameData.getPlayerGameState().equals(EPlayerGameStateClient.Lost);
	 }
	
	public boolean hasWon() {
		 GameData gameData = getGameData();
		 return gameData.getPlayerGameState().equals(EPlayerGameStateClient.Won);
	 }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendHalfMap(HalfMap halfMap) {
    	PlayerHalfMap halfMapToSend = convert.convertClientHalfMaptoServerHalfMap(halfMap, playerID);
    	
    	Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/halfmaps")
				.body(BodyInserters.fromValue(halfMapToSend)) 
				.retrieve().bodyToMono(ResponseEnvelope.class);
    	
    	ResponseEnvelope<UniquePlayerIdentifier> requestResult = webAccess.block();
    	
    	if (requestResult.getState() == ERequestState.Error) {
    		logger.error("Client error, errormessage: {}", requestResult.getExceptionMessage());
		} else {
			logger.info("Map was succesfully sent.");
		}
    	
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sendMove(EMoveClient move) {
		messagesbase.messagesfromclient.EMove sendMove = convert.convertClientEMoveToServerEMove(move);
		
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/moves")
				.body(BodyInserters.fromValue(PlayerMove.of(playerID, sendMove))) // specify the data which is sent to the server
				.retrieve().bodyToMono(ResponseEnvelope.class);
		
		ResponseEnvelope<UniquePlayerIdentifier> requestResult = webAccess.block();
    	
    	if (requestResult.getState() == ERequestState.Error) {
    		logger.error("Client error, errormessage: {}", requestResult.getExceptionMessage());
		} else {
			logger.info("Move was succesfully sent.");
		}
		//logger.trace?
	}
	
}
