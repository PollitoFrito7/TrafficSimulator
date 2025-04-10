package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	private List<Pair<String, Weather>> _ws;

	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		if (ws == null)
			throw new IllegalArgumentException("The list cannot be empty");
		_ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Weather> p : _ws) {
			Road road = map.getRoad(p.getFirst());
			if (road == null)
				throw new IllegalArgumentException("A listed road does not exist in the simulation");
			road.setWeather(p.getSecond());
		}
	}

	@Override
	public String toString() {
		return "Set weather of roads: "+_ws;	// +_ws.toString();
	}
}
