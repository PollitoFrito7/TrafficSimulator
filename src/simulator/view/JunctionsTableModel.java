package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private List<Junction> _junctions;
	
	public JunctionsTableModel(Controller _ctrl) {
		_junctions = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _junctions.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Junction junction = _junctions.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return junction.getId();
		case 1:
			return (junction.getGreenLightIndex() == -1) ? "NONE" : junction.getInRoads().get(rowIndex).getId();
		case 2:
			return junction.getInRoads();	// queues
		default:
			return null;
		}
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_junctions = map.getJunctions();
		fireTableStructureChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_junctions = map.getJunctions();
		fireTableStructureChanged();	
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_junctions = new ArrayList<>();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		
	}

	@Override
	public String getColumnName(int col) {
		switch(col) {
		case 0:
			return "Id";
		case 1:
			return "Green";
		default:
			return "Queues";
		}
	}
}
