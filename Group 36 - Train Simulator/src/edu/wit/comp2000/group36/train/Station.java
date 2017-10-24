package edu.wit.comp2000.group36.train;

/**
 * 
 * @author horowitzb
 *
 */
public class Station {
	private ArrayQueue<Passenger> inbound = new ArrayQueue<>(); //clockwise
	private ArrayQueue<Passenger> outbound = new ArrayQueue<>(); //counterclockwise
	private int location;
	private TrainRoute trainRoute;
	private boolean initialized = false;
	
	public Station(int location, TrainRoute trainRoute){
		if(location >= 0 && location <= trainRoute.getDistance() ) {
		this.location = location;
		this.trainRoute = trainRoute;
		trainRoute.addStation(this);
		initialized = true;
		} else {
			throw new IllegalArgumentException();
		}
	} // end constructor
	
	public void load(Passenger p){
		checkInitialization();
		//should inbound / outbound checking be done in station or TrainRoute? I think it should be done in Trainroute
		if(passengerIsInbound(p)) {
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
				if(tIsFull = t.load(inbound.getFront())) {
					inbound.dequeue();
				}
			} // end while
		} else { // t is outbound
			while(!outbound.isEmpty() && !tIsFull) {
				if(tIsFull = t.load(outbound.getFront())) {
					outbound.dequeue();
				}
			} // end while
		} // end else
	} // end unload
	
	public int getLocation() {
		checkInitialization();
		return location;
	} // end getLocation
	
	public String toString() {
		checkInitialization();
		return "Station at location " + this.getLocation();
	} // end toString
	
	public void log() {
		checkInitialization();
	} // end log
	
	/**
	 * Checks the direction of the train
	 * @return if it's inbound, true. if it's outbound, false.
	 **/
	private boolean passengerIsInbound(Passenger p) {
		checkInitialization();
		return trainRoute.calculateDistance(p.getStart(), p.getEnd(), true) <= trainRoute.calculateDistance(p.getStart(), p.getEnd(), false);
	}
	
	private void checkInitialization() {
		if ( !initialized )
		{
			throw new SecurityException( "Calculator is not properly initialized." ) ;
		} //end if
	} // end checkInitialization
	
	
	public static void main(String[] args) {
		System.out.println("\n---------------\nTesting Station");
		Station s = null;
		testConstructor(s);
		testGetters(s);
		testLoad(s);
		testUnload(s);
		testToString(s);
		System.out.println("\n---------------\nFinished testing Station");
	}
	
	private static void testGetters(Station s) {
		System.out.println("\\n---------------\\nTesting getLocation");
		s = new Station(3, new TrainRoute(4));
		printTest(true, "creating station at location 3 on TrainRoute of length 4" , Integer.toString(s.getLocation()), "3");
		
		s = new Station(0, new TrainRoute(5));
		printTest(true, "creating station at location 0 on TrainRoute of length 5" , Integer.toString(s.getLocation()), "0");
		
		String location;
		try {
		s = new Station(6, new TrainRoute(4));
		location = Integer.toString(s.getLocation());
		} catch (Exception e) {
			location = e.toString();
		}
		printTest(true, "creating station at location 6 on TrainRoute of length 4" , location, "java.lang.IllegalArgumentException");
		
		System.out.println("\n---------------\nFinished testing getLocation");
	}

	private static void testLoad(Station s) {
		System.out.println("\n---------------\ntesting Load");
		TrainRoute tr = new TrainRoute(8);
		s = new Station(5, tr);
		tr.addStation(s);
		Station temp;
		for(int i = 0; i < tr.getDistance(); i++) {
			try {
				temp = new Station(i, tr);
				tr.addStation(temp);
				s.load(new Passenger(s, temp));
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		while(!s.outbound.isEmpty()) {
			printTest(true, "dequeuing outbound", s.outbound.dequeue().toString(), "expected");
		}
		while(!s.inbound.isEmpty()) {
			printTest(true, "dequeuing inbound", s.inbound.dequeue().toString(), "expected");
		}
//		printTest(true, "dequeuing outbound", s.outbound.dequeue().toString(), "expected");
//		printTest(true, "dequeuing outbound", s.outbound.dequeue().toString(), "expected");
//		printTest(true, "dequeuing outbound", s.outbound.dequeue().toString(), "expected");
//		printTest(true, "dequeuing inbound", s.inbound.dequeue().toString(), "expected");
//		printTest(true, "dequeuing inbound", s.inbound.dequeue().toString(), "expected");
//		printTest(true, "dequeuing inbound", s.inbound.dequeue().toString(), "expected");
//		printTest(true, "dequeuing inbound", s.inbound.dequeue().toString(), "expected");
//		printTest(true, "dequeuing inbound", s.inbound.dequeue().toString(), "expected");
		System.out.println("\n---------------\nFinished testing Load");
	}

	private static void testUnload(Station s) {
		System.out.println("\n---------------\ntesting Unload");
		//inbound passengers on inbound train
		//outbound passengers on outbound train
		//inbound passengers on outbound train
		//outbound passengers on inbound train
		//empty passengers on inbound train
		//empty passengers on outbound train
		//inbound passengers on full train
		//outbound passengers on almost full train
		
		System.out.println("\n---------------\nFinished testing Unload");
	}

	private static void testToString(Station s) {
		System.out.println("\n---------------\ntesting toString");
		TrainRoute tr = new TrainRoute(10);
		for(int i = 0; i <= tr.getDistance(); i++) {
			s = new Station(i, tr);
			printTest(true, "testing tostring with station at location " + s.getLocation() + " on track of length " + tr.getDistance(), s.toString() , "Station at location " + i);
		} // end for
		String errorString;
		try {
			s = new Station(tr.getDistance() + 1, tr);
			errorString = s.toString();
		}catch (Exception e) {
			errorString = e.toString();
		}
		printTest(false, "testing tostring with station at location 11 on track of length " + tr.getDistance(), errorString, "java.lang.IllegalArgumentException");
		
		System.out.println("\n---------------\nFinished testing toString");
	} // end testToString

	private static void testConstructor(Station s) {
		System.out.println("\n---------------\nTesting Constructor");
		System.out.println("trainroute length = 4");
		for(int i = -1; i < 6; i++) {
			try {
				s = new Station(i, new TrainRoute(4));
				System.out.println("valid entry at location = " + i);
			} catch (Exception e) {
				System.out.println("invalid entry at location = " + i + e.toString());
			} // end catch
		} // end for
		System.out.println("\n---------------\nFinished testing Constructor");
	} // end testConstructor

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
