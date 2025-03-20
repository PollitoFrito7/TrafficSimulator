package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	private final String iconsPath = "resources/icons/";
	private Controller _ctrl;
	private JFileChooser fc;
	private JButton _lefButton;
	private JButton _cccButton;
	private JButton _cwButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JButton _exitButton;

	public ControlPanel(Controller ctrl) {
		super();
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel tb = new JPanel();
		JPanel tb2 = new JPanel();
		tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
		tb2.setLayout(new BoxLayout(tb2, BoxLayout.X_AXIS));
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
//		_lefButton.setAlignmentX(LEFT_ALIGNMENT);
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
		
		// exit button
		_exitButton = new JButton();
		_exitButton.setIcon( new ImageIcon(iconsPath + "exit.png") );	
		_exitButton.setToolTipText("Close application");
		_exitButton.addActionListener((x) -> {
			exitButtonAction();
		});
		tb2.add(_exitButton);
		
		wrapper.add(tb);
		wrapper.add(tb2);
		wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
		wrapper.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		this.add(wrapper);
	}
	
	private void lefButtonAction() {
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		File f = fc.getSelectedFile();
		FileInputStream is = null;
		try {
			is = new FileInputStream(f);
		} catch (FileNotFoundException e) {}
		if (is != null)
			_ctrl.loadEvents(is);
		}
	}

	private void cccButtonAction() {
		// TODO Auto-generated method stub
		
	}

	private void cwButtonAction() {
		// TODO Auto-generated method stub
		
	}

	private void runButtonAction() {
		// TODO Auto-generated method stub
		
	}

	private void stopButtonAction() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {	
	}
}
