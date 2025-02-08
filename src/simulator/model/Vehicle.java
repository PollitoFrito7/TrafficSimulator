package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	private List<Junction> _itinerary;
	private int _maximumSpeed;
	private int _currentSpeed;
	private VehicleStatus _status;
	private Road _road;
	private int _location = 0;
	private String _id;
	private int _contaminationClass = 0;
	private int _totalContamination = 0;
	private int _totalTraveledDist = 0;
	private int _lastJunctionEncountered = 0;

	protected Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);

		if (id.isEmpty())
			throw new IllegalArgumentException("The string cannot be empty.");
		else _id = id;
		if (maxSpeed <= 0) throw new IllegalArgumentException("The maximum speed must be positive.");
		else _maximumSpeed = maxSpeed;
		if (0 > contClass || contClass > 10)
			throw new IllegalArgumentException("The contamination class must be a number between 0 and 10 (both inclusive");
		else setContaminationClass(contClass);
		if (itinerary == null || itinerary.size() != 2)
			throw new IllegalArgumentException("The length of the list must be at least 2");
		else setItinerary(Collections.unmodifiableList(new ArrayList<>(itinerary)));
	}

	protected void setSpeed(int s) {
		if (s < 0)
			throw new IllegalArgumentException("The speed cannot be negative.");
		else
			_currentSpeed = Math.min(s, _maximumSpeed);

	}

	protected void setContaminationClass(int c) {
		if (c < 0 || c > 10)
			throw new IllegalArgumentException("The contamination cannot be set properly.");
		else
			_contaminationClass += c;
		// setContClass does the same as above in the else statement
	}

	@Override
	protected void advance(int time) {
		if (_status != VehicleStatus.TRAVELING)
			return;

//			maybe put some of this in separated functions?¿
		int _newLocation = 0;
		_newLocation = Math.min(_location + _currentSpeed, _road.getLength());

		int d = _totalTraveledDist;
		_totalTraveledDist = _newLocation - _location;

		int f = _contaminationClass;
		int c = d * f;

		_totalContamination += c;
		_road.addContamination(f);

		if (_newLocation == _road.getLength()) {
			_itinerary.get(_lastJunctionEncountered).enter(this);
			_status = VehicleStatus.PENDING;
			_currentSpeed = 0;
			_lastJunctionEncountered++;

		}
	}

	protected void moveToNextRoad() {
		if (_status != VehicleStatus.PENDING || _status != VehicleStatus.WAITING)
			throw new IllegalArgumentException("The status must be pending or waiting.");
		else if (_road != null || _lastJunctionEncountered > 0) {
			_road.exit(this);
		}
		else if(_status == VehicleStatus.WAITING && _itinerary.size() == _lastJunctionEncountered) {			
			_status = VehicleStatus.ARRIVED;
			_currentSpeed = 0;
			_road = null;
		} else {
			_itinerary.get(_lastJunctionEncountered).roadTo(_itinerary.get(_lastJunctionEncountered + 1));	
			_road.enter(this);
			_status = VehicleStatus.TRAVELING;
			_location = 0;
		}
	}

	// see which type of modifier suits better with getters and setters
	public int getLocation() {
		return _location;
	}

	public int getCurrentSpeed() {
		return _currentSpeed;
	}

	public int getContClass() {
		return _contaminationClass;
	}

	public int getMaxSpeed() {
		return _maximumSpeed;
	}

	public VehicleStatus getStatus() {
		return _status;
	}

	public int getTotalCO2() {
		return _totalContamination;
	}

	public List<Junction> getItinerary() {
		return _itinerary;
	}

	public Road getRoad() {
		return _road;
	}

	private void setItinerary(List<Junction> itinerary) {
		_itinerary = itinerary;
	}

	@Override
	public JSONObject report() {
		JSONObject vehicle = new JSONObject();
		
		vehicle.put("Id", _id);
		vehicle.put("Speed", _currentSpeed);
		vehicle.put("Distance", _totalTraveledDist);
		vehicle.put("CO2", _totalContamination);
		vehicle.put("Class", _contaminationClass);
		vehicle.put("Status", _status);
		if(_status != VehicleStatus.PENDING || _status!= VehicleStatus.ARRIVED) {
			vehicle.put("Road", _road);
			vehicle.put("Location", _location);
		}
		
		return vehicle;
	}


}