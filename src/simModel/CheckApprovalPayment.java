package simModel;

import simulationModelling.ConditionalActivity;

public class CheckApprovalPayment extends ConditionalActivity{
	SMSuperstore model;  // for referencing the model
	int id;
	
	public CheckApprovalPayment(SMSuperstore model) { this.model = model; }
	
	protected static boolean precondition(SMSuperstore md){
		boolean returnValue = false;
	    if( (!md.rSupervisor.isBusy && md.qApproveLine.n > 0)) returnValue = true;
		return(returnValue);
	}

	public void startingEvent() {
		this.id = model.qApproveLine.spRemoveQue();
		model.rcCounters[id].state=Counter.counterStates.PAYMENT;
		model.rSupervisor.isBusy=true;
	}

	protected double duration() {
		return (model.rvp.uApprovalTime());
	}

	protected void terminatingEvent() {
		model.rSupervisor.isBusy = false;
		if(model.rcCounters[id].baggerPresent) {
			model.rgBaggers.nAvail +=1;
			model.rcCounters[id].baggerPresent=false;
			model.rcCounters[id].state = Counter.counterStates.SCANNING_READY;
		} else {
			model.rcCounters[id].state = Counter.counterStates.BAGGING_READY;
		}
	}

}
