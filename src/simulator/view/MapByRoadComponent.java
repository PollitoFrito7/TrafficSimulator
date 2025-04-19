package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	private Image _car;
	private RoadMap _roadMap;
	
	private static final int _JUNCTION_RADIUS = 10;
	
	private static final Color _SOURCE_JUNCTION = Color.BLUE;
	private static final Color _BACKGROUND_COLOR = Color.WHITE;
	private static final Color _ROAD_LINE = Color.BLACK;
	private static final Color _DEST_JUNCT_RED = Color.RED;
	private static final Color _DEST_JUNCT_GREEN = Color.GREEN;
	
	public MapByRoadComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		setPreferredSize(new Dimension(300, 200));
		_car = loadImage("car.png");
	}

	public void paintComponent(Graphics graphics) {
		//draw a white background and smooth rendering for both text and shapes
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setColor(_BACKGROUND_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_roadMap == null || _roadMap.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics2D g) {
		drawRoads(g);
		drawJunctions(g);
		
	}


	private void drawJunctions(Graphics2D g) {
		for (int i = 0; i < _roadMap.getRoads().size(); i++) {
			Road road = _roadMap.getRoads().get(i);
			int x1 = 50;
			int x2 = getWidth() - 100;
			int y = (i + 1)* 50;
			
			Junction srcJunct = road.getSrc();
			g.setColor(_SOURCE_JUNCTION);
			g.fillOval(x1 - _JUNCTION_RADIUS / 2, y - _JUNCTION_RADIUS / 2, _JUNCTION_RADIUS, _JUNCTION_RADIUS);
			g.setColor(Color.RED);
			g.drawString(srcJunct.getId(), x1 - _JUNCTION_RADIUS/2, y - _JUNCTION_RADIUS);
			
			Junction destJunct = road.getDest();
			int greenIdx = destJunct.getGreenLightIndex();
			Color endColorJunct = _DEST_JUNCT_RED;
			if(greenIdx != -1 && road.equals(destJunct.getInRoads().get(greenIdx))) {
				endColorJunct = _DEST_JUNCT_GREEN;
			}
			
			g.setColor(endColorJunct);
			g.fillOval(x2 - _JUNCTION_RADIUS / 2, y - _JUNCTION_RADIUS / 2, _JUNCTION_RADIUS, _JUNCTION_RADIUS);
			g.setColor(Color.RED);
			g.drawString(destJunct.getId(), x2 - _JUNCTION_RADIUS/2, y - _JUNCTION_RADIUS);
		}
	}

	private void drawRoads(Graphics2D g) {
		for (int i = 0; i < _roadMap.getRoads().size(); i++) {
			Road road = _roadMap.getRoads().get(i);
			int x1 = 50;
			int x2 = getWidth() - 100;
			int y = (i+1)*50;
			
			g.setColor(_ROAD_LINE);
			g.drawLine(x1, y, x2, y);
			g.drawString(road.getId(), x1 - 30, y);
			
			//vehicles ç
			List<Vehicle> vehicles = road.getVehicles();
			for (int j = 0; j < vehicles.size(); j++) {
				int x = x1 + (int) ((x2 - x1) * ((double) vehicles.get(j).getLocation() / (double) _roadMap.getRoads().get(j).getLength()));
				g.drawImage(_car, x, y - 10, 16, 16, this);
				g.drawString(vehicles.get(j).getId(), x, y - 16);
			}
			
			Image weatherImg = null;
			
			if(road.getWeather() == Weather.CLOUDY) {
				weatherImg = loadImage("cloud.png");
			}
			else if(road.getWeather() == Weather.RAINY) {
				weatherImg = loadImage("rain.png");
			}
			else if(road.getWeather() == Weather.STORM) {
				weatherImg = loadImage("storm.png");
			}
			else if(road.getWeather() == Weather.SUNNY) {
				weatherImg = loadImage("sun.png");
			}
			else if(road.getWeather() == Weather.WINDY) {
				weatherImg = loadImage("wind.png");
			}
			if (weatherImg != null) {
				g.drawImage(weatherImg, getWidth()- 90, y - 20, 32, 32, this);				
			}
			
			int c = (int) Math.floor(Math.min((double) road.getTotalCO2()/(1.0 + (double) road.getContLimit()),1.0) / 0.19);
			
			g.drawImage(loadImage("cont_" + Integer.toString(c) + ".png"), getWidth() - 58, y - 20, 32, 32, this);
		}
	}

	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _roadMap.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}
	private Image loadImage(String string) {
		Image image = null;
		try {
			return ImageIO.read(new File("resources/icons/" + string));
		} catch (IOException e) {
		}
		return image;
	}

	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_roadMap = map;
			repaint();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map);	
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

}
