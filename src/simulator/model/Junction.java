package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	private List<Road> _inRoads;
	private List<List<Vehicle>> _queues;
	private Map<Road, List<Vehicle>> _queueByRoad;
	private Map<Junction, Road> _outRoadByJunction;
	private int _greenLightIndex;
	private int _lastSwitchingTime;
	private LightSwitchingStrategy _lsStrategy;
	private DequeuingStrategy _dqStrategy;
	@SuppressWarnings("unused")
	private int _xCoord;
	@SuppressWarnings("unused")
	private int _yCoord; 
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		
		_inRoads = new ArrayList<Road>();
		_queues = new ArrayList<List<Vehicle>>(); // TODO: Ask if the type of the inner list can be changed only to accept arraydeques
		_queueByRoad = new HashMap<Road, List<Vehicle>>();
		_outRoadByJunction = new HashMap<Junction, Road>();
		_greenLightIndex = -1;
		_lastSwitchingTime = 0;
		
		if(lsStrategy == null || dqStrategy == null) throw new IllegalArgumentException("The strategies cannot be null.");
		_lsStrategy = lsStrategy;
		_dqStrategy = dqStrategy;
		
		if(xCoor == 0 || yCoor == 0) throw new IllegalArgumentException("The coordinates cannot have negative values.");
		_xCoord = xCoor;
		_yCoord = yCoor;
	}
	
	protected void addIncommingRoad(Road r) {
		if (!r.getDest().equals(this)) throw new IllegalArgumentException("This junction must be the destination of the road");	//TODO: Ask if we can create our own Exceptions
		_inRoads.add(r);
		_queues.add(new LinkedList<Vehicle>());
		_queueByRoad.put(r, _queues.get(_queues.size() - 1));
	}
	
	protected void addOutgoingRoad(Road r) {
		_outRoadByJunction.put(r.getDest(), r);
	}
	
	protected void enter(Vehicle v) {
		_queueByRoad.get(v.getRoad()).add(v);
	}
	
	protected Road roadTo(Junction j) {
		return _outRoadByJunction.get(j);
	}
	
	@Override
	protected void advance(int time) {
		List<Vehicle> dequedVehicles = _dqStrategy.dequeue(_queues.get(_greenLightIndex));
		for (Vehicle v : dequedVehicles) {
			v.moveToNextRoad();
		}
		_lsStrategy.chooseNextGreen(_inRoads, _queues, _greenLightIndex, _lastSwitchingTime, time);
	}

	@Override
	public JSONObject report() {
		JSONObject junction = new JSONObject();
		
		junction.put("Id", _id);		// junction id
		junction.put("Green", _inRoads.get(_greenLightIndex).getId());
		JSONArray queues = new JSONArray();
		
		for (int i = 0; i < _queues.size(); i++) {
			JSONObject qi = new JSONObject();
			qi.put("road", _inRoads.get(i).getId());
			JSONArray vehicles = new JSONArray();
			for (int j = 0; i < _queueByRoad.get(_inRoads.get(i)).size(); j++)
				vehicles.put(_queueByRoad.get(_inRoads.get(i)).get(j).getId()); //TODO: ask whether we have to put the vehicle id or just enumerate the vehicles
			qi.put(_id, vehicles);
			queues.put(qi);
		}
		
		junction.put("queues", queues);	
		
		return junction;
	}
}
