package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) { //TODO: ask if a deep copy should be used instead of a shallow copy 
		List<Vehicle> copy = new ArrayList<Vehicle>();
	
		for (Vehicle v : q)
			copy.add(v);
		
		return copy;
	}

}
