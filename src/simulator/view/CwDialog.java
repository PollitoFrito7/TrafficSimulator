package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.Weather;

@SuppressWarnings("serial")
class CwDialog extends JDialog {
	private final String _cwDescription = "Schedule an event to change the weather of a road after a given number of"
			+ "<br>" + "simulation ticks from now" + "<br>" + " ‎";
	private int _status = 0;
	JComboBox<String> _roads;
	JComboBox<String> _weathers;
	JSpinner _tickSpinner;

	public CwDialog(JFrame frame, List<Road> roads) {
		super(frame, true);
		initGUI(roads);
	}

	public int getStatus() {
		return _status;
	}

	private void initGUI(List<Road> roads) {
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

		String[] roadArray = new String[roads.size()];
		for (int i = 0; i < roads.size(); i++) {
			roadArray[i] = roads.get(i).toString();
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
		for (int i = 0; i < ws.length; i++) {
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
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((e) -> {
			_status = 0;
			setVisible(false);
		});
		buttonsPanel.add(cancelButton);
		JButton OKButton = new JButton("OK");
		OKButton.addActionListener((e) -> {
			_status = 1;
			setVisible(false);
		});
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