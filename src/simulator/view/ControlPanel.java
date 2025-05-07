package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements TrafficSimObserver {
	private final String iconsPath = "resources/icons/";
	private final String FilesPath = "resources/examples/";
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
	private List<Vehicle> _vehicles;
	private List<Road> _roads;
	private int _time;

	public ControlPanel(Controller ctrl) {
		super();
		_ctrl = ctrl;
		_time = 0;
		_ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		// natural look of the GUI
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(false);

		_stopped = true;
		fc = new JFileChooser(FilesPath);

		add(Box.createRigidArea(new Dimension(5, 0)));

		// load event file button
		_lefButton = new JButton();
		_lefButton.setIcon(new ImageIcon(iconsPath + "open.png"));
		_lefButton.setToolTipText("Load from a file a list of events");
		_lefButton.addActionListener((x) -> {
			lefButtonAction();
		});
		add(_lefButton);

		add(Box.createRigidArea(new Dimension(5, 0)));

		// change contamination class button
		_cccButton = new JButton();
		_cccButton.setIcon(new ImageIcon(iconsPath + "co2class.png"));
		_cccButton.setToolTipText("Change contamination class of vehicles");
		_cccButton.addActionListener((x) -> {
			cccButtonAction();
		});
		add(_cccButton);

		// change weather button
		_cwButton = new JButton();
		_cwButton.setIcon(new ImageIcon(iconsPath + "weather.png"));
		_cwButton.setToolTipText("Change weather of roads");
		_cwButton.addActionListener((x) -> {
			cwButtonAction();
		});
		add(_cwButton);

		add(Box.createRigidArea(new Dimension(5, 0)));

		// run button
		_runButton = new JButton();
		_runButton.setIcon(new ImageIcon(iconsPath + "run.png"));
		_runButton.setToolTipText("Run the game for the selected amount of ticks");
		_runButton.addActionListener((x) -> {
			runButtonAction();
		});
		add(_runButton);

		// stop button
		_stopButton = new JButton();
		_stopButton.setIcon(new ImageIcon(iconsPath + "stop.png"));
		_stopButton.setToolTipText("Stops current simulation");
		_stopButton.addActionListener((x) -> {
			stopButtonAction();
		});
		add(_stopButton);

		add(Box.createRigidArea(new Dimension(5, 0)));

		// tick label and spinner
		_tickLabel = new JLabel("Ticks: ");
		_tickSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 1000, 1));
		_tickSpinner.setMaximumSize(new Dimension(60, 30));
		add(_tickLabel);
		add(_tickSpinner);

		add(Box.createRigidArea(new Dimension(10, 0)));

		// exit button
		_exitButton = new JButton();
		_exitButton.setIcon(new ImageIcon(iconsPath + "exit.png"));
		_exitButton.setToolTipText("Close application");
		_exitButton.addActionListener((x) -> {
			exitButtonAction();
		});
		add(Box.createHorizontalGlue());
		add(_exitButton);
	}

	private void lefButtonAction() {
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			FileInputStream is = null;
			try {
				is = new FileInputStream(f);
			} catch (FileNotFoundException e) {
			}
			if (is != null) {
				_ctrl.reset();
				_ctrl.loadEvents(is);
			}
		}

	}

	private void cccButtonAction() {
		CccDialog cccDialog = new CccDialog(new JFrame(), _vehicles);
		if (cccDialog.getStatus() == 1 && !_vehicles.isEmpty()) {
			List<Pair<String, Integer>> pairList = new ArrayList<>();
			pairList.add(new Pair<>(String.valueOf(cccDialog.getSelectedVehicle()),
					Integer.valueOf(String.valueOf(cccDialog.getSelectedContClass()))));
			_ctrl.addEvent(new SetContClassEvent(_time + cccDialog.extraTicks(), pairList));
		}

	}

	private void cwButtonAction() {
		CwDialog cwDialog = new CwDialog(new JFrame(), _roads);
		if (cwDialog.getStatus() == 1 && !_roads.isEmpty()) {
			List<Pair<String, Weather>> pairList = new ArrayList<>();
			pairList.add(new Pair<>(String.valueOf(cwDialog.getSelectedRoad()),
					Weather.valueOf(String.valueOf(cwDialog.getSelectedWeather()))));
			_ctrl.addEvent(new SetWeatherEvent(_time + cwDialog.extraTicks(), pairList));
		}

	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
				SwingUtilities.invokeLater(() -> run_sim(n - 1));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Unexpected error while running simulation", "Simulation error",
						JOptionPane.ERROR_MESSAGE);
				_stopped = true;
			}
		} else {
			_stopped = true;
			enableButtons();
		}
	}

	private void runButtonAction() {
		_stopped = false;
		_lefButton.setEnabled(false);
		_cccButton.setEnabled(false);
		_cwButton.setEnabled(false);
		_runButton.setEnabled(false);
		_exitButton.setEnabled(false);
		SwingUtilities.invokeLater(() -> run_sim((int) _tickSpinner.getValue()));
	}

	private void enableButtons() {
		_lefButton.setEnabled(true);
		_cccButton.setEnabled(true);
		_cwButton.setEnabled(true);
		_runButton.setEnabled(true);
		_exitButton.setEnabled(true);
	}

	private void stopButtonAction() {
		_stopped = true;
		enableButtons();
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
		_vehicles = map.getVehicles();
		_roads = map.getRoads();
		_time = time;
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {

	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_vehicles = map.getVehicles();
		_roads = map.getRoads();
		_time = time;
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_vehicles = map.getVehicles();
		_roads = map.getRoads();
		_time = time;
	}
}
