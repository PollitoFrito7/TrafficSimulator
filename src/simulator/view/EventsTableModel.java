package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private List<Event> _events;

	public EventsTableModel(Controller _ctrl) {		// controller parameter añadido
		_events = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _events.size();
	}

	@Override
	public int getColumnCount() {
		return 2;		
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object eventObject = null;
		
		if(columnIndex == 0) {
			eventObject = _events.get(rowIndex).getTime();
		} else if(columnIndex == 1) {
			eventObject = _events.get(rowIndex).toString();
		}
		
		return eventObject;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_events = new ArrayList<>(events);
		fireTableStructureChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_events.add(e);
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_events.clear();		// reset
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
	}
	
	@Override
	public String getColumnName(int col) {
		return col == 0 ? "Time" : "Description";
	}

}
