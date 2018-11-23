package simModel;
import simulationModelling.*;

/*
 * Implements the scheduled action Arrivals,  the entity stream representing the arriving customers
 */
public class Arrivals extends ScheduledAction {

	static SMSuperstore model;
	
	/*
	 * @return the next time value at which a customer will arrive
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
		Customer cust = new Customer();
		cust.nItems = model.rvp.nItems();
		cust.payMethod = model.rvp.payMethod(cust.nItems);
		cust.startWait = model.getClock();
		model.qCustLines[id].spInsertQue(cust); // add the customer to the chosen queue
		//System.out.println("one client arrived queue num: " + id + " queue length: "+ model.qCustLines[id].n );
	}

}
