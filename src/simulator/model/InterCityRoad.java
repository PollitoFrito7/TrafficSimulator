package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunct, Junction destJunct, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunct, destJunct, maxSpeed, contLimit, length, weather);
	}

	@Override
	protected void reduceTotalContamination() {
		int weatherDependance;
		switch (getWeather()) {
		case CLOUDY:
			weatherDependance = 3;
			break;
		case RAINY:
			weatherDependance = 10;
			break;
		case STORM:
			weatherDependance = 20;
			break;
		case SUNNY:
			weatherDependance = 2;
			break;
		case WINDY:
			weatherDependance = 15;
			break;
		default:
			weatherDependance = 0;
			break;
		}
		_totalContamination = Math.max(((100 - weatherDependance) * getTotalCO2()) / 100, 0);
	}

	@Override
	protected void updateSpeedLimit() {
		if (getTotalCO2() > getContLimit())
			_curMaxSpeed = getMaxSpeed() / 2;
		else
			_curMaxSpeed = getMaxSpeed();
	}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		if (getWeather() != Weather.STORM) {
			return Math.min(v.getMaxSpeed(), _curMaxSpeed);
		}
		return Math.min(v.getMaxSpeed(), (_curMaxSpeed * 8) / 10);
	}

}
