package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunct, Junction destJunct, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunct, destJunct, maxSpeed, contLimit, length, weather);
	}

	@Override
	protected void reduceTotalContamination() {
		int weatherDependance;
	
		switch(getWeather()) {
		case STORM:
			weatherDependance = 10;
			break;
		case WINDY:
			weatherDependance = 10;
			break;
		default:
			weatherDependance = 2;
			break;
		}
		
		_totalContamination -= weatherDependance;		
	}

	@Override
	protected void updateSpeedLimit() {
		_curMaxSpeed = getMaxSpeed();		
	}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		return (11 - v.getContClass())* _curMaxSpeed / 11;
	}
}
