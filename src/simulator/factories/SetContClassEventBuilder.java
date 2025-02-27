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

	protected void fill_in_Data(JSONObject o) {
		o.put("time", "The time at which the event is executed");
		o.put("info", "The information of the vehicle and the contamination class");
	}

	@Override
	protected SetContClassEvent create_instance(JSONObject data) {
		int time = data.getInt("time");

		JSONArray csArray = data.getJSONArray("info");
		List<Pair<String, Integer>> csList = new ArrayList<>();

		for (int i = 0; i < csArray.length(); i++) {
			JSONObject obj = csArray.getJSONObject(i);
			String vehicle = obj.getString("vehicle");
			int contClass = obj.getInt("class");
			csList.add(new Pair<>(vehicle, contClass));
		}

		return new SetContClassEvent(time, csList);
	}

}
