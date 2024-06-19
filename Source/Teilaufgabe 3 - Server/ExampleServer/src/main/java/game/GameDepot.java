package game;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import player.Player;

@Service
public class GameDepot {
	//private final static Logger logger = LoggerFactory.getLogger(GameDepot.class);

	private List <Game> games = new ArrayList<>();

	public void addGame(Game newGame) {
		checkGameCount();
		games.add(newGame);	
	}
	
	private void checkGameCount() {
		if(games.size() == 99) {
			games.remove(0);
		}
	}
	
	public Optional<Game> getGame(UniqueGameIdentifier gameID) {
		return games.stream().filter(game -> game.getGameID().equals(gameID)).findFirst();
	} 
	
	public Optional<Player> getGamePlayer(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		return getGame(gameID).flatMap(game -> game.getPlayer(playerID));
	}
	
}
