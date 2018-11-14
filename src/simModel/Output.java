package simModel;

class Output {
	SMSuperstore model;
	
	protected Output(SMSuperstore md) { 
		model = md; 
	}

    // SSOVs
	protected int[] numCustomers = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	protected int[] numLongWait = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	protected double[] propLongWait = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

}
