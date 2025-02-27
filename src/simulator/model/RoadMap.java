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

	public RoadMap() {
		_junctionList = new ArrayList<Junction>();
		_roadsList = new ArrayList<Road>();
		_vehiclesList = new ArrayList<Vehicle>();
		_junctMap = new HashMap<String, Junction>();
		_roadsMap = new HashMap<String, Road>();
		_vehiclesMap = new HashMap<String, Vehicle>();
	}

	protected void addJunction(Junction j) {
		if (_junctMap.containsKey(j.getId()))
			throw new IllegalArgumentException("There exists a junction with the same name");
		_junctionList.add(j);
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

		for (int i = 0; i < (v.getItinerary().size() - 1); i++) {
			if (v.getItinerary().get(i).roadTo(v.getItinerary().get(i + 1)) == null)
				throw new IllegalArgumentException("No road connects these junctions");
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
			junctArray.put(_junctionList.get(i).report());
		}

		roadMap.put("junctions", junctArray);

		for (int i = 0; i < _roadsList.size(); i++) {
			roadArray.put(_roadsList.get(i).report());
		}

		roadMap.put("roads", roadArray);

		for (int i = 0; i < _vehiclesList.size(); i++) {
			vehicleArray.put(_vehiclesList.get(i).report());
		}

		roadMap.put("vehicles", vehicleArray);

		return roadMap;
	}

}