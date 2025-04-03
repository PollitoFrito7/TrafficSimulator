package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	private Controller _ctrl;
	private JLabel _timeLabel;
	private JLabel _eventAddedLabel;
	int _time;		// to overwrite
	
	
	public StatusBar(Controller ctrl) {
		super();
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	
	private void initGUI() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		_timeLabel = new JLabel("Time: 0");
		_timeLabel.setPreferredSize(new Dimension(100, 20));
		_eventAddedLabel = new JLabel(" ");
		
		add(_timeLabel);
		
		// separator needs to be between the two labels
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		separator.setPreferredSize(new Dimension (10, 20));
		add(separator);
		
		add(_eventAddedLabel);
	}


	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		//updateTime
		_time = time;
		_timeLabel.setText("Time: " + time);
		_eventAddedLabel.setText("");
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		// update event
		_eventAddedLabel.setText("Event added (" + e.toString() + ")");		// it should display this type of message (example): Event added (Change CO2 class: [(v2,0)])
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		// called at the end of the method reset (after doing the reset)
		_time = 0;
		_timeLabel.setText("Time: " + time);
		_eventAddedLabel.setText("Reseting...");
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// called when an observer registers in the class TrafficSimulator
	}

}
