package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	private String _id;
	private int _maxSpeed;
	private int _contClass;
	private List<String> _itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		_id = id;
		_maxSpeed = maxSpeed;
		_contClass = contClass;
		_itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		List<Junction> itinerary = new ArrayList<>();

		for (String s : _itinerary) {
			itinerary.add(map.getJunction(s));
		}

		Vehicle vehicle = new Vehicle(_id, _maxSpeed, _contClass, itinerary);
		map.addVehicle(vehicle);
	}
}
