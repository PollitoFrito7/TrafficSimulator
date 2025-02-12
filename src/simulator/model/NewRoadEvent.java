package simulator.model;

public abstract class NewRoadEvent extends Event {
	private String _id;
	private String _srcJunc;
	private String _destJunc;
	private int _length;
	private int _co2Limit;
	private int _maxSpeed;
	private Weather _weather;
	
	public NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		  super(time);
		  _id = id;
		  _srcJunc = srcJunc;
		  _destJunc = destJunc;
		  _length = length;
		  _co2Limit = co2Limit;
		  _maxSpeed = maxSpeed;
		  _weather = weather;
		}
}
