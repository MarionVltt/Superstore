package simModel;

import simulationModelling.ConditionalActivity;

/*
 * Implements the conditional activity Bagging
 */
public class Bagging extends ConditionalActivity{
	
	static SMSuperstore model;  // for referencing the model
	int id; // counter considered in the activity
	
	
	/*
	 * @param the model
	 * @return true if the precondition, tested by the UDP.nextBagging, is true, false otherwise
	 */
	protected static boolean precondition(){
		boolean returnValue = false;
	    if( (model.udp.nextBagging() != Constants.NONE)) returnValue = true;
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
