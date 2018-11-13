package simModel;

public class Customer {
	
	protected int nItems;
	protected enum payMethods{
		CASH,
		CREDIT_CARD,
		CHECK_WITH_CARD,
		CHECK_NO_CARD;
	};
	
	protected payMethods payMethod;
	protected double startWait;
}
