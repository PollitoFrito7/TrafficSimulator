package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> list = new ArrayList<Vehicle>();		// references to original objects copied, not deep

		if (!q.isEmpty()) {
			list.add(q.get(0));		// includes the first vehicle of q
		}

		return list;

	}

}
