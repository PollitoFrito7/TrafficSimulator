package simulator.model;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

//	itinerary List<Junction>
	private int _maximumSpeed;
	private int _currentSpeed;

	VehicleStatus status;
//	Road road;
	private int _location = 0;

	// number between 0 and 10
	private int _contaminationClass = 0;
	private int _totalContamination;
	private int _totalTraveledDist;

	protected Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		// id not empty string
		
		// maxSpeed > 0
		
		// contClass number between 0 and 10
		
		// length of itinerary at least 2
		
		// 
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

}
