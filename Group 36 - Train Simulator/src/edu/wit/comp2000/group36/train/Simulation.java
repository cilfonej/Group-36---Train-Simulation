package edu.wit.comp2000.group36.train;

import java.util.Random;

/*
 *  Group 36
 *  Joshua Cilfone
 *   
 *  Comp 2000-03: Data Structures, Fall, 2017
 *  ADT 3: Queue ADT
 *  Due: 10/30/2017
 */

/**
 *  This class represents and controls the Simulation. 
 *  
 *  This class is responsible for creating new {@link Passenger Passengers} and stepping through the simulation.
 *  
 *  @author Joshua Cilfone
 */
public class Simulation {
	public static void main(String[] args) {
		Simulation simulation = new Simulation();
		
		for(int i = 0; i < 1_000_000; i ++) {
			simulation.step();
		}
	}
	
	private final Random RAND;
	
	private TrainRoute route;
	private Train[] trains;
	
	private int minPassengerCreation, maxPassengerCreation;
	private int passengerCreationRange;
	
	public Simulation() {
		RAND = new Random(ConfigParser.getSeed());
		
		route = new TrainRoute(ConfigParser.getRouteLength());
		
		minPassengerCreation = ConfigParser.getMinPassenegerCreatedPerTick();
		maxPassengerCreation = ConfigParser.getMinPassenegerCreatedPerTick();
		passengerCreationRange = maxPassengerCreation - minPassengerCreation;
		
		if(route.getStationCount() < 2) throw new IllegalStateException("There must be at least 2 Stations Loaded");
	}
	
	/**
	 *  Runs the Simulation for one "Step" <br>
	 *  This causes all {@link Train Trains} to move one unit forward and attempts to create new {@link Passenger Passengers}.
	 */
	public void step() {
		int creationCount = RAND.nextInt(passengerCreationRange) + minPassengerCreation;
		for(int i = 0; i < creationCount; i ++) {
			int count = route.getStationCount();
			int startIndex = RAND.nextInt(count);
			int destIndex = (RAND.nextInt(count - 1) + 1 + startIndex) % count;

			Station start = route.getStation(startIndex);
			Station end = route.getStation(destIndex);
			
			start.load(new Passenger(start, end));
		}
	}

	public TrainRoute getRoute() { return route; }
	public Train[] getTrains() { return trains; }
}
