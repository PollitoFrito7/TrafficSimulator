package extra.jtable;

public class EventEx {

	private Integer _time;// tiempo en el que se produce el evento
	private String _desc;// respecto de otros eventos

	public EventEx(Integer time, String desc) {
		_time = time;
		_desc = desc;

	}

	public int getTime() {
		return _time;

	}

	public String getDesc() {
		return _desc;

	}

}
