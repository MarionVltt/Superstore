package simModel;

import simulationModelling.ConditionalActivity;

/*
 * This class represents the conditional activity scanning.
 */

public class Scanning extends ConditionalActivity {
	
	static SMSuperstore model;  // for referencing the model
	int id; // this activity is parameterized since it's the same for all the counters
	
	/*
	 * @param the model
	 * @return true if the precondition of this activity, defined by UDP.nextScanning(), is true, false otherwise.
	 */
	protected static boolean precondition(SMSuperstore md){
		boolean returnValue = false;
	    if( (md.udp.nextScanning() != Constants.NONE)) returnValue = true;
		return(returnValue);
	}

	/*
	 * Starting event SCS
	 */
	public void startingEvent() {
		Output output = model.output;
		this.id = model.udp.nextScanning();
		model.rcCounters[id].customer = model.qCustLines[id].spRemoveQue();
		model.rcCounters[id].state = Counter.counterStates.SCANNING;
		if(model.getClock()-model.rcCounters[id].customer.startWait > 15) {
			output.numLongWait[(int) model.getClock()/30] +=1;
		}
		output.numCustomers[(int) model.getClock()/30]+=1;
		output.propLongWait[(int) model.getClock()/30] = (double) (output.numLongWait[(int) model.getClock()/30])/ (double) (output.numCustomers[(int) model.getClock()/30]);
		if (model.rgBaggers.nAvail > 0) {
			model.rgBaggers.nAvail -=1;
			model.rcCounters[id].baggerPresent = true;
		}
	}

	/*
	 * Duration of the activity defined by the number of items to scan, and the need or not of a price check.
	 */
	protected double duration() {
		double t=model.rvp.uScanTime(model.rcCounters[id].customer.nItems);
		System.out.println("duration scanning : " + t);
		return (t);
	}

	/*
	 * Terminating event SCS
	 */
	protected void terminatingEvent() {
		if(model.rcCounters[id].customer.payMethod == Customer.payMethods.CHECK_NO_CARD) {
			model.qApproveLine.spInsertQue(id);
		}
		model.rcCounters[id].state = Counter.counterStates.PAYMENT_READY;
	}

}
