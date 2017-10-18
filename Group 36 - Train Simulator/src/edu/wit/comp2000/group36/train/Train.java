package edu.wit.comp2000.group36.train;

import java.util.ArrayList;

public class Train {
	private ArrayList<Passenger> passengers;
	private int location;
	private int id;
	private final int maxCapacity = 50;
	private boolean isInbound;
	private boolean initialized = false;
	
	public Train(){
		
		initialized = true;
	}
	
	public boolean load(Passenger p) {
		checkInitialization();
		return false;
	}
	
	public void unload() {
		checkInitialization();
		
	}
	
	
	public int getLocation() {
		checkInitialization();
		return location;
	}
	
	public boolean isInbound() {
		checkInitialization();
		return isInbound;
	}
	
	private void checkInitialization() {
		if ( !initialized )
		{
			throw new SecurityException( "Calculator is not properly initialized." ) ;
		} //end if
	} // end checkInitialization
}
