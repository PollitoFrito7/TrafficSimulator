package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy>{

	public MoveAllStrategyBuilder() {
<<<<<<< HEAD
		super("move_All_dqs", "A new Move All Strategy");
=======
		super("move_all_dqs", "A new Move All Strategy");
>>>>>>> Vehicle
	}
	
	@Override
	protected DequeuingStrategy create_instance(JSONObject data) {
		return new MoveAllStrategy();
	}

}
