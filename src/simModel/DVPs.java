package simModel;

class DVPs {
	
	SMSuperstore model;  // for accessing the clock
	
	// Constructor
	protected DVPs(SMSuperstore model) { this.model = model; }
	
	/*
	 * Times at which the schedules change, every 30 minutes
	 * The case t=0 is considered in the initialise action
	 */
	private final double [] scheduleChange = {30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360, 390, 420, 450};
	
	/*
	 * @return the next time value in the list
	 */
	protected double nextSchedule()
	{
		if(model.getClock()<450)
			return scheduleChange[(int)(model.getClock()) /30];
		else
			return model.stopTime+1; // this ensure the event is not scheduled after the end of time sequence
	}
	
	/*
	 * Set the first nCash counters to open and the others to closed, where nCash is the 
	 * number of cashiers in the schedule for each period
	 */
	protected void openCloseCounters() {
		int period = (int) (model.getClock())/30 ; 
		int nCash = model.cashierSchedule[period];
		for (int id=Constants.C1; id<=Constants.C20; id++) {
			if (id<nCash) {
				model.rcCounters[id].uOpen = true;
			} else {
				model.rcCounters[id].uOpen = false;
			}
		}
	}
}
