package simModel;

import simulationModelling.ConditionalActivity;

/*
 * Implements the conditional activity Bagging
 */
public class Bagging extends ConditionalActivity{
	
	SMSuperstore model;  // for referencing the model
	int id; // counter considered in the activity
	
	/*
	 * Constructor
	 */
	public Bagging(SMSuperstore model) { this.model = model; }
	
	/*
	 * @param the model
	 * @return true if the precondition, tested by the UDP.nextBagging, is true, false otherwise
	 */
	protected static boolean precondition(SMSuperstore md){
		boolean returnValue = false;
	    if( (md.udp.nextBagging() != Constants.NONE)) returnValue = true;
		return(returnValue);
	}

	/*
	 * Starting event SCS
	 */
	public void startingEvent() {
		this.id = model.udp.nextBagging();
		model.rcCounters[id].state=Counter.counterStates.BAGGING;
	}

	/*
	 * Duration determined by the RVP.uBaggingTime and dependent on the number of items
	 */
	protected double duration() {
		return (model.rvp.uBaggingTime(model.rcCounters[id].customer.nItems));
	}

	/*
	 * Terminating event SCS
	 */
	protected void terminatingEvent() {
		model.rcCounters[id].state = Counter.counterStates.SCANNING_READY;
		model.rcCounters[id].customer=null;

	}

}
