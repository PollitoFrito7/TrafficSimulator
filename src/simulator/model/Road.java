package simulator.model;

import java.util.List;

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
	private List<Vehicle> _vehicles;
	
	Road(String id, Junction srcJunct, Junction destJunct, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		_srcJunct = srcJunct; //TODO: add the road to the junction once Junction class is implemented
		_destJunct = destJunct; //TODO: add the road to the junction once Junction class is implemented
		if (length <= 0 ) throw new IllegalArgumentException("The length of the road must be positive."); 
		else setLength(length);			// _length = length
		if (maxSpeed <= 0) throw new IllegalArgumentException("The maximum speed of the road must be positive.");
		else _maxSpeed = maxSpeed;
		_curMaxSpeed = maxSpeed;
		_contLimit = contLimit;
		_weather = weather;
		_totalContamination = 0;		
	}

	protected void enter(Vehicle v) {
		
	}
	
	protected void exit(Vehicle v) {
		
	}
	
	protected void setWeather(Weather w) {
		
	}
	
	protected void addContamination(int c) {
		
	}
	
	protected abstract void reduceTotalContamination();
	
	protected abstract void updateSpeedLimit();
	
	protected abstract int calculateVehicleSpeed(Vehicle v);
	
	@Override
	protected void advance(int time) {
		
	}
	
	/*
	 * @Override
	public JSONObject report() {
		
	}
	 * */
	
	// used to get access length field from Road in class Vehicle
	public int getLength() {
		return _length;
	}

	private void setLength(int _length) {
		this._length = _length;
	}
}
