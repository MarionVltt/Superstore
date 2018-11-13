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
        for(int id=0; id<20; id++) {
        	model.qCustLines[id].setN(0); //all queues empty
        	model.rcCounters[id].setBusy(false);
        	model.rcCounters[id].setBaggerPresent(false);
        }
        model.rgBaggers.group.clear(); // empties the group
        model.rSupervisor.setBusy(false);
        model.qApproveLine.setN(0);
	}
	

}
