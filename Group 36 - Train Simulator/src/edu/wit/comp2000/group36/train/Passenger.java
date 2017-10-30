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
		Logger.logging("Passenger " + ID + " is coming from " + start.toString());
		Logger.logging("Pasenger " + ID + " is going to " + end.toString());
		
	}
	
	private void checkInitialization() {
		if ( !initialized )
		{
			throw new SecurityException( "Passenger is not initialized." ) ;
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
		return "Passenger " + ID;
	}
	
	/**
	* Test method
	**/
	public static void main(String[]args) {
			System.out.println("Testing Passenger Class");
		Passenger p = null;
		testGetters(p);
	}

	/**
	* Sets train route.
	* 1st passenger will go from station 2 to station 5.
	* 2nd passenger will go from station 3 to station 6.
	* 3rd passenger will go from station 2 to station 5.
	**/
	private static void testGetters(Passenger p) {
		// TODO Auto-generated method stub
		TrainRoute tr = new TrainRoute(10);
		p = new Passenger(new Station(2, tr), new Station(5, tr));
		System.out.print(p.toString() + " starts at " + p.getStart().toString() + " and ends at " + p.getEnd().toString() + "\n");
		p = new Passenger(new Station(3, tr), new Station(6, tr));
		System.out.print(p.toString() + " starts at " + p.getStart().toString() + " and ends at " + p.getEnd().toString() + "\n");
		p = new Passenger(new Station(2, tr), new Station(5, tr));
		System.out.print(p.toString() + " starts at " + p.getStart().toString() + " and ends at " + p.getEnd().toString() + "\n");
	}

}
