package edu.wit.comp2000.group36.train;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 	Utility class used to read in simulation settings from a file. 
 *  This class provides a value for all of the configurable settings in the simulation. <p>
 * 
 * 	If a value was not specified in the config file, then a Default Value is used. 
 * 	This default value my be different for each variable.
 * 
 *  @author Joshua Cilfone
 */
public class ConfigParser {
	/**
	 *  The character used to denote a Comment in file. <br>
	 *  Anything that follows this character will be ignored by the parser.
	 */
	private static final char COMMENT_CHAR = '#';

	/**
	 *  The name of the Config File to be used by the simulation
	 */
	private static final String CONFIG_FILE = "TrainSimulation.config";

	/**
	 *  The map of Key-Values from the Config File
	 */
	private static final HashMap<String, String> VALUES;
	
	/* 
	 *  Once this class is referenced it will load the Config File
	 */
	static {
		VALUES = new HashMap<>();
		
		try(Scanner scan = new Scanner(new File(CONFIG_FILE))) {
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				int commentIndex = line.indexOf(COMMENT_CHAR);
				if(commentIndex > -1) line = line.substring(0, commentIndex); 
				
				line = line.trim();
				if(line.isEmpty()) continue; // Line only contained Comment or White Space
				
				String[] parts = line.split("=", 2);
				if(parts.length < 2) continue; // Line did not contain a '='
				
				VALUES.put(parts[0].toLowerCase().trim(), parts[1].trim());
			}
		} catch(Exception e) {
			// TODO: Log that we were unable to parse file
			e.printStackTrace();
		}
	} 
	
	/**
	 *  Looks up the value in the VALUES map associated with the given Key. 
	 *  
	 *  <b> NOTE: </b> The Key that is provided is converted {@link String#toLowerCase() to lower case} before being used.
	 * 
	 *  @param key The Key of the value to look up
	 *  @return he value mapped to the provided Key, or null if no such entry exists
	 *  
	 *  @see #lookUp(String, String)
	 */
	private static String lookUp(String key) { return lookUp(key, null); }
	
	/**
	 *  Looks up the value in the VALUES map associated with the given Key. 
	 *  If no entry is found then the provided <code> DefaulValue </code> will be returned. <p>
	 *  
	 *  <b> NOTE: </b> The Key that is provided is converted {@link String#toLowerCase() to lower case} before being used.
	 *  
	 *  @param key The Key of the value to look up
	 *  @param defaultVal The default value to return if the key's entry does not exist
	 *  
	 *  @return The value mapped to the provided Key, or the default value if no such entry exists
	 */
	private static String lookUp(String key, String defaultVal) {
		return VALUES.getOrDefault(key.toLowerCase(), defaultVal);
	}
	
	/**
	 *  The standard Location returned if the Location cannot be determined
	 */
	private static final int INVALID_LOCATION = -1;
	
	/**
	 *  Calculates a location give a raw location value and the maximum length of the track. <p>
	 *  
	 *  This method is capable of converting a Integer Location from 0 to <code> maxLength </code>, or 
	 *  reading in a Double value and determining the appropriate location of 0 to <code> maxLength </code>.
	 * 
	 *  @param raw The raw location to convert
	 *  @param maxLength The maximum length of the track
	 *  
	 *  @return The parsed version of the provided raw String location, 
	 *  			or {@link #INVALID_LOCATION} if it fails to parse the location
	 */
	private static int getLocation(String raw, int maxLength) {
		try { 
			int location = Integer.parseInt(raw);  	// Attempts to read in the values as Integer Locations
			if(location > maxLength || location < 0) throw new NumberFormatException("Invalid Location Value: " + location);
			return location;
		} catch(NumberFormatException ignore) {   	// If that Fails, then
			try { 
				double val = Double.parseDouble(raw);
				if(val > 1 || val < 0) throw new NumberFormatException("Invalid Location Value: " + val);
				return (int) (maxLength * val);  	// Read in the numbers as % of the Track
				
			} catch(NumberFormatException e) {
				return INVALID_LOCATION;			// If we're unable to parse the Location, just return INVALID_LOCATION 
			}
		}
	} 
	
//	----------------------------------------------------------------------------------------------------------------------- \\
//	---------------------------------------------- Simulation ------------------------------------------------------------- \\
//	----------------------------------------------------------------------------------------------------------------------- \\
	
	private static final String RNG_SEED_KEY = "Seed";
	private static final long RNG_SEED_DEFAULT = System.currentTimeMillis();
	
	public static long getSeed() {
		String raw = lookUp(RNG_SEED_KEY);
		return raw == null ? RNG_SEED_DEFAULT : Long.parseLong(raw);
	}
	
	private static final String SIMULATION_DURATION_KEY = "Simulation Duration";
	private static final int SIMULATION_DURATION_DEFAULT = 1_000;
	
	public static int getSimulationDuration() {
		String raw = lookUp(SIMULATION_DURATION_KEY);
		return raw == null ? SIMULATION_DURATION_DEFAULT : Integer.parseInt(raw);
	}
	
