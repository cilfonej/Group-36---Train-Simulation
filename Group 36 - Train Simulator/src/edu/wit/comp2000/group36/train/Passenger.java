package edu.wit.comp2000.group36.train;

import java.util.Scanner;

/**
 * @author Leslie Vongphakdy
 * COMP 2000 - 03
 * Lab 3: Queues, Train Simulator
 * Group 36
 * Due: October 30, 2017
 **/

public class Passenger {
	
	private Station start;
	private Station end;
	private static int nextID = 1;
	private static int ID;
	private boolean isInbound; //TODO: calculate isInbound
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
	}
	
	/**
	 * Checks the direction of the train
	 * @return if it's inbound, true. if it's outbound, false.
	 **/
	public boolean IsInbound() {
		checkInitialization();
		return isInbound;
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
		return ID;
	}
	
	/**
	 * Calls and returns Station start Location (id) 
	 * from Station class.
	 * @return
	 **/
	public Station getStart() {
		return start;
	}
	
	/**
	 * Calls and returns Station destination Location (id)
	 * from Station class.
	 * @return
	 **/
	public Station getEnd() {
		return end;
	}
	
	/**
	 *  sets up output for Passenger's itinerary
	 **/
	public String toString() {
		String result = " ";
		result += "Ticket Itinerary:";
		result += "Passenger " + ID + "\t";
		result += "Station coming from is " + start.getLocation() + "\t";
		result += "Station they're going to is " + end.getLocation() + "\t";
		result += "IsInbound.";
		return result;
	}
	
	public static void main(String[]args) {
		Scanner s = new Scanner(System.in);
		
		System.out.print("Enter passenger ID to see itinerary: ");
		ID = s.nextInt();
		

		
		
	}
}
