package simulator.model;

import java.util.PriorityQueue;
import java.util.Queue;
import org.json.JSONObject;

public class TrafficSimulator {
	private RoadMap _roadMap;
	private Queue<Event> _events;
	private int _simulationTime;

	public TrafficSimulator() {
		_roadMap = new RoadMap();
		_events = new PriorityQueue<>();
		_simulationTime = 0;
	}

	public void addEvent(Event e) {
		_events.add(e);
	}

	public void advance() {
		_simulationTime++;

		while (!_events.isEmpty() && _events.element().getTime() == _simulationTime) {
			_events.element().execute(_roadMap);
			_events.remove();
		}

		for (Junction j : _roadMap.getJunctions()) {
			j.advance(_simulationTime);
		}

		for (Road r : _roadMap.getRoads()) {
			r.advance(_simulationTime);
		}
	}

	public void reset() {
		_roadMap.reset();
		_events.clear();
		_simulationTime = 0;
	}

	public JSONObject report() {
		JSONObject simulator = new JSONObject();

		simulator.put("time", _simulationTime);
		simulator.put("state", _roadMap.report());

		return simulator;
	}

}
