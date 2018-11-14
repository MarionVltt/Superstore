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
		Customer cust = new Customer(model);
		cust.nItems = model.rvp.nItems();
		cust.payMethod = model.rvp.payMethod(cust.nItems);
		cust.startWait = model.getClock();
		model.qCustLines[id].spInsertQue(cust);
	}

}
