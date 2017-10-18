package edu.wit.comp2000.group36.train;

import java.util.ArrayList;

/**
 * 
 * @author horowitzb
 *
 */
public class Train {
	private ArrayList<Passenger> passengers;
	private int location;
	private int id;
	private final int maxCapacity = 50;
	private boolean isInbound;
	private boolean initialized = false;
	
	public Train(boolean isInbound){
		this.isInbound = isInbound;
		initialized = true;
	} // end constructor
	
	public boolean load(Passenger p) {
		checkInitialization();
		passengers.add(p);
		return false;
	} // end load
	
	public void unload() {
		checkInitialization();
		for(int i = 0; i < passengers.size(); i++) {
			if(passengers.get(i).getEnd().getLocation() == this.getLocation()) {
				passengers.remove(i);
			} // end if
		} // end for
	} // end unload
	
	public void simulate() {
		if(this.isInbound()) {
			location--;
			
		}else {
			location++;
		} // end else
		//if location > distance or < 0 do stuff
	} // end simulate 
	
	public int getLocation() {
		checkInitialization();
		return location;
	} // end location
	
	public boolean isInbound() {
		checkInitialization();
		return isInbound;
	} // end isInbound
	
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
		System.out.println("\n---------------\nTesting Train");
		testConstructor();
		testGetters();
		testIsInbound();
		testLoad();
		testUnload();
		testToString();
		System.out.println("\n---------------\nFinished testing Train");
	}

	private static void testConstructor() {
		// TODO Auto-generated method stub
		
	}

	private static void testGetters() {
		// TODO Auto-generated method stub
		
	}

	private static void testIsInbound() {
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
	
}
