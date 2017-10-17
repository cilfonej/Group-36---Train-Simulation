package edu.wit.comp2000.group36.train;

/*
 *  Joshua Cilfone
 */
public class Simulation {
	public static void main(String[] args) {
		Simulation simulation = new Simulation();
		
		for(int i = 0; i < 1_000_000; i ++) {
			simulation.step();
		}
	}
	
	private TrainRoute route;
	private Train[] trains;
	
	public Simulation() {
		
	}
	
	public void step() {
	}
	

	public TrainRoute getRoute() { return route; }
	public Train[] getTrains() { return trains; }
}
