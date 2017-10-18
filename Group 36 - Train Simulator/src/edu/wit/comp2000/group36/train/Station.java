package edu.wit.comp2000.group36.train;

/**
 * 
 * @author horowitzb
 *
 */
public class Station {
	private ArrayQueue<Passenger> inbound; //clockwise
	private ArrayQueue<Passenger> outbound; //counterclockwise
	private int location;
	private boolean initialized = false;
	
	public Station(int location){
		this.location = location;
		initialized = true;
	}
	
	public void load(Passenger p){
		checkInitialization();
		//should inbound / outbound checking be done in station or TrainRoute? I think it should be done in Trainroute
		if(p.getIsInbound()) {
			inbound.enqueue(p);
		} else {
			outbound.enqueue(p);
		}
	}
	
	public void unload(Train t){
		checkInitialization();
		boolean tIsFull = false;
		if(t.isInbound()) {
			while(!inbound.isEmpty() && !tIsFull) {
				tIsFull = t.load(inbound.dequeue());
			} // end while
		} else { // t is outbound
			while(!outbound.isEmpty() && !tIsFull) {
				tIsFull = t.load(outbound.dequeue());
			} // end while
		} // end else
	} // end unload
	
	public int getLocation() {
		checkInitialization();
		return location;
	}
	
	private void checkInitialization() {
		if ( !initialized )
		{
			throw new SecurityException( "Calculator is not properly initialized." ) ;
		} //end if
	} // end checkInitialization
}
