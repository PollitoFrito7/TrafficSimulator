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
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	protected void addIncommingRoad(Road r) {
		
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
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	@Override
	public void JSONObject report() {
		return null;
	}*/
}
