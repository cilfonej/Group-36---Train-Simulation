package edu.wit.comp2000.group36.train;

public class Passenger {
	private Station start;
	private Station end;
	private static int nextID = 1;
	private int ID;
	private boolean isInbound; //TODO: calculate isInbound
	private boolean initialized = false;
	
	public Passenger(Station start, Station end) {
		ID = nextID;
		nextID++;
		this.start = start;
		this.end = end;
		initialized = true;
	}
	
	public boolean getIsInbound() {
		checkInitialization();
		return isInbound;
	}
	
	private void checkInitialization() {
		if ( !initialized )
		{
			throw new SecurityException( "Calculator is not properly initialized." ) ;
		} //end if
	} // end checkInitialization
}
