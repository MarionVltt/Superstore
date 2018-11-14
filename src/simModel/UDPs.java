package simModel;

class UDPs 
{
	SMSuperstore model;  // for accessing the clock
	
	// Constructor
	protected UDPs(SMSuperstore model) { this.model = model; }

	// Translate User Defined Procedures into methods
    /*-------------------------------------------------
	                       Example
	    protected int ClerkReadyToCheckOut()
        {
        	int num = 0;
        	Clerk checker;
        	while(num < model.NumClerks)
        	{
        		checker = model.Clerks[num];
        		if((checker.currentstatus == Clerk.status.READYCHECKOUT)  && checker.list.size() != 0)
        		{return num;}
        		num +=1;
        	}
        	return -1;
        }
	------------------------------------------------------------*/
	protected int ChooseQueue() {
		int mini = model.qCustLines[0].n+(model.rcCounters[0].customer==null?0:1);
		int id = Constants.C1;
		for (int i=Constants.C1; i<=Constants.C20; i++) {
			if(model.rcCounters[i].uOpen && model.qCustLines[i].n + (model.rcCounters[i].customer==null?0:1) < mini){
				id = i;
				mini = model.qCustLines[i].n + (model.rcCounters[i].customer==null?0:1);
			}
		}
		return id;
	}
	
	protected int nextScanning() {
		for(int i=Constants.C1; i<=Constants.C20; i++) {
			if(model.rcCounters[i].state == Counter.counterStates.SCANNING_READY && model.qCustLines[i].n >0) {
				return i;
			}
		}
		return Constants.NONE;
	}
	
	protected int nextPayment() {
		for(int i=Constants.C1; i<=Constants.C20; i++) {
			if(model.rcCounters[i].state == Counter.counterStates.PAYMENT_READY 
					&& model.rcCounters[i].customer.payMethod != Customer.payMethods.CHECK_NO_CARD) {
				return i;
			}
		}
		return Constants.NONE;
	}
	
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
