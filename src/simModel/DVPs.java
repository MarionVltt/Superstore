package simModel;

class DVPs 
{
	SMSuperstore model;  // for accessing the clock
	
	// Constructor
	protected DVPs(SMSuperstore model) { this.model = model; }

	// Translate deterministic value procedures into methods
        /* -------------------------------------------------
	                       Example
	protected double getEmpNum()  // for getting next value of EmpNum(t)
	{
	   double nextTime;
	   if(model.clock == 0.0) nextTime = 90.0;
	   else if(model.clock == 90.0) nextTime = 210.0;
	   else if(model.clock == 210.0) nextTime = 420.0;
	   else if(model.clock == 420.0) nextTime = 540.0;
	   else nextTime = -1.0;  // stop scheduling
	   return(nextTime);
	}
	------------------------------------------------------------*/
	
	private final double [] scheduleChange = {25, 55, 85, 115, 145, 175, 205, 235, 265, 295, 325, 355, 385, 415, 445};
	
	protected double nextSchedule()
	{
		return scheduleChange[(int)(model.getClock()) /30 + 1];  //this ensures that at t=25, the next time returned is 55, and so on
		
	}
	
	protected void openCloseCounters() {
		int period = (int) (model.getClock())/30 +1; //the 6 ensures the next period starting in 5 minutes is returned.
		int nCash = model.cashierSchedule[period];
		for (int id=Constants.C1; id<=Constants.C20; id++) {
			if (id<=nCash) {
				model.rcCounters[id].uOpen = true;
			} else {
				model.rcCounters[id].uOpen = false;
			}
		}
	}
}
