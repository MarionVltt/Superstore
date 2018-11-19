package simModel;

import simulationModelling.ConditionalActivity;

/*
 * Implements the conditional activity Payment, used when the payment method is NOT
 * check without card
 */
public class Payment extends ConditionalActivity{
	
	static SMSuperstore model;  // for referencing the model
	int id; // counter considered here
	
	/*
	 * @param the model
	 * @return true if the precondition, tested by the UDP.nextPayment, is true, false otherwise
	 */
	protected static boolean precondition(SMSuperstore md){
		boolean returnValue = false;
	    if( (md.udp.nextPayment	() != Constants.NONE)) returnValue = true;
		return(returnValue);
	}

	/*
	 * Starting event SCS
	 */
	public void startingEvent() {
		this.id = model.udp.nextPayment();
		model.rcCounters[id].state=Counter.counterStates.PAYMENT;
	}

	/*
	 * Duration determined by the RVP.uPayTime and dependent on the payment method
	 */
	protected double duration() {
		return (model.rvp.uPayTime(model.rcCounters[id].customer.payMethod));
	}

	/*
	 * Terminating event SCS
	 */
	protected void terminatingEvent() {
		if(model.rcCounters[id].baggerPresent) {
			model.rgBaggers.nAvail +=1;
			model.rcCounters[id].baggerPresent=false;
			model.rcCounters[id].customer=null;
			model.rcCounters[id].state = Counter.counterStates.SCANNING_READY;
		} else {
			model.rcCounters[id].state = Counter.counterStates.BAGGING_READY;
		}
	}

}
