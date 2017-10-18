package edu.wit.comp2000.group36.train;

public class Passenger {
	private Station start;
	private Station end;
	private static int nextID = 1;
	private int ID;
	private boolean isInbound; //TODO: calculate isInbound
	private boolean initialized = false;
	
	public Passenger(int ID, Station start, Station destination) {
		ID = nextID;
		nextID++;
		this.start = start;
		this.end = end;
		initialized = true;
	}
	
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
	
	public int getID() {
		return ID;
	}
	
	public Station getStart() {
		return start;
	}
	
	public Station getEnd() {
		return end;
	}
	 
	public String toString() {
		String result = " ";
		result += "Passenger " + ID + "\t";
		result += "Station coming from is " + start.getLocation() + "\t";
		result += "Station they're going to is " + end.getLocation() + "\t";
		result += "IsInbound.";
		return result;
	}
	
	public static void main(String[]args) {
		
	}
}
