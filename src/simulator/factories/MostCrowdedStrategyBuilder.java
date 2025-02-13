package simulator.factories;

import org.json.JSONObject;

import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<MostCrowdedStrategy>{

	public MostCrowdedStrategyBuilder() {
		super("round_robin_lss", "A new round robin strategy");
	}
	
	@Override
	protected void fill_in_Data(JSONObject o) {
		o.put("timeslot" , "The amount of ticks a road can be with a green light");
	}

	@Override
	protected MostCrowdedStrategy create_instance(JSONObject data) {
		int timeSlot = data.getInt("timeslot");
		return new MostCrowdedStrategy(timeSlot);
	}

}
