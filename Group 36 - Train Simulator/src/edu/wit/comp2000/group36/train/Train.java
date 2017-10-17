package edu.wit.comp2000.group36.train;

import java.util.ArrayList;

public class Train {
	private ArrayList<Passenger> passengers;
	private int location;
	private int id;
	private final int maxCapacity = 50;
	private boolean isInbound;
	
	Train(){
		
	}
	
	boolean load(Passenger p) {
		return false;
	}
	
	void unload() {
		
	}
	
	
	public int getLocation() {
		return location;
	}
	
	public boolean isInbound() {
		return isInbound;
	}
	
}
