package map.validation;

import java.util.List;
import java.util.Map;
import map.Position;
import map.enumerations.ETerrainClient;

public class HalfMapValidator {
	private final List<IHalfMapValidation> validations;
	
	public HalfMapValidator(List<IHalfMapValidation> validations) {
		this.validations = validations;
	}
	
	public boolean isValidated(Map<Position, ETerrainClient> terrain) {
		return validations.stream().allMatch(validation -> validation.isValidated(terrain));
	}

}


