package simModel;
import simulationModelling.*;

public class ApplySchedule extends ScheduledAction{

SMSuperstore model;
	
	public ApplySchedule(SMSuperstore model) {this.model = model;}
	
	@Override
	protected double timeSequence() {
		return model.dvp.nextSchedule();
	}

	/*The computation of the period, done by adding 1 period is here to make sure that
	 * the index returned is the one of the next period (theoreticaly starting in 5 minutes) */
	
	@Override
	protected void actionEvent() {
		model.dvp.openCloseCounters();
		model.rgBaggers.nAvail += model.baggerSchedule[(int)(model.getClock())/30]-
				model.baggerSchedule[(int)(model.getClock())/30-1];
		
	}
	
}
