package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	private String _id;
	private Junction _srcJunct;
	private Junction _destJunct;
	private int _length;
	private int _maxSpeed;
	protected int _curMaxSpeed;
	private int _contLimit;
	private Weather _weather;
	protected int _totalContamination;
	private List<Vehicle> _vehicles;
	
	Road(String id, Junction srcJunct, Junction destJunct, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		if (srcJunct == null || destJunct == null) throw new IllegalArgumentException("The junctions can't be NULL"); 
		_srcJunct = srcJunct;
		srcJunct.addOutgoingRoad(this);
		_destJunct = destJunct; 
		destJunct.addIncommingRoad(this);	
		
		if (length <= 0 ) throw new IllegalArgumentException("The length of the road must be positive."); 
		_length = length;
		
		if (maxSpeed <= 0) throw new IllegalArgumentException("The maximum speed of the road must be positive.");
		_maxSpeed = maxSpeed;
		
		_curMaxSpeed = maxSpeed;
		
		if (contLimit <= 0) throw new IllegalArgumentException("The maximum contamination must be positive");
		_contLimit = contLimit;
		
		if (weather == null) throw new IllegalArgumentException("The weather can't be NULL");
		_weather = weather;
		
		_totalContamination = 0;
		
		_vehicles = new ArrayList<Vehicle>(); // ArrayList chosen due to the high amount of accesses to specific vehicles
	}
	
	public String getId() {
		return _id;
	}
	
	public int getLength() {
		return _length;
	}
	
	public Junction getDest() {
		return _destJunct;
	}
	
	public Junction getSrc() {
		return _srcJunct;
	}
	
	public Weather getWeather() {
		return _weather;
	}
	
	public int getContLimit() {
		return _contLimit;
	}
	
	public int getMaxSpeed() {
		return _maxSpeed;
	}
	
	public int getTotalCO2() {
		return _totalContamination;
	}
	
	public int getSpeedLimit() {
		return _curMaxSpeed;
	}
	
	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(_vehicles);
	}

	protected void enter(Vehicle v) {
		_vehicles.add(v);
		
	}
	
	protected void exit(Vehicle v) {
		_vehicles.remove(v); 	
	}
	
	protected void setWeather(Weather w) {
		if (w == null) throw new IllegalArgumentException("The weather can't be null");
		else _weather = w;
	}
	
	protected void addContamination(int c) {
		if (c < 0) throw new IllegalArgumentException("The contamination must be positive");
		else _totalContamination += c;
	}
	
	protected abstract void reduceTotalContamination();
	
	protected abstract void updateSpeedLimit();
	
	protected abstract int calculateVehicleSpeed(Vehicle v);
	
	@Override
	protected void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		
		for (Vehicle v : _vehicles) {
			calculateVehicleSpeed(v);
			v.advance(time);
		}
		
		_vehicles.sort((vehicle1, vehicle2) -> {
			Vehicle v1 = (Vehicle) vehicle1;
			Vehicle v2 = (Vehicle) vehicle2;
			if (v1.getLocation() > v2.getLocation()) return -1; //descending order
			if (v1.getLocation() < v2.getLocation()) return 1;
			return 0;
		});
	}
	
	
	@Override
	public JSONObject report() {
		JSONObject road = new JSONObject();
		
		road.put("Id", _id);
		road.put("Speedlimit", _curMaxSpeed);
		road.put("Weather",  _weather);
		road.put("CO2", _totalContamination);
		JSONArray vehicles = new JSONArray();
		
		for (Vehicle v : _vehicles)
			vehicles.put(v.getId());
		
		road.put("Vehicles", vehicles);
		
		return road;		
	}
	
	

	
}
