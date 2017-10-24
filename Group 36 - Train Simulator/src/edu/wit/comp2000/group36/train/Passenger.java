package edu.wit.comp2000.group36.train;

/**
 * @author Leslie Vongphakdy
 * COMP 2000 - 03
 * Lab 3: Queues, Train Simulator
 * Group 36
 * Due: October 30, 2017
 **/

public class Passenger {
	
	private static Station start;
	private static Station end;
	private static int nextID = 1;
	private static int ID;
	private boolean initialized = false;
	
	/**
	 * Gives every passenger a number id (aka their name).
	 * Parameters start and destination set where the passenger 
	 * is coming from and where they are going.
	 * @param ID
	 * @param start
	 * @param end 
	 **/
	public Passenger(Station start, Station end) {
		ID = nextID;
		nextID++;
		this.start = start;
		this.end = end;
		initialized = true;
		Logger.logging("Train is coming from " + start);
		Logger.logging("Train is going to " + end);
		
	}
	
	private void checkInitialization() {
		if ( !initialized )
		{
			throw new SecurityException( "Train is not inbound." ) ;
		} //end if
	} // end checkInitialization
	
	/**
	 * Calls and then returns id for passenger.
	 * @return
	 **/
	public int getID() {
		checkInitialization();
		return ID;
	}
	
	/**
	 * Calls and returns Station start Location (id) 
	 * from Station class.
	 * @return
	 **/
	public Station getStart() {
		checkInitialization();
		return start;
	}
	
	/**
	 * Calls and returns Station destination Location (id)
	 * from Station class.
	 * @return
	 **/
	public Station getEnd() {
		checkInitialization();
		return end;
	}
	
	/**
	 *  sets up output for Passenger's itinerary
	 **/
	public String toString() {
		String result = " ";
		result += "Passenger " + ID;
		return result;
	}
	
	public static void main(String[]args) {
		
	}
}
