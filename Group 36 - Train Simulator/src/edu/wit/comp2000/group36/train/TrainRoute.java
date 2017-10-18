package edu.wit.comp2000.group36.train;

import java.util.ArrayList;

/*
 *  Group 36
 *  Joshua Cilfone
 *   
 *  Comp 2000-03: Data Structures, Fall, 2017
 *  ADT 3: Queue ADT
 *  Due: 10/30/2017
 */

/**
 *  A simple construct used to keep track of a collection of {@link Station Stations}. 
 *  This class is also responsible for calculating the distances between stations, 
 *  see {@link #calculateDistance(Station, Station, boolean)}.
 *  
 *  @author Joshua Cilfone
 */
public class TrainRoute {
	private ArrayList<Station> stations;
	private int distance;
	private boolean initialized;
	
	/**
	 *  Create a new TrainRoute with a length of the specified Distance
	 *  @param distance The total length of this TrainRoute
	 */
	public TrainRoute(int distance){
		this.distance = distance;
		this.stations = new ArrayList<>();
		
		initialized = true;
	}
	
	/**
	 * 	Calculates the distance between two {@link Station Stations} by moving along the track in a specified direction
	 * 
	 *  @param start The station to start the calculation from
	 *  @param end The station to find the distance to
	 *  @param inbound Whether the distance should be calculated going inbound or outbound from the start station
	 *  
	 *  @return The distance between the two specified Stations, given the direction
	 *  
	 *  @throws IllegalArgumentException If the start or end Stations are not found on this TrainRoute
	 */
	public int calculateDistance(Station start, Station end, boolean inbound) {
		checkInitialization();
		
		int startIndex = 0;
		for(; startIndex < stations.size(); startIndex ++) {
			if(stations.get(startIndex) == start) break;
		}
		
		if(startIndex >= stations.size()) throw new IllegalArgumentException(start + " is not on this TrainRoute");
		
		int dir = inbound ? -1 : 1;
		int distance = 0, lastLocation = start.getLocation();
		
		for(int i = (startIndex + dir + stations.size()) % stations.size(); ; i = (i + dir + stations.size()) % stations.size()) {
			Station check = stations.get(i);
			
			int diff = check.getLocation() - lastLocation;
			if(Math.signum(diff) != dir) { // Wrap to other end of Track
				distance += Math.min(check.getLocation(), lastLocation);
				distance += distance - Math.max(check.getLocation(), lastLocation);
			} else {
				distance += Math.abs(diff);
			}
			
			lastLocation = check.getLocation();
		
			if(check == end) {
				return distance;
				
			} else if(check == start) {
				throw new IllegalArgumentException(end + " is not on this TrainRoute");
			}
		}
	}
	
	/**
	 *  Adds a {@link Station} to the TrainRoute
	 *  @param s The station to add
	 */
	public void addStation(Station s) {
		checkInitialization();
		stations.add(s);
	}
	
	/**
	 *  Provides access the the total length of this TrainRoute
	 *  @return The length of the TrainRout
	 */
	public int getDistance() {
		checkInitialization();
		return distance;
	}
	
	/**
	 *  Provides the number of Station on this TrainRoute
	 *  @return The number of station on this TrainRoute
	 */
	public int getStationCount() { 
		checkInitialization();
		return stations.size(); 
	}
	
	/**
	 *  Returns a the {@link Station} at the specified index <p>
	 *  <b>NOTE:</b> This <code>index</code> is not the same as the <code>location</code>, for access to a Station
	 *  the {@link #getStationAtLocation(int)} method should be used
	 *  
	 *  @param index The index of the Station to get
	 *  @return The station stored with the specified index
	 */
	public Station getStation(int index) { 
		checkInitialization();
		return stations.get(index); 
	}
	
	/**
	 *  Finds the {@link Station} at the given location
	 *  
	 *  @param location The location to look for a Station at
	 *  @return The Station at the specified location, or null if no such station exists 
	 */
	public Station getStationAtLocation(int location) {
		for(Station station : stations) {
			if(station.getLocation() == location)
				return station;
		}
		
		return null;
	}
	
	/**
	 *  Checks if the Instance has been properly initialized
	 *  @throws SecurityException if the Instance has not been properly initialized
	 */
	private void checkInitialization() {
		if(!initialized) {
			throw new SecurityException("TrainRoute is not properly initialized") ;
		}
	}
}
