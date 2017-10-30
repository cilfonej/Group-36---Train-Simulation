package edu.wit.comp2000.group36.train.visual.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

import javax.swing.JComponent;
import javax.swing.plaf.PanelUI;

import edu.wit.comp2000.group36.train.Simulation;
import edu.wit.comp2000.group36.train.visual.ui.SimulationUIComponents.Particle;
import edu.wit.comp2000.group36.train.visual.ui.SimulationUIComponents.StationDrawInfo;
import edu.wit.comp2000.group36.train.visual.ui.SimulationUIComponents.TrainDrawInfo;

/*
 *  Group 36
 *  Joshua Cilfone
 *   
 *  Comp 2000-03: Data Structures, Fall, 2017
 *  ADT 3: Queue ADT
 *  Due: 10/30/2017
 */

/**
 * @author Joshua Cilfone
 */
public class SimulationUI extends PanelUI implements ComponentListener {
	protected Simulation simulation;
	protected TrainDrawInfo[] trains;
	protected StationDrawInfo[] stations;
	
	protected Queue<Particle> particles;
	private SimulationUI_InfoManager info;
	
	private BufferedImage image;
	
	public SimulationUI(Simulation simulation) {
		this.simulation = simulation;
		this.trains = new TrainDrawInfo[simulation.getTrains().length];
		this.stations = new StationDrawInfo[simulation.getRoute().getStationCount()];
		
		this.particles = new ArrayDeque<>();
		
		for(int i = 0; i < trains.length; i ++) {
			trains[i] = new TrainDrawInfo(simulation.getTrains()[i], this);
		}
		
		for(int i = 0; i < stations.length; i ++) {
			stations[i] = new StationDrawInfo(simulation.getRoute().getStation(i), this);
		}
		
		stepMulti = (float) simulation.getRoute().getDistance() / 100;
		stepMulti = 1;
		
		image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
	}
	
	private static final int DELAY = 10;
	
	private static final float STEP_SIZE = .1f;
	private static final float STEP_SIZE_SQ = STEP_SIZE * STEP_SIZE;
	
	protected ArrayList<Point2D> pointsInt;
	protected ArrayList<Point2D> pointsOut;
	private Path2D pathInt;
	private Path2D pathOut;
	
	protected double stepCount, stepLimit;
	private float stepMulti;
	private double lastPerc;
	private boolean simulating;
	
	protected int scale, shiftX, shiftY;
	
	public void installUI(JComponent c) {
//		c.addComponentListener(this);
		
		info = new SimulationUI_InfoManager(this);
		c.addMouseMotionListener(info);
		c.addMouseListener(info);
		
		c.setLayout(null);
	}
	
	public void addParticle(Particle add) { this.particles.add(add); }
	
	public void prepSimulation(long milli) {
		stepLimit = milli / DELAY;
		simulating = true;
		stepCount = 0;
	}
	
	public void paint(Graphics g, JComponent c) {
		if(pathOut == null) componentResized(new ComponentEvent(c, 0));
		Graphics2D g2d = image.createGraphics();
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
		
//		int size = Math.min(c.getWidth(), c.getHeight());
//		if(c.getWidth() < c.getHeight()) {
//			g2d.translate(0, (c.getHeight() - size) / 2);
//		} else {
//			g2d.translate((c.getWidth() - size) / 2, 0);
//		}

		for(StationDrawInfo stations : stations) {
			stations.draw(g2d);
		}
		
		// Draw Tracks
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(3));
		
		g2d.draw(pathOut);
		g2d.draw(pathInt);

		g2d.setStroke(new BasicStroke(1));
		
		for(TrainDrawInfo train : trains) {
			train.draw(g2d);
		}
		
		for(StationDrawInfo stations : stations) {
			stations.drawInfo(g2d);
		}
		
		for(TrainDrawInfo train : trains) {
			train.drawInfo(g2d);
		}
		
		double perc = Math.min(1, (stepCount) / stepLimit);
		boolean simulateParticle = perc > 0 && simulating;
		for(int i = 0, limit = particles.size(); i < limit; i ++) {
			Particle particle = particles.poll();
			particle.draw(g2d);
			
			if(simulateParticle) particle.simulate(perc - lastPerc);
			if(!simulateParticle || !particle.isDead()) particles.add(particle);
		}
		
		lastPerc = perc;
		
		if(info.info != null) info.info.paint(g2d);
		
//		g2d.setColor(Color.RED);
//		for(int i = 0; i < simulation.getRoute().getDistance(); i ++) {
//			g2d.draw(new Line2D.Float(
//				pointsOut.get(getIndexForLocation(i, false)),
//				pointsInt.get(getIndexForLocation(i, true))
//			));
//		}
		
