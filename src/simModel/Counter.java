package simModel;

public class Counter {
	
	protected boolean uOpen;
	protected boolean isBusy;
	protected Customer customer;
	protected boolean baggerPresent;
	
	protected enum counterStates {
		SCANNING_READY,
		SCANNING,
		PAYMENT_READY,
		PAYMENT,
		BAGGING_READY,
		BAGGING;
	}; //enumeration
	
	protected counterStates state;

}
