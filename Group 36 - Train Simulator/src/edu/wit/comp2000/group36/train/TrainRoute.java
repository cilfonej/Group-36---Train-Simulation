package edu.wit.comp2000.group36.train;

import java.util.ArrayList;

public class TrainRoute {
	private ArrayList<Station> stations;
	private int distance;
	private boolean initialized;
	
	public TrainRoute(int distance){
		this.distance = distance;
		initialized = true;
	}
	
	public int calculateDistance(Station start, Station end, boolean isInbound) {
		checkInitialization();
		return 0;
	}
	
	public void addStation(Station s) {
		checkInitialization();
		
	}
	
	public int getDistance() {
		checkInitialization();
		return distance;
	}
	
	private void checkInitialization() {
		if ( !initialized )
		{
			throw new SecurityException( "Calculator is not properly initialized." ) ;
		} //end if
	} // end checkInitialization
}
