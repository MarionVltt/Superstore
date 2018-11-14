package simModel;
import simulationModelling.*;

/*
 * Implements the scheduled action ApplySchedule
 */
public class ApplySchedule extends ScheduledAction{

	SMSuperstore model;
	
	/*
	 * Constructor
	 * @param model
	 */
	public ApplySchedule(SMSuperstore model) {this.model = model;}
	
	/*
	 * @return The next value of time at which the schedules will be updated
	 */
	@Override
	protected double timeSequence() {
		return model.dvp.nextSchedule();
	}
	
	/*
	 * Updates the schedules by using the DVP.openCloseCounters() to set the right number of open
	 * counters, and the formula below for the baggers
	 */
	@Override
	protected void actionEvent() {
		model.dvp.openCloseCounters();
		// to explain
		model.rgBaggers.nAvail += model.baggerSchedule[(int)(model.getClock())/30]-
				model.baggerSchedule[(int)(model.getClock())/30-1];
		
	}
	
}
