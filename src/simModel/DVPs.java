package simModel;

class DVPs 
{
	SMSuperstore model;  // for accessing the clock
	private int current_schedule;  //to keep track of the current schedule
	
	// Constructor
	protected DVPs(SMSuperstore model) { 
		this.model = model;
		current_schedule=-1;  // since the index is increased before it is used, start at -1;
	}

	
	private final double [] scheduleChange = {20, 30, 50, 60, 80, 90, 110, 120, 140, 150, 170, 180, 200,
											  210, 230, 240, 260, 270, 290, 300, 320, 330, 350, 360, 380, 
											  390, 410, 420, 440, 450};
	
	
	/*
	 * This DVP returns the time sequence for the ApplySchedule scheduled action.
	 */
	protected double nextSchedule()
	{
		current_schedule +=1; //move to next schedule index
		return scheduleChange[current_schedule];
		
		
	}
	
	protected void openCloseCounters() {
		int period = (int) (model.getClock() + 10)/30 ;  // the +10 here ensure the correct period is computed, in regard to the ApplySchedule time sequence.
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