//	----------------------------------------------------------------------------------------------------------------------- \\
//	---------------------------------------------- Train Route ------------------------------------------------------------ \\
//	----------------------------------------------------------------------------------------------------------------------- \\
	
	private static final String ROUTE_LENGTH_KEY = "Route Length";
	private static final int ROUTE_LENGTH_DEFAULT = 25;
	
	public static int getRouteLength() {
		String raw = lookUp(ROUTE_LENGTH_KEY);
		return raw == null ? ROUTE_LENGTH_DEFAULT : Integer.parseInt(raw);
	}
	
//	----------------------------------------------------------------------------------------------------------------------- \\
//	---------------------------------------------- Passengers ------------------------------------------------------------- \\
//	----------------------------------------------------------------------------------------------------------------------- \\
	
	private static final String MAX_PASS_CREATE_KEY = "Max Passenger Creation";
	private static final int MAX_PASS_CREATE_DEFAULT = 5;
	
	public static int getMaxPassenegerCreatedPerTick() {
		String raw = lookUp(MAX_PASS_CREATE_KEY);
		return raw == null ? MAX_PASS_CREATE_DEFAULT : Integer.parseInt(raw);
	}
	
	private static final String MIN_PASS_CREATE_KEY = "Min Passenger Creation";
	private static final int MIN_PASS_CREATE_DEFAULT = 0;
	
	public static int getMinPassenegerCreatedPerTick() {
		String raw = lookUp(MIN_PASS_CREATE_KEY);
		return raw == null ? MIN_PASS_CREATE_DEFAULT : Integer.parseInt(raw);
	}

//	----------------------------------------------------------------------------------------------------------------------- \\
//	----------------------------------------------- Stations -------------------------------------------------------------- \\
//	----------------------------------------------------------------------------------------------------------------------- \\
	
	private static final String STATIONS_KEY = "Stations";
	private static final String STATIONS_DEFAULT = ".25, .75";
	
	/**
	 *  Create a new Set of {@link Station Stations} at the provided locations in the config. file, 
	 *  and adds the stations to the provided {@link TrainRoute}.
	 *  
	 *  @param route The route to add the Stations to
	 *  @return an Array of all of the newly created Stations
	 */
	public static Station[] createStations(TrainRoute route) {
		String raw = lookUp(STATIONS_KEY, STATIONS_DEFAULT);
		String[] stationLocs = raw.split(",");
		
		int trackLength = getRouteLength();
		
		ArrayList<Station> stations = new ArrayList<>();
		for(String locRoaw : stationLocs) {
			String locRaw = locRoaw.trim();
			
			int location = getLocation(locRaw, trackLength);
			if(location == INVALID_LOCATION) continue; // TODO: Log Invalid Location
			
			stations.add(new Station(location, route));
			route.addStation(stations.get(stations.size() - 1));
		}
		
		return stations.toArray(new Station[0]);
	}

//	----------------------------------------------------------------------------------------------------------------------- \\
//	------------------------------------------------- Trains -------------------------------------------------------------- \\
//	----------------------------------------------------------------------------------------------------------------------- \\
	
	private static final String TRAIN_DEFAULT_CAPACITY_KEY = "Train Default Capacity";
	private static final int TRAIN_DEFAULT_CAPACITY_DEFAULT = 100;
	
	public static int getDefaultTrainCapacity() {
		String raw = lookUp(TRAIN_DEFAULT_CAPACITY_KEY);
		return raw == null ? TRAIN_DEFAULT_CAPACITY_DEFAULT : Integer.parseInt(raw);
	}
	
	private static final String TRAINS_KEY = "Trains";
	private static final String TRAINS_DEFAULT = "I/.5/25 O/.75/50";
	
	/**
	 *  Create a new Set of {@link Train Trains} at the provided locations in the config. file, 
	 *  and adds the stations to the provided {@link TrainRoute}.
	 *  
	 *  @return an Array of all of the newly created Trains
	 */
	public static Train[] createTrains() {
		String raw = lookUp(TRAINS_KEY, TRAINS_DEFAULT);
		String[] trainsRaw = raw.split(" ");
		
		int trackLength = getRouteLength();
		int defaultCapacity = getDefaultTrainCapacity();
		
		ArrayList<Train> trains = new ArrayList<>();
		for(String trainDataRaw : trainsRaw) {
			trainDataRaw = trainDataRaw.trim();

			String[] trainData = trainDataRaw.split("/");
			if(trainData.length < 2) continue; // TODO: Log Invalid Train Setup

			boolean isInbound = trainData[0].toLowerCase().startsWith("i");
			
			int location = getLocation(trainData[1], trackLength);
			if(location == INVALID_LOCATION) continue; // TODO: Log Invalid Location
			
			int capacity = trainData.length > 2 ? Integer.parseInt(trainData[2]) : defaultCapacity;
			
//			trains.add(new Train(isInbound, location, capacity));
		}
		
		return trains.toArray(new Train[0]);
	}
}
