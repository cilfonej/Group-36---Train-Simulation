package edu.wit.comp2000.group36.train;

import java.util.ArrayList;

/*
 *  Group 36
 *  Brandon Horowitz
 *   
 *  Comp 2000-03: Data Structures, Fall, 2017
 *  ADT 3: Queue ADT
 *  Due: 10/30/2017
 */

public class Train {
	private ArrayList<Passenger> passengers = new ArrayList<>();
	private int location;
	private int ID;
	private static int nextID = 1;
	private final int maxCapacity;
	private final static int DEFAULT_CAPACITY = 50;
	private boolean isInbound;
	private TrainRoute tr;
	private boolean initialized = false;
	private int justBoarded;
	
	/**
	 * calls constructor with default value for maxCapacity
	 * @param isInbound
	 * @param startLocation
	 * @param tr
	 */
	public Train(boolean isInbound, int startLocation, TrainRoute tr) {
		this(DEFAULT_CAPACITY, isInbound, startLocation, tr);
	}
	
	/**
	 * assigns each train a capacity for how many passengers it can hold,
	 * a boolean for whether the train is inbound,
	 * an integer representation for a starting location,
	 * the trainroute it belongs to,
	 * and automatically assigns the train an ID number.
	 * will throw an exception if the start location is invalid or the maxCapacity is < 0
	 * @param maxCapacity
	 * @param isInbound
	 * @param startLocation
	 * @param tr
	 */
	public Train(int maxCapacity, boolean isInbound, int startLocation, TrainRoute tr){
		if(maxCapacity >= 0 && startLocation >= 0 && startLocation <= tr.getDistance()) {
			this.maxCapacity = maxCapacity;
			this.location = startLocation;
			this.isInbound = isInbound;
			this.tr = tr;
			ID = nextID;
			nextID++;
			initialized = true;
		} else {
			throw new IllegalArgumentException();
		}
	} // end constructor
	
	/**
	 * adds the passenger to the train if the train has room.
	 * @param p: Passenger to load on to the train
	 * @return true if the train can hold the passenger, false otherwise
	 */
	public boolean load(Passenger p) {
		checkInitialization();
		if(passengers.size() < maxCapacity) {
			passengers.add(p);
			log(p.toString() + " got on " + this.toString());
			justBoarded ++;
			return true;
		}else {
			return false;
		} // end else
	} // end load
	
	/**
	 * removes passengers from the train if they have arrived at their end station
	 */
	public void unload() {
		checkInitialization();
		for(int i = passengers.size() - 1; i >= 0; i--) {
			if(passengers.get(i).getEnd().getLocation() == this.getLocation()) {
				log(passengers.get(i).toString() + " got off at location " + location);
				passengers.remove(i);
			} // end if
		} // end for
	} // end unload
	
	/**
	 * simulates the train's movement and loading / unloading.
	 */
	public void simulate() {
		justBoarded = 0;
		
		if(this.isInbound()) {
			location--;
		}else {
			location++;
		} // end else
		//if location > distance or < 0 do stuff
		if(location < 0) {
			location = tr.getDistance();
		}else if(location > tr.getDistance()) {
			location = 0;
		} // end else if
		log(this.toString() + " moved to location " + location);
		
		unload();
		
		Station currentStation = tr.getStationAtLocation(location);
		if(currentStation != null) {
			currentStation.unload(this);
		}
	} // end simulate 
	
	public int getLocation() { checkInitialization(); return location; }
	public int getMaxCapacity() { checkInitialization(); return maxCapacity; }
	public boolean isInbound() { checkInitialization(); return isInbound; }
	public int getID() { checkInitialization(); return ID; }
	public int getPassengerCount() { checkInitialization(); return passengers.size(); }
	public int getJustBoarded() { checkInitialization(); return justBoarded; }
	
