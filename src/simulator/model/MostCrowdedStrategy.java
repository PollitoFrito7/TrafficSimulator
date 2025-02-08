package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	private int _timeSlot;

	public MostCrowdedStrategy(int timeSlot) {
		_timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		int maximalSize = 0;
		int roadWithMaxQueue = 0;

		if (roads.isEmpty())
			return -1;
		if (currGreen == -1) { // red lights are -1
			for (int i = 0; i < qs.size(); i++) { // iterate through array of vehicles
				if (qs.get(i).size() > maximalSize) {
					maximalSize = qs.get(i).size();
					roadWithMaxQueue = i; // picks the first one that finds during the search
				}
			}
			return roadWithMaxQueue; // return index of the road with the largest queue
		}

		if (currTime - lastSwitchingTime < _timeSlot) {
			return currGreen;
		} else {	// traverse circularly the list
			int numIncomingRoads = roads.size();
			
			for (int i = 1; i < numIncomingRoads; i++) {		
				int index = (currGreen + i) % numIncomingRoads;
				
				if(qs.get(index).size() > maximalSize) {
					do {
						maximalSize = qs.get(index).size();
						roadWithMaxQueue = index;
					} while (roadWithMaxQueue != currGreen);		// considering only incoming red roads?
				}	
			}
			return roadWithMaxQueue;
		}		
		
	}

}
