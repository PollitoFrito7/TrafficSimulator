package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{

	private Factory<LightSwitchingStrategy> _lssFactory;
	private Factory<DequeuingStrategy> _dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction", "A new junction");
		_lssFactory = lssFactory;
		_dqsFactory = dqsFactory;
	}

	protected void fill_in_Data(JSONObject o) {
		o.put("time", "The time at which the event is executed");
		o.put("id", "The ID of the new junction");
		o.put("coor", "The coordinates of the new junction");
		
//		"ls_strategy" : { "type" : "round_robin_lss", "data" : { "timeslot" : 5} }
		o.put("ls_strategy", "This is the ls_strategy");	 		
//		"dq_strategy" : { "type" : "move_first_dqs",  "data" : {} }
		o.put("dq_strategy", "This is the dq_strategy");
	}
	
	@Override
	protected NewJunctionEvent create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		
		JSONArray coor = data.getJSONArray("coor");
		int x = coor.getInt(0);		// element in the 1st position
		int y = coor.getInt(1);		// element in the 2nd position
		
		// obtaining objects
		// extract JSON object and stores it in lsStrategyData
		JSONObject lsStrategyData = data.getJSONObject("ls_strategy");
		JSONObject dqStrategyData = data.getJSONObject("dq_strategy");

		// creates an instance given a specific strategy
		LightSwitchingStrategy lss = _lssFactory.create_instance(lsStrategyData);
		DequeuingStrategy dqs = _dqsFactory.create_instance(dqStrategyData);		
		
		return new NewJunctionEvent(time, id, lss, dqs, x, y);
	}

}
