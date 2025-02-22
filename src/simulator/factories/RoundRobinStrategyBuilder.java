package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss", "A new round robin strategy");
	}
	
	@Override
	protected void fill_in_Data(JSONObject o) {
		o.put("timeslot" , "The amount of ticks a road can be with a green light");
	}

	@Override
	protected RoundRobinStrategy create_instance(JSONObject data) {
		int timeSlot = data.optInt("timeslot", 1);
		return new RoundRobinStrategy(timeSlot);
	}

}
