package simModel;

import simulationModelling.ConditionalActivity;

public class Payment extends ConditionalActivity{
	
	SMSuperstore model;  // for referencing the model
	int id;
	
	public Payment(SMSuperstore model) { this.model = model; }
	
	protected static boolean precondition(SMSuperstore md){
		boolean returnValue = false;
	    if( (md.udp.nextPayment	() != Constants.NONE)) returnValue = true;
		return(returnValue);
	}

	public void startingEvent() {
		this.id = model.udp.nextPayment();
		model.rcCounters[id].state=Counter.counterStates.PAYMENT;
	}

	protected double duration() {
		return (model.rvp.uPayTime(model.rcCounters[id].customer.payMethod));
	}

	protected void terminatingEvent() {
		if(model.rcCounters[id].baggerPresent) {
			model.rgBaggers.nAvail +=1;
			model.rcCounters[id].baggerPresent=false;
			model.rcCounters[id].state = Counter.counterStates.SCANNING_READY;
		} else {
			model.rcCounters[id].state = Counter.counterStates.BAGGING_READY;
		}
	}

}
