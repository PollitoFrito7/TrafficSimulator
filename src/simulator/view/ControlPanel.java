package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements TrafficSimObserver {
	private final String iconsPath = "resources/icons/";
	private final String _cccDescription = "Schedule an event to change the CO2 class of a vehicle after a given number" + "<br>" + "of simulation ticks from now." + "<br>" + " ‎";
	private final String _cwDescription = "Schedule an event to change the weather of a road after a given number of" + "<br>" + "simulation ticks from now" + "<br>" + " ‎";
	private Controller _ctrl;
	private JFileChooser fc;
	private JButton _lefButton;
	private JButton _cccButton;
	private JButton _cwButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JButton _exitButton;
	private JLabel _tickLabel;
	private JSpinner _tickSpinner;
	private boolean _stopped;

	public ControlPanel(Controller ctrl) {
		super();
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		setLayout(new FlowLayout(FlowLayout.LEADING));
		JPanel tb = new JPanel();
		tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
		JPanel tb2 = new JPanel();
		tb2.setLayout(new BoxLayout(tb2, BoxLayout.X_AXIS));

		_stopped = true;
		fc = new JFileChooser();
		
		tb.add(new JSeparator(SwingConstants.VERTICAL)); 
		tb.add(Box.createRigidArea(new Dimension(2, 0))); 
		tb.add(new JSeparator(SwingConstants.VERTICAL)); 
		tb.add(Box.createRigidArea(new Dimension(5, 0))); 
		
		// load event file button
		_lefButton = new JButton();
		_lefButton.setIcon( new ImageIcon(iconsPath + "open.png") );
		_lefButton.setToolTipText("Load from a file a list of events");
		_lefButton.addActionListener((x) -> {
			lefButtonAction();
		});
		tb.add(_lefButton);
		
		tb.add(Box.createRigidArea(new Dimension(5, 0))); 
		tb.add(new JSeparator(SwingConstants.VERTICAL)); 
		tb.add(Box.createRigidArea(new Dimension(5, 0)));
		
		// change contamination class button
		_cccButton = new JButton();
		_cccButton.setIcon( new ImageIcon(iconsPath + "co2class.png") );
		_cccButton.setToolTipText("Change contamination class of vehicles");
		_cccButton.addActionListener((x) -> {
			cccButtonAction();
		});
		tb.add(_cccButton);
		
		// change weather button
		_cwButton = new JButton();
		_cwButton.setIcon( new ImageIcon(iconsPath + "weather.png") );
		_cwButton.setToolTipText("Change weather of roads");
		_cwButton.addActionListener((x) -> {
			cwButtonAction();
		});
		tb.add(_cwButton);
		
		tb.add(Box.createRigidArea(new Dimension(5, 0))); 
		tb.add(new JSeparator(SwingConstants.VERTICAL)); 
		tb.add(Box.createRigidArea(new Dimension(5, 0)));
		
		// run button
		_runButton = new JButton();
		_runButton.setIcon( new ImageIcon(iconsPath + "run.png") );
		_runButton.setToolTipText("Run the game for the selected amount of ticks");
		_runButton.addActionListener((x) -> {
			runButtonAction();
		});
		tb.add(_runButton);
		
		// stop button
		_stopButton = new JButton();	
		_stopButton.setIcon( new ImageIcon(iconsPath + "stop.png") );	
		_stopButton.setToolTipText("Stops current simulation");
		_stopButton.addActionListener((x) -> {
			stopButtonAction();
		});
		tb.add(_stopButton);
		
		tb.add(Box.createRigidArea(new Dimension(5, 0))); 
		
		// tick label and spinner
		_tickLabel = new JLabel("Ticks: ");
		_tickSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 1000, 1));
		tb.add(_tickLabel);
		tb.add(_tickSpinner);
		
		tb.add(Box.createRigidArea(new Dimension(10, 0))); 
		tb.add(new JSeparator(SwingConstants.VERTICAL)); 
		tb.add(Box.createRigidArea(new Dimension(5, 0)));
		
		// exit button
		_exitButton = new JButton();
		_exitButton.setIcon( new ImageIcon(iconsPath + "exit.png") );	
		_exitButton.setToolTipText("Close application");
		_exitButton.addActionListener((x) -> {
			exitButtonAction();
		});
		tb2.add(_exitButton);		
		
		this.add(tb);
		this.add(tb2);
	}
	
	private void lefButtonAction() {
		if (_stopped ) {
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				FileInputStream is = null;
				try {
					is = new FileInputStream(f);
				} catch (FileNotFoundException e) {}
				if (is != null) {
					_ctrl.loadEvents(is);
				}
			}			
		}
	}

	private void cccButtonAction() {
		if (_stopped ) {
			class CccDialog extends JDialog {
				private int _status = 0;
				JComboBox<String> _vehicles;
				JComboBox<Integer> _contaminationClasses;
				JSpinner _tickSpinner;
				public CccDialog(JFrame frame) {
					super(frame, true);
					initGUI();
				}
				
				public int getStatus() {
					return _status;
				}
				
				private void initGUI() {
					setTitle("Change CO2 Class");
					JPanel mainPanel = new JPanel(new BorderLayout());
					setContentPane(mainPanel);
					
					// dialog description
					JLabel dialogDescription = new JLabel();
					dialogDescription.setText("<html>" + _cccDescription + "</html>");				
					mainPanel.add(dialogDescription, BorderLayout.PAGE_START);
					
					// choosing and modifying vehicle
					JPanel bodyPanel = new JPanel();
					bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));
					
					JLabel vehicle = new JLabel("Vehicle: ");
					
					String[] vehicleArray = new String[_ctrl.getVehicles().size()];
					for ( int i = 0; i < _ctrl.getVehicles().size(); i++) {
						vehicleArray[i] = _ctrl.getVehicles().get(i).toString();
					}
					
					 
					
					_vehicles = new JComboBox<String>(new DefaultComboBoxModel<String>(vehicleArray));
					if (vehicleArray.length == 0) {
						_vehicles.setSelectedIndex(-1);
					} else {
						_vehicles.setSelectedIndex(0);
					}
					
					JLabel cc = new JLabel("CO2 class: ");
					Integer[] ccs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
					_contaminationClasses = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>(ccs));
					_contaminationClasses.setSelectedIndex(0);
					
					JLabel tickLabel = new JLabel("Ticks: ");
					_tickSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
					
					bodyPanel.add(Box.createRigidArea(new Dimension(10, 0)));
					bodyPanel.add(vehicle);
					bodyPanel.add(Box.createRigidArea(new Dimension(5, 0)));
					bodyPanel.add(_vehicles);
					bodyPanel.add(Box.createRigidArea(new Dimension(10, 0)));
					bodyPanel.add(cc);
					bodyPanel.add(Box.createRigidArea(new Dimension(5, 0)));
					bodyPanel.add(_contaminationClasses);
					bodyPanel.add(Box.createRigidArea(new Dimension(10, 0)));
					bodyPanel.add(tickLabel);
					bodyPanel.add(Box.createRigidArea(new Dimension(5, 0)));
					bodyPanel.add(_tickSpinner);
					bodyPanel.add(Box.createRigidArea(new Dimension(10, 0)));
					mainPanel.add(bodyPanel, BorderLayout.CENTER);
					
					// buttons
					JPanel buttonsPanel = new JPanel();
					JButton cancelButton = new JButton("Cancel"); cancelButton.addActionListener((e) -> {
					_status = 0; setVisible(false); });
					buttonsPanel.add(cancelButton);
					JButton OKButton = new JButton("OK"); OKButton.addActionListener((e) -> {
					_status = 1; setVisible(false); });
					buttonsPanel.add(OKButton);
					mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
					this.pack();
					setVisible(true);
			 	}

				public Object getSelectedVehicle() {
					return _vehicles.getSelectedItem();
				}

				public Object getSelectedContClass() {
					return _contaminationClasses.getSelectedItem();
				}

				public int extraTicks() {
					return (int) _tickSpinner.getValue();
				};
			}
		 	
			CccDialog cccDialog = new CccDialog(new JFrame());
		 	if (cccDialog.getStatus() == 1 && !_ctrl.getVehicles().isEmpty()) {
		 		List<Pair<String, Integer>> pairList = new ArrayList<>();
		 		pairList.add(new Pair<>(String.valueOf(cccDialog.getSelectedVehicle()), Integer.valueOf(String.valueOf(cccDialog.getSelectedContClass()))));
		 		_ctrl.addEvent(new SetContClassEvent(_ctrl.getTime() + cccDialog.extraTicks(), pairList));
		 	}
		}
	}

	private void cwButtonAction() {
		if (_stopped ) {
			class CwDialog extends JDialog {
				private int _status = 0;
				JComboBox<String> _roads;
				JComboBox<String> _weathers;
				JSpinner _tickSpinner;
				public CwDialog(JFrame frame) {
					super(frame, true);
					initGUI();
				}
				
				public int getStatus() {
					return _status;
				}
				
				private void initGUI() {
					setTitle("Change Road Weather");
					JPanel mainPanel = new JPanel(new BorderLayout());
					setContentPane(mainPanel);
					
					// dialog description
					JLabel dialogDescription = new JLabel();
					dialogDescription.setText("<html>" + _cwDescription + "</html>");				
					mainPanel.add(dialogDescription, BorderLayout.PAGE_START);
					
					// choosing and modifying vehicle
					JPanel bodyPanel = new JPanel();
					bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));
					
					JLabel road = new JLabel("Road: ");
					
					String[] roadArray = new String[_ctrl.getRoads().size()];
					for ( int i = 0; i < _ctrl.getVehicles().size(); i++) {
						roadArray[i] = _ctrl.getVehicles().get(i).toString();
					}
					
					 
					
					_roads = new JComboBox<String>(new DefaultComboBoxModel<String>(roadArray));
					if (roadArray.length == 0) {
						_roads.setSelectedIndex(-1);
					} else {
						_roads.setSelectedIndex(0);
					}
					
					JLabel weather = new JLabel("Weather: ");
					Weather[] ws = Weather.values();
					String[] weathers = new String[ws.length];
					for ( int i = 0; i < ws.length; i++) {
						weathers[i] = ws[i].toString();
					}
					
					_weathers = new JComboBox<String>(new DefaultComboBoxModel<String>(weathers));
					_weathers.setSelectedIndex(0);
					
					JLabel tickLabel = new JLabel("Ticks: ");
					_tickSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
					
					bodyPanel.add(Box.createRigidArea(new Dimension(10, 0)));
					bodyPanel.add(road);
					bodyPanel.add(Box.createRigidArea(new Dimension(5, 0)));
					bodyPanel.add(_roads);
					bodyPanel.add(Box.createRigidArea(new Dimension(10, 0)));
					bodyPanel.add(weather);
					bodyPanel.add(Box.createRigidArea(new Dimension(5, 0)));
					bodyPanel.add(_weathers);
					bodyPanel.add(Box.createRigidArea(new Dimension(10, 0)));
					bodyPanel.add(tickLabel);
					bodyPanel.add(Box.createRigidArea(new Dimension(5, 0)));
					bodyPanel.add(_tickSpinner);
					bodyPanel.add(Box.createRigidArea(new Dimension(10, 0)));
					mainPanel.add(bodyPanel, BorderLayout.CENTER);
					
					// buttons
					JPanel buttonsPanel = new JPanel();
					JButton cancelButton = new JButton("Cancel"); cancelButton.addActionListener((e) -> {
					_status = 0; setVisible(false); });
					buttonsPanel.add(cancelButton);
					JButton OKButton = new JButton("OK"); OKButton.addActionListener((e) -> {
					_status = 1; setVisible(false); });
					buttonsPanel.add(OKButton);
					mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
					this.pack();
					setVisible(true);
			 	}

				public Object getSelectedRoad() {
					return _roads.getSelectedItem();
				}

				public Object getSelectedWeather() {
					return _weathers.getSelectedItem();
				}

				public int extraTicks() {
					return (int) _tickSpinner.getValue();
				};
			}
		 	
			CwDialog cwDialog = new CwDialog(new JFrame());
		 	if (cwDialog.getStatus() == 1 && !_ctrl.getRoads().isEmpty()) {
		 		List<Pair<String, Weather>> pairList = new ArrayList<>();
		 		pairList.add(new Pair<>(String.valueOf(cwDialog.getSelectedRoad()), Weather.valueOf(String.valueOf(cwDialog.getSelectedWeather()))));
		 		_ctrl.addEvent(new SetWeatherEvent(_ctrl.getTime() + cwDialog.extraTicks(), pairList));
		 	}
		}
	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
	         		SwingUtilities.invokeLater(() -> run_sim(n - 1));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Unexpected error while running simulation", "Simulation error", JOptionPane.ERROR_MESSAGE);
				_stopped = true;
			}
		} else {
			_stopped = true;
		}
	}
	
	private void runButtonAction() {
		_stopped = false;
		run_sim((int) _tickSpinner.getValue());
	}

	private void stopButtonAction() {
		_stopped = true;
	}

	private void exitButtonAction() {
		int n = JOptionPane.showOptionDialog(this, "Are sure you want to quit?", "Close Traffic Simulator",
				 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		
		if (n == 0) { 
			System.exit(0);
		}
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {

	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {

	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {

	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {	
	}
}
