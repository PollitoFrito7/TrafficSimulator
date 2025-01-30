package simulator.model;

public abstract class Road extends SimulatedObject{
	private String _id;
	private Junction _srcJunct;
	private Junction _destJunct;
	private int _length;
	private int _maxSpeed;
	private int _curMaxSpeed;
	private int _contLimit;
	private Weather _weather;
	private int _totalContamination;
	private List<vehicle> _vehicles;
	
	Road(String id, Junction srcJunct, Junction destJunct, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		_srcJunct = srcJunct; //TODO: add the road to the junction once Junction class is implemented
		_destJunct = destJunct; //TODO: add the road to the junction once Junction class is implemented
		if (length <= 0 ) throw new IllegalArgumentException("the length of the road must be positive."); 
		else _length = length;
		if (maxSpeed <= 0) throw new IllegalArgumentException("the maximun speed of the road must be positive.");
		else _maxSpeed = maxSpeed;
		_curMaxSpeed = maxSpeed;
		_contLimit = contLimit;
		_weather = weather;
		_totalContamination = 0;		
	}
}
