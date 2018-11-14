package simModel;

import simulationModelling.ScheduledAction;

class Initialise extends ScheduledAction
{
	SMSuperstore model;
	
	// Constructor
	protected Initialise(SMSuperstore model) { this.model = model; }

	double [] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
	int tsix = 0;  // set index to first entry.
	protected double timeSequence() 
	{
		return ts[tsix++];  // only invoked at t=0
	}

	protected void actionEvent() {
		// System Initialisation
        for(int id=Constants.C1; id<=Constants.C20; id++) {
        	model.qCustLines[id] = new CustLine();
        	model.rcCounters[id] = new Counter();
        	model.qCustLines[id].n = 0; //all queues empty
        	model.rcCounters[id].state=Counter.counterStates.SCANNING_READY;
        	model.rcCounters[id].baggerPresent = false;
        }
        model.dvp.openCloseCounters();
        model.rgBaggers.nAvail=model.baggerSchedule[0];
        model.rSupervisor.isBusy = false;
        model.qApproveLine.n = 0;
	}
	

}
