package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy>{

	public MoveFirstStrategyBuilder() {
<<<<<<< HEAD
		super("move_First_dqs", "A new Move First Strategy");
=======
		super("move_first_dqs", "A new Move First Strategy");
>>>>>>> Vehicle
	}
	
	@Override
	protected DequeuingStrategy create_instance(JSONObject data) {
		return new MoveFirstStrategy();
	}

}
