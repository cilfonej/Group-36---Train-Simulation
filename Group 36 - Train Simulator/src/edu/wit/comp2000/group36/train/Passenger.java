package edu.wit.comp2000.group36.train;

public class Passenger {
	private Station start;
	private Station end;
	private static int nextID = 1;
	private int ID;
	
	Passenger(Station start, Station end) {
		ID = nextID;
		nextID++;
		this.start = start;
		this.end = end;
		
	}
	
	
}
