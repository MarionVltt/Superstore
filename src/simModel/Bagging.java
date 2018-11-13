package simModel;

import simulationModelling.ConditionalActivity;

public class Bagging extends ConditionalActivity{
	
	SMSuperstore model;  // for referencing the model
	int id;
	
	public Bagging(SMSuperstore model) { this.model = model; }
	
	protected static boolean precondition(SMSuperstore md){
		boolean returnValue = false;
	    if( (md.udp.nextBagging() != Constants.NONE)) returnValue = true;
		return(returnValue);
	}

	public void startingEvent() {
		this.id = model.udp.nextBagging();
		model.rcCounters[id].state=Counter.counterStates.BAGGING;
	}

	protected double duration() {
		return (model.rvp.uBaggingTime(model.rcCounters[id].customer.nItems));
	}

	protected void terminatingEvent() {
		model.rcCounters[id].state = Counter.counterStates.SCANNING_READY;

	}

}
