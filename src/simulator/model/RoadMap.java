package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> _junctionList;
	private List<Road> _roadsList;
	private List<Vehicle> _vehiclesList;
	private Map<String, Junction> _junctMap;
	private Map<String, Road> _roadsMap;
	private Map<String, Vehicle> _vehiclesMap;

	// maps -> search for an object (more efficient)
	// lists -> traverse objects in same order as added

	public RoadMap() {
		_junctionList = new ArrayList<Junction>();
		_roadsList = new ArrayList<Road>();
		_vehiclesList = new ArrayList<Vehicle>();
		_junctMap = new HashMap<String, Junction>();
		_roadsMap = new HashMap<String, Road>();
		_vehiclesMap = new HashMap<String, Vehicle>();
	}

	protected void addJunction(Junction j) {
		_junctionList.add(j); // end of the list
		_junctMap.put(j.getId(), j);
	}

	protected void addRoad(Road r) {
		if (_roadsMap.containsKey(r.getId()))
			throw new IllegalArgumentException("There exists a road with the same ID.");
		if (!_junctMap.containsKey(r.getSrc().getId()) || !_junctMap.containsKey(r.getDest().getId()))
			throw new IllegalArgumentException("Source/Destination junction is not in the RoadMap.");
		_roadsList.add(r);
		_roadsMap.put(r.getId(), r);
	}

	protected void addVehicle(Vehicle v) {
		if (_vehiclesMap.containsKey(v.getId()))
			throw new IllegalArgumentException("There exists a vehicle with the same ID.");

		// road from the i-th junction to the (i + 1)-th junction
		for (int i = 0; i < _junctionList.size() - 1; i++) {
			if (v.getItinerary().get(i).roadTo(v.getItinerary().get(i + 1)) == null)
				throw new IllegalArgumentException("You suck ass");
		}

		_vehiclesList.add(v);
		_vehiclesMap.put(v.getId(), v);
	}

	public Junction getJunction(String id) {
		return _junctMap.get(id);
	}

	public Road getRoad(String id) {
		return _roadsMap.get(id);
	}

	public Vehicle getVehicle(String id) {
		return _vehiclesMap.get(id);
//		returns the value to which the specified key is mapped
//		or null if this map contains no mapping for the key
	}

	public List<Junction> getJunctions() {
		return Collections.unmodifiableList(new ArrayList<>(_junctionList));
	}

	public List<Road> getRoads() {
		return Collections.unmodifiableList(new ArrayList<>(_roadsList));
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(new ArrayList<>(_vehiclesList));
	}

	protected void reset() {
		// clear all lists and maps
		_junctionList.clear();
		_roadsList.clear();
		_vehiclesList.clear();
		_junctMap.clear();
		_roadsMap.clear();
		_vehiclesMap.clear();
	}

	public JSONObject report() {
		JSONObject roadMap = new JSONObject();
		JSONArray junctArray = new JSONArray();
		JSONArray roadArray = new JSONArray();
		JSONArray vehicleArray = new JSONArray();

		for (int i = 0; i < _junctionList.size(); i++) {
			junctArray.put(roadMap.put("junctions", _junctionList.get(i).report()));
		}

		for (int i = 0; i < _roadsList.size(); i++) {
			roadArray.put(roadMap.put("road", _roadsList.get(i).report()));
		}
		for (int i = 0; i < _vehiclesList.size(); i++) {
			vehicleArray.put(roadMap.put("vehicles", _vehiclesList.get(i).report()));
		}

		return roadMap;
	}

}