	/**
	 * toString method for train.
	 */
	public String toString() {
		checkInitialization();
		return "train " + ID;
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
	 * checks that the train has been initialized
	 */
	private void checkInitialization() {
		if ( !initialized )
		{
			throw new SecurityException( "Train is not properly initialized." ) ;
		} //end if
	} // end checkInitialization

//	-------------------------------------------------------------------------------------------------------------- \\
//	----------------------------------- Test Methods ------------------------------------------------------------- \\
//	-------------------------------------------------------------------------------------------------------------- \\
	
	public static void main(String[] args) {
		System.out.println("\n---------------\nTesting Train");
		Train t = null;
		testConstructor(t);
		TrainRoute tr = new TrainRoute(100);
		testGetters(t);
		testLoad(t);
		testUnload(t);
		testSimulate(t);
		testToString(t);
		System.out.println("\n---------------\nFinished testing Train");
	}

	private static void testSimulate(Train t) {
		System.out.println("\n---------------\ntesting simulate");
		TrainRoute tr = new TrainRoute(10);
		Train tOutbound = new Train(false, 10, tr);
		Train tInbound = new Train (true, 1, tr);
		Station[] stations = new Station[5];
		for(int i = 0; i < stations.length - 1; i++) {
			stations[i] = new Station(i, tr);
		}
		stations[4] = new Station(9, tr);
		stations[0].load(new Passenger(stations[0], stations[1]));
		stations[0].load(new Passenger(stations[0], stations[1]));
		stations[0].load(new Passenger(stations[0], stations[1]));
		stations[1].load(new Passenger(stations[1], stations[2]));
		stations[3].load(new Passenger(stations[3], stations[4]));
		stations[4].load(new Passenger(stations[4], stations[1]));
		
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "0");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "3");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "1");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "0");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "0");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "0");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "0");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "0");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "0");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "0");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "1");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "1");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "1");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "0");
		tOutbound.simulate();
		printTest(true, "outbound passenger count at location " + tOutbound.getLocation(), Integer.toString(tOutbound.getPassengerCount()), "0");
		
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "1");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "1");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "1");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "1");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "1");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		tInbound.simulate();
		printTest(true, "inbound passenger count at location " + tInbound.getLocation(), Integer.toString(tInbound.getPassengerCount()), "0");
		System.out.println("\n---------------\nFinished testing simulate");
	}

	private static void testConstructor(Train t) {
		TrainRoute tr = new TrainRoute(5);
		System.out.println("\n---------------\nTesting Constructor");
		System.out.println("trainroute length = " + Integer.toString(tr.getDistance()));
		try {
			t = new Train(true, 2, tr);
			System.out.println("valid entry at default capacity = 50 & startlocation = " + t.getLocation());
		} catch (Exception e) {
			System.out.println("invalid entry at default capacity = 50 & start location = 2" + e.toString());
		} // end catch
		
		for(int i = -1; i < 7; i++) {
			try {
				t = new Train(false, i, tr);
				System.out.println("valid entry at default capacity & location = " + i);
			} catch (Exception e) {
				System.out.println("invalid entry at default capacity & location = " + i + " " + e.toString());
			} // end catch
		} // end for
		
		try {
			t = new Train(-1, true, 3, tr);
			System.out.println("valid entry with max capacity = -1");
		} catch (Exception e) {
			System.out.println("invalid entry with max capacity = -1" + e.toString());
		}
		System.out.println("\n---------------\nFinished testing Constructor");
	}

	private static void testGetters(Train t) {
		System.out.println("\n---------------\ntesting Getters");
		
		TrainRoute tr = new TrainRoute(10);
		t = new Train(true, 4, tr);
		
		printTest(true, "testing getLocation", Integer.toString(t.getLocation()), "4");

		printTest(true, "testing getMaxCapacity", Integer.toString(t.getMaxCapacity()), "50");

		printTest(true, "testing getIsInbound", Boolean.toString(t.isInbound()), "true");

		printTest(true, "testing getID", Integer.toString(t.getID()), "8");
		
		System.out.println("\n---------------\nFinished testing Getters");
	}

	private static void testLoad(Train t) {
		System.out.println("\n---------------\n\testing Load");
		TrainRoute tr = new TrainRoute(10);
		t = new Train(1, true, 1, tr);
		Station s1 = new Station(1, tr);
		Station s2 = new Station(8, tr);
		printTest(true, "testing load with passenger on unfilled train", Boolean.toString(t.load(new Passenger(s1, s2))), "true");
		printTest(true, "testing load with passenger on filled train", Boolean.toString(t.load(new Passenger(s1, s2))), "false");
		System.out.println("\n---------------\nFinished testing Load");
	}

	private static void testUnload(Train t) {
		System.out.println("\n---------------\ntesting unload");
		TrainRoute tr = new TrainRoute(10);
		t = new Train(true, 0, tr);
		Station[] stations = new Station[5];
		for(int i = 0; i < stations.length - 1; i++) {
			stations[i] = new Station(i, tr);
		}
		stations[4] = new Station(10, tr);
		t.load(new Passenger(stations[0], stations[1]));
		t.load(new Passenger(stations[0], stations[1]));
		t.load(new Passenger(stations[0], stations[1]));
		t.load(new Passenger(stations[0], stations[2]));
		t.load(new Passenger(stations[0], stations[3]));
		t.load(new Passenger(stations[0], stations[4]));
		t.load(new Passenger(stations[0], stations[4]));
		
		t.unload();
		printTest(true, "passenger count at location " + t.getLocation(), Integer.toString(t.getPassengerCount()), "7");
		t.location = 1;
		t.unload();
		printTest(true, "passenger count at location " + t.getLocation(), Integer.toString(t.getPassengerCount()), "4");
		t.location = 2;
		t.unload();
		printTest(true, "passenger count at location " + t.getLocation(), Integer.toString(t.getPassengerCount()), "3");
		t.location = 3;
		t.unload();
		printTest(true, "passenger count at location " + t.getLocation(), Integer.toString(t.getPassengerCount()), "2");
		t.location = 4;
		t.unload();
		printTest(true, "passenger count at location " + t.getLocation(), Integer.toString(t.getPassengerCount()), "2");
		t.location = 9;
		t.unload();
		printTest(true, "passenger count at location " + t.getLocation(), Integer.toString(t.getPassengerCount()), "2");
		t.location = 10;
		t.unload();
		printTest(true, "passenger count at location " + t.getLocation(), Integer.toString(t.getPassengerCount()), "0");
		System.out.println("\n---------------\nFinished testing unload");
	}

	private static void testToString(Train t) {
		System.out.println("\n---------------\ntesting toString");
		TrainRoute trainroute = new TrainRoute(100);
		Train train = new Train(true, 0, trainroute);
		printTest(true, "testing toString", train.toString(), "train 13");
		train = new Train(false, 10, trainroute);
		printTest(true, "testing toString", train.toString(), "train 14");
		train = new Train(true, 0, trainroute);
		printTest(true, "testing toString", train.toString(), "train 15");
		System.out.println("\n---------------\nFinished testing toString");
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
	
	

