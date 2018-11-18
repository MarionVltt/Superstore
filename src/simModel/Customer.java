package simModel;

/*
 * Class, each instance represents a customer
 */
public class Customer {
	
	protected int nItems; // number of items
	protected enum payMethods{
		CASH,
		CREDIT_CARD,
		CHECK_WITH_CARD,
		CHECK_NO_CARD;
	};
	
	protected payMethods payMethod; 
	protected double startWait; // time at which the customer started to wait
	
}
