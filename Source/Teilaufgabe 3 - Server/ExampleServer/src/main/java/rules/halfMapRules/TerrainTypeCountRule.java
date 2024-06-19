package rules.halfMapRules;


import org.springframework.stereotype.Component;

import exceptions.fullMapExceptions.InvalidFullMapException;
import exceptions.halfMapExceptions.InvalidHalfMapException;
import exceptions.halfMapExceptions.TerrainTypeCountRuleException;
import messagesbase.UniqueGameIdentifier;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromserver.FullMap;
import rules.IMapRule;

@Component
public class TerrainTypeCountRule implements IMapRule{
	
	private static final int MIN_WATER = 7;
	private static final int MIN_MOUNTAIN = 5;
	private static final int MIN_GRASS = 24;

	@Override
	public void validateHalfMap(PlayerHalfMap halfMap, UniqueGameIdentifier gameID) throws InvalidHalfMapException {
		int grassNodes = (int) halfMap.getMapNodes().stream().filter(t -> t.getTerrain().equals(ETerrain.Grass)).count();
		int mountNodes = (int) halfMap.getMapNodes().stream().filter(t -> t.getTerrain().equals(ETerrain.Mountain)).count();
		int waterNodes = (int) halfMap.getMapNodes().stream().filter(t -> t.getTerrain().equals(ETerrain.Water)).count();
		
		if(grassNodes < MIN_GRASS) throw new TerrainTypeCountRuleException("TerrainTypeCountRuleException: ", "GRASS node count must be at least "
				+ MIN_GRASS + " but is " + grassNodes + ".");
		if(mountNodes < MIN_MOUNTAIN) throw new TerrainTypeCountRuleException("TerrainTypeCountRuleException: ", "MOUNTAIN node count must be at least "
				+ MIN_MOUNTAIN + " but is " + mountNodes + ".");
		if(waterNodes < MIN_WATER) throw new TerrainTypeCountRuleException("TerrainTypeCountRuleException: ", "WATER node count must be at least "
				+ MIN_WATER + " but is " + waterNodes + ".");
		
		return;
		
	}

	@Override
	public void validateFullMap(FullMap fullMap) throws InvalidFullMapException {}

}
