package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {
	private List<Vehicle> _vehicles;
	
	public VehiclesTableModel(Controller _ctrl) {
		_vehicles = new ArrayList<>();
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _vehicles.size();
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Vehicle vehicle = _vehicles.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return vehicle.getId();
		case 1:
			if(vehicle.getRoad() == null) return "No road";
			else if(vehicle.getStatus() == VehicleStatus.PENDING) return "Pending";
			else if(vehicle.getStatus() == VehicleStatus.ARRIVED) return "Arrived";
			else if(vehicle.getStatus() == VehicleStatus.WAITING) return "Waiting";
			else return vehicle.getRoad().getId() + ":" + vehicle.getLocation();	// r1:5628 for example
		case 2:
			return vehicle.getItinerary();
		case 3:
			return vehicle.getContClass();
		case 4:
			return vehicle.getMaxSpeed();
		case 5:
			return vehicle.getSpeed();
		case 6:
			return vehicle.getTotalCO2();
		case 7:
			return vehicle.getDist();
		default:
			return null;
		}
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_vehicles = map.getVehicles();
		fireTableStructureChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_vehicles = map.getVehicles();
		fireTableStructureChanged();	
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_vehicles = new ArrayList<>();
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
			return "Location";
		case 2:
			return "Itinerary";
		case 3:
			return "CO2 Class";
		case 4:
			return "Max. Speed";
		case 5:
			return "Speed";
		case 6:
			return "Total CO2";
		default:
			return "Distance";
		}
	}

}
