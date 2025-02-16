package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	public NewVehicleEventBuilder() {
		super("new_vehicle", "A new vehicle");
	}

	protected void fill_in_data(JSONObject o) {
		o.put("time", "The time at which the event in executed");
		o.put("id", "The ID of the new vehicle");
		o.put("maxspeed", "The vehicle's max speed");
		o.put("class", "The vehicle's contamination class");
		o.put("itinerary", "The vehicle's itinerary");
	}
	
	@Override
	protected NewVehicleEvent create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int maxSpeed = data.getInt("maxspeed");
		int contClass = data.getInt("class");
		
		JSONArray itineraryArray = data.getJSONArray("itinerary");		// retrieves JSONArray from JSONObject 
		List<String> itinerary = new ArrayList<>();						// empty list to store junction IDs
		
		for (int i = 0; i < itineraryArray.length(); i++) {
			itinerary.add(itineraryArray.getString(i));					// iterates through array and adds it to list 
		}
		
		return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
	}

}
