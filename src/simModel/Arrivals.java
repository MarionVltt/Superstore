package simModel;
import simulationModelling.*;

/*
 * Implements the scheduled action Arrivals
 */
public class Arrivals extends ScheduledAction {

	SMSuperstore model;
	
	/*
	 * Constructor
	 */
	public Arrivals(SMSuperstore model) {this.model = model;}
	
	/*
	 * @return the next value of time at which a customer will arrive
	 */
	@Override
	protected double timeSequence() {
		return model.rvp.duCArr();
	}

	/*
	 * Create a new customer arriving in the store, and generate all needed attributes
	 */
	@Override
	protected void actionEvent() {
		int id = model.udp.ChooseQueue(); 
		Customer cust = new Customer(model);
		cust.nItems = model.rvp.nItems();
		cust.payMethod = model.rvp.payMethod(cust.nItems);
		cust.startWait = model.getClock();
		model.qCustLines[id].spInsertQue(cust); // add the customer to the chosen queue
		//System.out.println("one client arrived queue num: " + id + " queue length: "+ model.qCustLines[id].n );
	}

}
