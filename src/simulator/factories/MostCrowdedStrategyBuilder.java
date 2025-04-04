package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss", "A new most crowded strategy");
	}

	@Override
	protected void fill_in_Data(JSONObject o) {
		o.put("timeslot", "The amount of ticks a road can be with a green light");
	}

	@Override
	protected MostCrowdedStrategy create_instance(JSONObject data) {
		int timeslot = data.optInt("timeslot", 1);
		return new MostCrowdedStrategy(timeslot);
	}

}
