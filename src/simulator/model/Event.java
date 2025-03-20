package simulator.model;

public abstract class Event implements Comparable<Event> {

	private static long _counter = 0;

	protected int _time;
	protected long _time_stamp;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Invalid time: " + time);
		else {
			_time = time;
			_time_stamp = _counter++;
		}
	}

	public int getTime() {
		return _time;
	}

	long getTimeStamp() {
		return _time_stamp;
	}

	@Override
	public int compareTo(Event o) {
		if (_time > o.getTime() || (_time == o.getTime() && _time_stamp > o.getTimeStamp()))
			return 1;
		else if (_time < o.getTime() || (_time == o.getTime() && _time_stamp < o.getTimeStamp()))
			return -1;
		return 0;
	}

	abstract void execute(RoadMap map);
}