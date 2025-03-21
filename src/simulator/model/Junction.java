package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
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
	private int _xCoord;
	private int _yCoord;

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);

		_inRoads = new ArrayList<Road>();
		_queues = new ArrayList<List<Vehicle>>();
		_queueByRoad = new HashMap<Road, List<Vehicle>>();
		_outRoadByJunction = new HashMap<Junction, Road>();
		_greenLightIndex = -1;
		_lastSwitchingTime = 0;

		if (lsStrategy == null || dqStrategy == null)
			throw new IllegalArgumentException("The strategies cannot be null.");
		_lsStrategy = lsStrategy;
		_dqStrategy = dqStrategy;

		if (xCoor < 0 || yCoor < 0)
			throw new IllegalArgumentException("The coordinates cannot have negative values.");
		_xCoord = xCoor;
		_yCoord = yCoor;
	}

	public int getX() {
		return _xCoord;
	}
	
	public int getY() {
		return _yCoord;
	}
	
	protected void addIncommingRoad(Road r) {
		_inRoads.add(r);
		_queues.add(new LinkedList<Vehicle>());
		_queueByRoad.put(r, _queues.get(_queues.size() - 1));
	}

	protected void addOutgoingRoad(Road r) {
		if (!r.getSrc().equals(this))
			throw new IllegalArgumentException("This junction must be the destination of the road");
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
		if (_greenLightIndex != -1) {
			List<Vehicle> dequedVehicles = _dqStrategy.dequeue(_queues.get(_greenLightIndex));
			for (Vehicle v : dequedVehicles) {
				v.moveToNextRoad();
				_queues.get(_greenLightIndex).remove(v);
			}
		}
		int nextGreen = _lsStrategy.chooseNextGreen(_inRoads, _queues, _greenLightIndex, _lastSwitchingTime, time);
		if (nextGreen != _greenLightIndex) {
			_greenLightIndex = nextGreen;
			_lastSwitchingTime = time;
		}
	}

	public int getGreenLightIndex() {
		return _greenLightIndex;
	}
	
	public List<Road> getInRoads() {
		return Collections.unmodifiableList(_inRoads);
	}
	
	@Override
	public JSONObject report() {
		JSONObject junction = new JSONObject();

		junction.put("id", _id);
		if (_greenLightIndex == -1)
			junction.put("green", "none");
		else
			junction.put("green", _inRoads.get(_greenLightIndex).getId());
		JSONArray queues = new JSONArray();

		for (int i = 0; i < _queues.size(); i++) {
			JSONObject qi = new JSONObject();
			qi.put("road", _inRoads.get(i).getId());
			JSONArray vehicles = new JSONArray();
			for (int j = 0; j < _queueByRoad.get(_inRoads.get(i)).size(); j++)
				vehicles.put(_queueByRoad.get(_inRoads.get(i)).get(j).getId());
			qi.put("vehicles", vehicles);
			queues.put(qi);
		}

		junction.put("queues", queues);

		return junction;
	}

}