		g2d.dispose(); // -------------------------------------------------------------------------------------------
		
		scale = Math.min(c.getWidth(), c.getHeight());
		if(c.getWidth() < c.getHeight()) {
			shiftY = (c.getHeight() - scale) / 2;
			shiftX = 0;
			g.drawImage(image, shiftX, shiftY, scale, scale, null);
			
		} else {
			shiftY = 0;
			shiftX = (c.getWidth() - scale) / 2;
			g.drawImage(image, shiftX, shiftY, scale, scale, null);
		}
		
		
		if((stepCount += stepMulti) < stepLimit + stepMulti) {
			try { Thread.sleep(DELAY); } 
			catch(InterruptedException ignore) { }
			c.repaint();
			return;
		}

		lastPerc = 0;
		simulating = false;
		synchronized(this) { this.notifyAll(); }
	}
	
//	private int getIndexForLocation(int location, boolean inbound) {
//		ArrayList<Point2D> path = inbound ? pointsInt : pointsOut;
//		int length = simulation.getRoute().getDistance();
//		float locLength = (float) path.size() / length;
//		
//		return ((int) (location * locLength) + path.size()) % path.size();
//	}
	
	private void appendShape(Shape shape, Collection<Point2D> points, AffineTransform transform) {
		PathIterator iter = new FlatteningPathIterator(shape.getPathIterator(transform), 0.01f);
		Point2D p0 = null;
        
        float[] coords = new float[6];
        while(!iter.isDone()) {
            iter.currentSegment(coords);
            
            float x = (int) coords[0];
            float y = (int) coords[1];
            
            Point2D p = new Point2D.Float(x, y);
            if(p0 == null) {
            	p0 = p;
            	
            } else {
            	double distSq = p.distanceSq(p0);
            	if(distSq > STEP_SIZE_SQ) {
            		double dist = Math.sqrt(distSq);
            		double dx = p.getX() - p0.getX();
            		double dy = p.getY() - p0.getY();

            		double nx = dx / dist * STEP_SIZE;
            		double ny = dy / dist * STEP_SIZE;
            		
            		for(int i = 0; i < dist / STEP_SIZE; i ++) {
            			points.add(p0 = new Point2D.Double(p0.getX() + nx, p0.getY() + ny));
            		}
            	} 
            }
            
            iter.next();
        }
	}
	
	public void componentResized(ComponentEvent e) {
		pointsInt = new ArrayList<>();
		pointsOut = new ArrayList<>();
		
		pathInt = new GeneralPath();
		pathOut = new GeneralPath();
		
		int size = Math.min(e.getComponent().getWidth(), e.getComponent().getHeight());
		
		float outerLength = size * 7f / 8;					// 3/4
		float outerArc = outerLength * 4f / 16;
		float outerCorner = (size - outerLength) / 2;
		outerLength -= outerArc * 2;
		
		float innerLength = size * 13f / 16;					// 5/8
		float innerArc = innerLength * 4f / 16;
		float innerCorner = (size - innerLength) / 2;
		innerLength -= innerArc * 2;
		
		Arc2D outArc = new Arc2D.Float(outerCorner, outerCorner, outerArc * 2, outerArc * 2, 180, -90, Arc2D.OPEN);
		Line2D outLine = new Line2D.Float(outerCorner + outerArc, outerCorner, outerLength + outerCorner + outerArc, outerCorner);
		
		Arc2D intArc = new Arc2D.Float(innerCorner, innerCorner, innerArc * 2, innerArc * 2, 180, -90, Arc2D.OPEN);
		Line2D intLine = new Line2D.Float(innerCorner + innerArc, innerCorner, innerLength + innerCorner + innerArc, innerCorner);

		AffineTransform transform = new AffineTransform();
		
		for(int i = 0; i < 4; i ++) {
			appendShape(outArc, pointsOut, transform);
			appendShape(outLine, pointsOut, transform);
			
			appendShape(intArc, pointsInt, transform);
			appendShape(intLine, pointsInt, transform);
			
			pathOut.append(outArc.getPathIterator(transform), true);
			pathOut.append(outLine.getPathIterator(transform), true);
			
			pathInt.append(intArc.getPathIterator(transform), true);
			pathInt.append(intLine.getPathIterator(transform), true);
			
			transform.rotate(Math.PI / 2, size / 2, size / 2);
		}
		
		for(StationDrawInfo stations : stations) {
			stations.recalculate();
		}
	}

	public void componentMoved(ComponentEvent e) { }
	public void componentShown(ComponentEvent e) { }
	public void componentHidden(ComponentEvent e) { }
	
	public Dimension getPreferredSize(JComponent p) { return new Dimension(500, 500); }
}
