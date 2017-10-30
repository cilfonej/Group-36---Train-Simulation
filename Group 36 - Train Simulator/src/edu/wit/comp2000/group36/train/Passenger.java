package edu.wit.comp2000.group36.train;

/**
 * @author Leslie Vongphakdy
 * COMP 2000 - 03
 * Lab 3: Queues, Train Simulator
 * Group 36
 * Due: October 30, 2017
 **/

public class Passenger {
	
	private final Station start;
	private final Station end;
	private static int nextID = 1;
	private int ID;
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
		Logger.logging("Passenger " + ID + " is coming from " + i);
		Logger.logging("Pasenger " + ID + " is going to " + end);
		
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
			System.out.println("Testing Passenger Class");
		Passenger p = null;
		testGetters(p);
	}

	private static void testGetters(Passenger p) {
		// TODO Auto-generated method stub
		p = new Passenger(2, new Station(2), new Station(5));
		System.out.print(toString(getID(2)) + " " + p);
	}

	public static int getID() {
		return ID;
	}

	public static void setID(int iD) {
		ID = iD;
	}
}
