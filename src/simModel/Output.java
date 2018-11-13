package simModel;

class Output {
	SMSuperstore model;
	
	protected Output(SMSuperstore md) { model = md; }

    // SSOVs
	protected int[] numCustomers;
	protected int[] numLongWait;
	protected double[] propLongWait;
}
