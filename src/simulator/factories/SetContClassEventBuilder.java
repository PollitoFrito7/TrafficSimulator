package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class", "Set the contamination class of the event");
	}
	
	protected void fill_in_data(JSONObject o) {
		o.put("time", "The time at which the event is executed");
		o.put("info", "The information of the vehicle and the contamination class");
	}

	@Override
	protected SetContClassEvent create_instance(JSONObject data) {
		int time = data.getInt("time");
		
		JSONArray csArray = data.getJSONArray("info");				// extract the array of pairs
		List<Pair<String, Integer>> csList = new ArrayList<>();		// empty at first
		
		for(int i = 0; i < csArray.length(); i++) {
			JSONObject obj = csArray.getJSONObject(i);		// extract the objects of the array
			String vehicle = obj.getString("vehicle");		// retrieves "vehicle" value from extracted JSONObject
			int contClass = obj.getInt("class");			// retrieves "class" value from extracted JSONObject
			csList.add(new Pair<>(vehicle, contClass));		// a Pair<String, Integer> is created and added to the list
		}
		
		return new SetContClassEvent(time, csList);
	}

}
