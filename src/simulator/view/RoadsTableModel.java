package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private List<Road> _roads;
	
	public RoadsTableModel(Controller _ctrl) {
		_roads = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _roads.size();
	}

	@Override
	public int getColumnCount() {
		return 7;		// 7 columns
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
//		Object roadObject = null;
		Road road = _roads.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return road.getId();
		case 1:
			return road.getLength();
		case 2:
			 return road.getWeather();
		case 3:
			 return road.getMaxSpeed();
		case 4:
			 return road.getSpeedLimit();
		case 5:
			 return road.getTotalCO2();
		case 6:
			return road.getContLimit();
		default:
			return null;		// in case our index > 6 (should not)
		}
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_roads = map.getRoads();
		fireTableStructureChanged();		// fireTableDataChanged() ?¿
		
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_roads = map.getRoads();
		fireTableStructureChanged();	
		
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_roads = new ArrayList<>();
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
			return "Length";
		case 2:
			return "Weather";
		case 3:
			return "Max. Speed";
		case 4:
			return "Speed limit";
		case 5:
			return "Total CO2";
		default:
			return "CO2 Limit";
		}
	}

}
