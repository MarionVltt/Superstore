package simModel;

import simulationModelling.ConditionalActivity;

public class Scanning extends ConditionalActivity {
	
	SMSuperstore model;  // for referencing the model
	int id;
	
	public Scanning(SMSuperstore model) { this.model = model; }
	
	protected static boolean precondition(SMSuperstore md){
		boolean returnValue = false;
	    if( (md.udp.nextScanning() != Constants.NONE)) returnValue = true;
		return(returnValue);
	}

	public void startingEvent() {
		Output output = model.output;
		this.id = model.udp.nextScanning();
		model.rcCounters[id].customer = model.qCustLines[id].spRemoveQue();
		model.rcCounters[id].state = Counter.counterStates.SCANNING;
		if(model.getClock()-model.rcCounters[id].customer.startWait > 15) {
			output.numLongWait[(int) model.getClock()/30] +=1;
		}
		if (model.rgBaggers.nAvail > 0) {
			model.rgBaggers.nAvail -=1;
			model.rcCounters[id].baggerPresent = true;
		}
	}

	protected double duration() {
		return (model.rvp.uScanTime(model.rcCounters[id].customer.nItems));
	}

	protected void terminatingEvent() {
		if(model.rcCounters[id].customer.payMethod == Customer.payMethods.CHECK_NO_CARD) {
			model.qApproveLine.spInsertQue(id);
		}
		model.rcCounters[id].state = Counter.counterStates.PAYMENT_READY;
	}

}
