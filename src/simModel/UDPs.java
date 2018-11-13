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
		int id = Constants.NONE;
		int mini = model.qCustLines[0].n;
		for (int i = 0; i<20; i++) {
			if(model.rcCounters[i].uOpen && model.qCustLines[i].n<mini){
				id = i;				
			}
		}
		return id;
	}
	
	protected int nextScanning() {
		for(int i =0; i<20; i++) {
			if(!model.rcCounters[i].isBusy && model.qCustLines[i].n >0) {
				return i;
			}
		}
		return Constants.NONE;
	}
	
	protected int nextPayment() {
		for(int i = 0; i<20; i++) {
			if(model.rcCounters[i].state == Counter.counterStates.PAYMENT_READY 
					&& model.rcCounters[i].customer.payMethod != Customer.payMethods.CHECK_NO_CARD) {
				return i;
			}
		}
		return Constants.NONE;
	}
	
	protected int nextBagging() {
		for(int i = 0; i<20; i++) {
			if(model.rcCounters[i].state == Counter.counterStates.BAGGING_READY 
					&& model.rcCounters[i].baggerPresent == false) {
				return i;
			}
		}
		return Constants.NONE;
	}
}
