package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event>{
	
	public NewCityRoadEventBuilder() {
		super("new_city_road", "A new city road");
	}
	
	protected void fill_in_Data(JSONObject o) {
		o.put("time", "The time at which the event is executed");
		o.put("id", "The ID of the new city road");
		o.put("src", "The source junction");
		o.put("dest", "The destination junction");
		o.put("length", "The length of the road");
		o.put("co2limit", "The contamination limit");
		o.put("maxspeed", "The maximum speed that the new city road can have");
		o.put("weather", "The weather in the new city road");
	}

	@Override
	protected NewCityRoadEvent create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String srcJunct = data.getString("src");
		String destJunct = data.getString("dest");		
		int length = data.getInt("length");
		int co2Limit = data.getInt("co2limit");
		int maxSpeed = data.getInt("maxspeed");
		Weather weather = Weather.valueOf(data.getString("weather"));
		
		
		return new NewCityRoadEvent(time, id, srcJunct, destJunct, length, co2Limit, maxSpeed, weather);
	}

}
