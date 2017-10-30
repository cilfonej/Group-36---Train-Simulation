package edu.wit.comp2000.group36.train;

import com.pearson.carrano.ArrayQueue;

/*
 *  Group 36
 *  Brandon Horowitz
 *   
 *  Comp 2000-03: Data Structures, Fall, 2017
 *  APP 3: Queue APP
 *  Due: 10/30/2017
 */

/**
 * a simple construct used to keep track of passengers
 * This class is also responsible for determining whether passengers should join the inbound queue or outbound queue
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
	private int inboundCount, outboundCount;
	
	/**
	 * takes in an integer representation of the Station's location, checks it against the trainroute's distance for validity
	 * if the location is valid, assigns location to the instance variable of the same name
	 * @param location
	 * @param trainRoute
	 */
	public Station(int location, TrainRoute trainRoute){
		if(location >= 0 && location <= trainRoute.getDistance() ) {
		this.location = location;
		this.trainRoute = trainRoute;
		trainRoute.addStation(this);
		initialized = true;
		} else {
			throw new IllegalArgumentException();
		} // end else
	} // end constructor
	
	/**
	 * loads a passenger onto the station in the queue that will take them to their destination fastest (inbound or outbound).
	 * if inbound and outbound distances are equal, then the passenger will be assigned to the inbound queue.
	 * @param p: Passenger to load into queue
	 */
	public void load(Passenger p){
		checkInitialization();
		//should inbound / outbound checking be done in station or TrainRoute? I think it should be done in Trainroute
		if(passengerIsInbound(p)) {
			inbound.enqueue(p);
			inboundCount ++;
			log(p.toString() + " assigned to inbound at " + this.toString());
		} else {
			outbound.enqueue(p);
			outboundCount ++;
			log(p.toString() + " assigned to outbound at " + this.toString());
		} // end else
	} // end load
	
	/**
	 * unloads passengers into train until either the relevant queue is empty or the train is full.
	 * @param t: Train to unload passengers into
	 */
	public void unload(Train t){
		checkInitialization();
		boolean tIsFull = false;
		if(t.isInbound()) {
			while(!inbound.isEmpty() && !tIsFull) {
				if(!(tIsFull = !t.load(inbound.getFront()))) {
					inbound.dequeue();
					inboundCount --;
				} // end if
			} // end while
		} else { // t is outbound
			while(!outbound.isEmpty() && !tIsFull) {
				if(!(tIsFull = !t.load(outbound.getFront()))) {
					outbound.dequeue();
					outboundCount --;
				} // end if
			} // end while
		} // end else
	} // end unload
	
	/**
	 * @return location
	 */
	public int getLocation() { checkInitialization(); return location;}
	public int getInboundWaiting() { checkInitialization(); return inboundCount; }
	public int getOutboundWaiting() { checkInitialization(); return outboundCount; }
	
	/**
	 * toString method for Station
	 */
	public String toString() {
		checkInitialization();
		return "Station at location " + this.getLocation();
	} // end toString
	
	/**
	 * submits String to logger
	 * @param s: String to submit
	 */
	private void log(String s) {
		checkInitialization();
		Logger.logging(s);
	} // end log
	
	/**
	 * Checks the direction of the train
	 * @return if it's inbound, true. if it's outbound, false.
	 **/
	private boolean passengerIsInbound(Passenger p) {
		checkInitialization();
		return trainRoute.calculateDistance(p.getStart(), p.getEnd(), true) <= trainRoute.calculateDistance(p.getStart(), p.getEnd(), false);
	} // end passengerIsInbound
	
	/**
	 * checks if the station has been initialized
	 */
	private void checkInitialization() {
		if ( !initialized )
		{
			throw new SecurityException( "Station is not properly initialized." ) ;
		} //end if
	} // end checkInitialization

//	-------------------------------------------------------------------------------------------------------------- \\
//	----------------------------------- Test Methods ------------------------------------------------------------- \\
//	-------------------------------------------------------------------------------------------------------------- \\
	
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
		System.out.println("\n---------------\nTesting getLocation");
		s = new Station(3, new TrainRoute(4));
		printTest(true, "creating station at location 3 on TrainRoute of length 4 " , Integer.toString(s.getLocation()), "3");
		
		s = new Station(0, new TrainRoute(5));
		printTest(true, "creating station at location 0 on TrainRoute of length 5 " , Integer.toString(s.getLocation()), "0");
		
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
		Station temp;
		for(int i = 0; i < 5; i++) {
			try {
				temp = new Station(i, tr);
				s.load(new Passenger(s, temp));
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		for(int i = 6; i < tr.getDistance(); i++) {
			try {
				temp = new Station(i, tr);
				s.load(new Passenger(s, temp));
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		printTest(true, "dequeuing outbound", s.outbound.dequeue().toString(), " Passenger 1");
		printTest(true, "dequeuing outbound", s.outbound.dequeue().toString(), " Passenger 6");
		printTest(true, "dequeuing outbound", s.outbound.dequeue().toString(), " Passenger 7");
		printTest(true, "dequeuing inbound", s.inbound.dequeue().toString(), " Passenger 2");
		printTest(true, "dequeuing inbound", s.inbound.dequeue().toString(), " Passenger 3");
		printTest(true, "dequeuing inbound", s.inbound.dequeue().toString(), " Passenger 4");
		printTest(true, "dequeuing inbound", s.inbound.dequeue().toString(), " Passenger 5");
		System.out.println("\n---------------\nFinished testing Load");
	}

	private static void testUnload(Station s) {
		System.out.println("\n---------------\ntesting Unload");
		TrainRoute tr = new TrainRoute(8);
		s = new Station(5, tr);
		Station temp;
		for(int i = 0; i < 5; i++) {
			try {
				temp = new Station(i, tr);
				s.load(new Passenger(s, temp));
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		for(int i = 6; i < tr.getDistance(); i++) {
			try {
				temp = new Station(i, tr);
				tr.addStation(temp);
				s.load(new Passenger(s, temp));
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		System.out.println();
		System.out.println("inbound waiting: " + s.getInboundWaiting());
		System.out.println("outbound waiting: " + s.getOutboundWaiting());
		System.out.println("unloading to inbound train");
		s.unload(new Train(true, 5, tr));
		printTest(true, "inbound count, outbound count", s.getInboundWaiting() + ", " + s.getOutboundWaiting(), "0, 3");
		System.out.println("unloading to outbound train");
		s.unload(new Train(false, 5, tr));
		printTest(true, "inbound count, outbound count", s.getInboundWaiting() + ", " + s.getOutboundWaiting(), "0, 0");
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
				System.out.println("invalid entry at location = " + i + " " + e.toString());
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
