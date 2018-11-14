package simModel;

class UDPs 
{
	SMSuperstore model;  // for accessing the clock
	
	// Constructor
	protected UDPs(SMSuperstore model) { this.model = model; }

	// Translate User Defined Procedures into methods
    
	/*
	 * This UDP returns the id of the queue in which to put the newly arriving customer.
	 * It's essentially a min function with special conditions. 
	 */
	protected int ChooseQueue() {
		int mini = model.qCustLines[0].n+(model.rcCounters[0].customer==null?0:1);  //the ternary operator accounts for the customer in the counter. 
		int id = Constants.C1;
		for (int i=Constants.C1; i<=Constants.C20; i++) {
			if(model.rcCounters[i].uOpen && model.qCustLines[i].n + (model.rcCounters[i].customer==null?0:1) < mini){
				id = i;
				mini = model.qCustLines[i].n + (model.rcCounters[i].customer==null?0:1);
			}
		}
		return id;
	}
	
	/*
	 * This UDP return the id (if it exists) of a counter ready to begin scanning.
	 * The preconditions are defined in the detailed level CM
	 */
	protected int nextScanning() {
		for(int i=Constants.C1; i<=Constants.C20; i++) {
			if(model.rcCounters[i].state == Counter.counterStates.SCANNING_READY && model.qCustLines[i].n >0) {
				return i;
			}
		}
		return Constants.NONE;
	}
	
	/*
	 * This UDP return the id (if it exists) of a counter ready to begin payment when no supervisor is required.
	 * The preconditions are defined in the detailed level CM
	 */
	protected int nextPayment() {
		for(int i=Constants.C1; i<=Constants.C20; i++) {
			if(model.rcCounters[i].state == Counter.counterStates.PAYMENT_READY 
					&& model.rcCounters[i].customer.payMethod != Customer.payMethods.CHECK_NO_CARD) {
				return i;
			}
		}
		return Constants.NONE;
	}
	
	/*
	 * This UDP return the id (if it exists) of a counter ready to begin bagging if not already done by a bagger.
	 * The preconditions are defined in the detailed level CM
	 */
	protected int nextBagging() {
		for(int i=Constants.C1; i<=Constants.C20; i++) {
			if(model.rcCounters[i].state == Counter.counterStates.BAGGING_READY 
					&& model.rcCounters[i].baggerPresent == false) {
				return i;
			}
		}
		return Constants.NONE;
	}
}
