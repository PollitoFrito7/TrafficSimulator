package simulator.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import extra.jtable.EventsTableModel;
import extra.jtable.JunctionsTableModel;
import extra.jtable.RoadsTableModel;
import extra.jtable.VehiclesTableModel;
import simulator.control.Controller;
import javax.swing.border.Border;

//Vamos a sacar los datos de un ArrayList en un JTable
//Para esto necesitamos un modelo de tabla.
//Pues no siempre los datos van a venir en un array bidimensional
//
// In this example we will show the information stored in an List using
// a JTable

public class MainWindow extends JFrame {
	private Controller _ctrl;
	
	public MainWindow(Controller crtl) {
		super("Traffic Simulator");
		_ctrl = crtl;
		intitGUI();
	}

	private void intitGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel controlPanel = new JPanel();	// cambiar a ControlPanel cuando la clase esté implementada
		JPanel statusPanel = new JPanel();
		
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
		mainPanel.add(statusPanel, BorderLayout.PAGE_END);
		
		// map view
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		// tablesPanel for events, vehicles, roads and junctions
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);

		// mapsPanel for maps by road
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		
		
		
		
		// tables
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		
		JPanel vehiclesView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);
		
		JPanel roadsView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
		roadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);
		
		JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
		junctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);
		
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		
		/*
		JPanel mapByRoadComponent = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
		mapByRoadComponent.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapByRoadComponent);
		*/
		
		
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		
		// creation of black border of thickness 2
		Border border = BorderFactory.createLineBorder(Color.black, 2);
		p.setBorder(border);
		return p;
		
	}
}





