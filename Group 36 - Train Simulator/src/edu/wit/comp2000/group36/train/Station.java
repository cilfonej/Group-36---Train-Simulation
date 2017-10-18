package edu.wit.comp2000.group36.train;

/**
 * 
 * @author horowitzb
 *
 */
public class Station {
	private ArrayQueue<Passenger> inbound; //clockwise
	private ArrayQueue<Passenger> outbound; //counterclockwise
	private int location;
	private boolean initialized = false;
	
	public Station(int location){
		this.location = location;
		initialized = true;
	} // end constructor
	
	public void load(Passenger p){
		checkInitialization();
		//should inbound / outbound checking be done in station or TrainRoute? I think it should be done in Trainroute
		if(p.IsInbound()) {
			inbound.enqueue(p);
		} else {
			outbound.enqueue(p);
		} // end else
	} // end load
	
	public void unload(Train t){
		checkInitialization();
		boolean tIsFull = false;
		if(t.isInbound()) {
			while(!inbound.isEmpty() && !tIsFull) {
				tIsFull = t.load(inbound.dequeue());
			} // end while
		} else { // t is outbound
			while(!outbound.isEmpty() && !tIsFull) {
				tIsFull = t.load(outbound.dequeue());
			} // end while
		} // end else
	} // end unload
	
	public int getLocation() {
		checkInitialization();
		return location;
	} // end getLocation
	
	public String toString() {
		checkInitialization();
		return null;
	} // end toString
	
	public void log() {
		checkInitialization();
	} // end log
	
	private void checkInitialization() {
		if ( !initialized )
		{
			throw new SecurityException( "Calculator is not properly initialized." ) ;
		} //end if
	} // end checkInitialization
	
	
	public static void main(String[] args) {
		System.out.println("\n---------------\nTesting Station");
		testConstructor();
		testGetters();
		testLoad();
		testUnload();
		testToString();
		System.out.println("\n---------------\nFinished testing Station");
	}

	private static void testConstructor() {
		System.out.println("\n---------------\nTesting Constructor");
		Station s = new Station(2);
		Station s = new Station
	}

	private static void testGetters() {
		// TODO Auto-generated method stub
		
	}
	
	private static void testLoad() {
		// TODO Auto-generated method stub
		
	}

	private static void testUnload() {
		// TODO Auto-generated method stub
		
	}

	private static void testToString() {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * Utility function to print out testing info.
     * @param isValid  is this testing valid parameters or invalid ones
     * @param description  a description of the test being run
     * @param received  output received by the test
     * @param expected  what the received output should be
     * @author Brandon Horowitz
     */
	private static void printTest( 	boolean isValid, String description, String recieved,String expected ){
		System.out.println( String.format( "Is Valid: %s%nDescription: %s%nRecieved: %s%nExpected: %s%n", isValid, description, recieved, expected ) ) ;
	} //end printTest
	
} // end Station
