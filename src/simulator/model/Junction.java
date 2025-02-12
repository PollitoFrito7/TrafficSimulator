package simulator.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {
	private List<Road> _inRoads;
	private List<List<Vehicle>> _queues;
	private Map<Road, List<Vehicle>> _queueByRoad;
	private Map<Junction, Road> _outRoadByJunction;
	private int _greenLightIndex;
	private int _lastSwitchingTime;
	private LightSwitchingStrategy _lss;
	private DequeuingStrategy _dqs;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
<<<<<<< Updated upstream
		// TODO Auto-generated constructor stub
=======
		if(lsStrategy == null || dqStrategy == null) throw new IllegalArgumentException("The strategies cannot be null.");
		else {
			_lsStrategy = lsStrategy;
			_dqStrategy = dqStrategy;
		}
		if(xCoor == 0 || yCoor == 0) throw new IllegalArgumentException("The coordinates cannot have negative values.");
		else {
			_xCoord = xCoor;
			_yCoord = yCoor;
		}
>>>>>>> Stashed changes
	}
	
	protected void addIncommingRoad(Road r) {
		
		
		_inRoads.add(r);
		
	}
	
	protected void addOutgoingRoad(Road r) {
		
	}
	
	protected void enter(Vehicle v) {
		
	}
	
	protected Road roadTo(Junction j) {
		return null;
	}
	
	@Override
	protected void advance(int time) {
		
	}

	@Override
	public JSONObject report() {
<<<<<<< Updated upstream
		// TODO Auto-generated method stub
=======
		JSONObject junction = new JSONObject();
		
		
		
		
		
>>>>>>> Stashed changes
		return null;
	}
	
	/*
	@Override
	public void JSONObject report() {
		return null;
	}*/
}
