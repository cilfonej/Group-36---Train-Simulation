package edu.wit.comp2000.group36.train;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

// Capacity
// 

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
	
//	----------------------------------------------------------------------------------------------------------------------- \\
//	---------------------------------------------- Simulation ------------------------------------------------------------- \\
//	----------------------------------------------------------------------------------------------------------------------- \\
	
	private static final String RNG_SEED_KEY = "Seed";
	private static final long RNG_SEED_DEFAULT = System.currentTimeMillis();
	
	public static long getSeed() {
		String raw = lookUp(RNG_SEED_KEY);
		return raw == null ? RNG_SEED_DEFAULT : Long.parseLong(raw);
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
	
	public static Station[] createStations() {
		String raw = lookUp(MIN_PASS_CREATE_KEY, STATIONS_DEFAULT);
		String[] stationLocs = raw.split(",");
		
		int trackLength = getRouteLength();
		
		Station[] stations = new Station[stationLocs.length];
		for(int i = 0; i < stationLocs.length; i ++) {
			String locRaw = stationLocs[i].trim();
			int location;
			
			try { location = Integer.parseInt(locRaw); }
			catch(NumberFormatException ignore) {
				try { 
					location = (int) (trackLength * Double.parseDouble(locRaw)); 
				} catch(NumberFormatException e) {
					continue;
				}
			}
			
//			stations[i] = new Station(location);
		}
		
		return stations;
	}
}
