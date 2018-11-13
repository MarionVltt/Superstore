package simModel;
import simulationModelling.*;

public class Arrivals extends ScheduledAction {

	SMSuperstore model;
	
	public Arrivals(SMSuperstore model) {this.model = model;}
	@Override
	protected double timeSequence() {
		return model.rvp.duCArr();
	}

	@Override
	protected void actionEvent() {
		int id = model.udp.ChooseQueue();
		

	}

}
