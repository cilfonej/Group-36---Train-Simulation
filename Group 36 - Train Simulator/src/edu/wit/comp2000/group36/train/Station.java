package edu.wit.comp2000.group36.train;

public class Station {
	private ArrayQueue<Passenger> Inbound; //clockwise
	private ArrayQueue<Passenger> Outbound; //counterclockwise
	private int location;
	
	Station(int location){
		this.location = location;
	}
	
	void load(Passenger p){
		
	}
	
	void unload(Train t){
		
	}
	
	int getLocation() {
		return location;
	}
}
