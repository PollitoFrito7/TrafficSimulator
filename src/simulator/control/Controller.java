package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator _sim;
	private Factory<Event> _eventsFactory;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if (sim == null | eventsFactory == null)
			throw new IllegalArgumentException("The values cannot be null");
		else {
			_sim = sim;
			_eventsFactory = eventsFactory;
		}
	}

	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray events = jo.getJSONArray("events");
		for (int i = 0; i < events.length(); i++) {
			_sim.addEvent(_eventsFactory.create_instance(events.getJSONObject(i)));
		}
	}

	public void addObserver(TrafficSimObserver o) {
		_sim.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		_sim.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		_sim.addEvent(e);
	}
	
	public void run(int n) {
		for (int i = 0; i < n; i++) {
			_sim.advance();
		}
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);

		p.print("{");
		p.print("  \"states\": [");

		// loop for the first n-1 states (to print comma after each state)
		for (int i = 0; i < n - 1; i++) {
			_sim.advance();
			p.print(_sim.report());
			p.println(",");
		}

		// last step, only if 'n > 0'
		if (n > 0) {
			_sim.advance();
			p.print(_sim.report());
		}

		p.print("]");
		p.print("}");
	}

	public void reset() {
		_sim.reset();
	}
}
