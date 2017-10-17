package edu.wit.comp2000.group36.train;

import java.util.ArrayList;

public class TrainRoute {
	private ArrayList<Station> stations;
	private int distance;
	
	TrainRoute(int distance){
		this.distance = distance;
	}
	
	int calculateDistance(Station start, Station end, boolean isInbound) {
		return 0;
	}
	
	void addStation(Station s) {
		
	}
	
	public int getDistance() {
		return distance;
	}
}
