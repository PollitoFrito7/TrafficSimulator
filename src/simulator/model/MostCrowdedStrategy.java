package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	private int _timeSlot;

	public MostCrowdedStrategy(int timeSlot) {
		_timeSlot = timeSlot;
	}

	private int checkMaximalSize(int idx, List<List<Vehicle>> qs) {
		int maximalSize = 0;
		int roadWithMaxQueue = 0;
		
		if (qs.get(idx).size() > maximalSize) {
			maximalSize = qs.get(idx).size();
			roadWithMaxQueue = idx;
		}
		
		return roadWithMaxQueue;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		int roadWithMaxQueue = 0;

		if (roads.isEmpty())
			return -1;
		if (currGreen == -1) {
			for (int i = 0; i < qs.size(); i++) {
				checkMaximalSize(i, qs);
			}
			return roadWithMaxQueue;
		}

		if (currTime - lastSwitchingTime < _timeSlot) {
			return currGreen;
		} else {
			int numIncomingRoads = roads.size();

			for (int i = 1; i < numIncomingRoads; i++) {
				int index = (currGreen + i) % numIncomingRoads;

				checkMaximalSize(index, qs);
			}
			return roadWithMaxQueue;
		}

	}

}
