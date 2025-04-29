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

import simulator.model.Vehicle;

@SuppressWarnings("serial")
class CccDialog extends JDialog {
	private final String _cccDescription = "Schedule an event to change the CO2 class of a vehicle after a given number"
			+ "<br>" + "of simulation ticks from now." + "<br>" + " ‎";
	private int _status = 0;
	JComboBox<String> _vehicles;
	JComboBox<Integer> _contaminationClasses;
	JSpinner _tickSpinner;

	public CccDialog(JFrame frame, List<Vehicle> vehicles) {
		super(frame, true);
		initGUI(vehicles);
	}

	public int getStatus() {
		return _status;
	}

	private void initGUI(List<Vehicle> vehicles) {
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

		String[] vehicleArray = new String[vehicles.size()];
		for (int i = 0; i < vehicles.size(); i++) {
			vehicleArray[i] = vehicles.get(i).toString();
		}

		_vehicles = new JComboBox<String>(new DefaultComboBoxModel<String>(vehicleArray));
		if (vehicleArray.length == 0) {
			_vehicles.setSelectedIndex(-1);
		} else {
			_vehicles.setSelectedIndex(0);
		}

		JLabel cc = new JLabel("CO2 class: ");
		Integer[] ccs = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
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