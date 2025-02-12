package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		_junctionList.add(j);		// end of the list
		_junctMap.put(j.getId(), j);
		
	}
	
	protected void addRoad(Road r) {
		// HashMaps do not have duplicated keys nor null values?
		// recorrer lista
		
		_roadsList.add(r);
		_roadsMap.put(r.getId(), r);
		// tiene que existir el source y el destination
		// more implementation sweetie
	}
	
	protected void addVehicle(Vehicle v) {
		_vehiclesList.add(v);
		_vehiclesMap.put(v.getId(), v);
		
		// more implementation sweetie
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
	
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(_junctionList);
	}
	
	public List<Road> getRoads(){
		return Collections.unmodifiableList(new ArrayList<>(_roadsList));
	}
	
	public List<Vehicle> getVehicles(){
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
		
		
		return null;
	}
	
}
