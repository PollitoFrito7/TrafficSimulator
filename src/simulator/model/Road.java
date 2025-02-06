package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	private String _id;
	private Junction _srcJunct;
	private Junction _destJunct;
	private int _length;
	private int _maxSpeed;
	private int _curMaxSpeed;
	private int _contLimit;
	private Weather _weather;
	private int _totalContamination;
	private List<Vehicle> _vehicles = new ArrayList<Vehicle>(); // ArrayList chosen due to the high amount of accesses to specific vehicles
	
	Road(String id, Junction srcJunct, Junction destJunct, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		if (srcJunct == null) throw new IllegalArgumentException("The junction can't be NULL"); 
		else _srcJunct = srcJunct; //TODO: add the road to the junction once Junction class is implemented
		
		if (destJunct == null) throw new IllegalArgumentException("The junction can't be NULL");
		else _destJunct = destJunct; //TODO: add the road to the junction once Junction class is implemented
		
		if (length <= 0 ) throw new IllegalArgumentException("The length of the road must be positive."); 
		else setLength(length);
		
		if (maxSpeed <= 0) throw new IllegalArgumentException("The maximum speed of the road must be positive.");
		else _maxSpeed = maxSpeed;
		
		_curMaxSpeed = maxSpeed;
		
		if (contLimit <= 0) throw new IllegalArgumentException("The maximum contamination must be positive");
		else _contLimit = contLimit;
		
		if (weather == null) throw new IllegalArgumentException("The weather can't be NULL");
		else _weather = weather;
		
		_totalContamination = 0;		
	}
	
	public int getLength() {
		return _length;
	}

	private void setLength(int length) {
		_length = length;
	}

	protected void enter(Vehicle v) {
		_vehicles.add(v);
		_vehicles.sort((vehicle1, vehicle2) -> { //TODO: Ask whether we have to sort the list after adding vehicles
			Vehicle v1 = (Vehicle) vehicle1;
			Vehicle v2 = (Vehicle) vehicle2;
			if (v1.getLocation() > v2.getLocation()) return 1;
			if (v1.getLocation() < v2.getLocation()) return -1;
			return 0;
		});
	}
	
	protected void exit(Vehicle v) {
		_vehicles.remove(v); //TODO: this only works if every vehicle is different, ask for info		
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
			if (v1.getLocation() > v2.getLocation()) return 1;
			if (v1.getLocation() < v2.getLocation()) return -1;
			return 0;
		});
	}
	
	
	@Override
	public JSONObject report() {
		JSONObject road = new JSONObject();
		
		road.put(_id, _id);
		road.put("Speedlimit", _curMaxSpeed);
		road.put("Weather",  _weather);
		road.put("co2", _totalContamination);
		
		return road;		
	}
	
	

	
}
