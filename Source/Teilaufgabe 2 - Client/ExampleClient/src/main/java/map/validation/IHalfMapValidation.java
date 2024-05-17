package map.validation;

import java.util.Map;
import map.Position;
import map.enumerations.ETerrainClient;

public interface IHalfMapValidation {
	boolean isValidated(Map<Position, ETerrainClient> terrain);
}
