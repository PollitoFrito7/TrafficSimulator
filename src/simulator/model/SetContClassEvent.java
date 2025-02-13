package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {

	private List<Pair<String, Integer>> _cs;

	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if (cs == null)
			throw new IllegalArgumentException("The list cannot be empty");
		_cs = cs;
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Integer> p : _cs) {
			Vehicle vehicle = map.getVehicle(p.getFirst());
			if (vehicle == null)
				throw new IllegalArgumentException("A listed road does not exist in the simulation");
			vehicle.setContaminationClass(p.getSecond());
		}
	}

}
