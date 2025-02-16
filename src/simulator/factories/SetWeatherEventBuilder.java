package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder() {
		super("set_weather", "Set the weather of the event");
	}
	
	protected void fill_in_data(JSONObject o) {
		o.put("time", "The time at which the event is executed");
		o.put("info", "The information of the road and the weather");
	}
	
	@Override
	protected SetWeatherEvent create_instance(JSONObject data) {
		int time = data.getInt("time");
		
		JSONArray wsArray = data.getJSONArray("info");				// extract "info" from array
		List<Pair<String, Weather>> wsList = new ArrayList<>();		// empty list
		
		for(int i = 0; i < wsArray.length(); i++) {
			JSONObject obj = wsArray.getJSONObject(i);				// extracts JSONObject at position i
			String road = obj.getString("road");
			Weather w = Weather.valueOf(obj.getString("weather"));
			wsList.add(new Pair<>(road, w));
		}
		
		return new SetWeatherEvent(time, wsList);
	}

}
