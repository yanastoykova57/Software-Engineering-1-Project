package map;

import java.util.Map;
import map.enumerations.ETerrainClient;

public class HalfMap {

	private final Map<Position, ETerrainClient> terrain;
	private final Position fortress;
	
	public HalfMap(final Map<Position, ETerrainClient> terrain, final Position fortress) {
		this.terrain = terrain;
		this.fortress = fortress;
	}

	public Map<Position, ETerrainClient> getTerrain() {
		return terrain;
	}

	public Position getFortress() {
		return fortress;
	}

}
