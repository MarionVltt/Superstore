package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.Normal;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;

/*
 * This class defines the Random Variate Procedures (RVPs) used in the model. The RVPs will be implemented 
 * as methods returning the next number in the pseudo-random sequence.
 */

class RVPs 
{
	static SMSuperstore model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds


	// Constructor
	protected RVPs(Seeds sd) 
	{ 
		// Set up distribution functions
		
		for (int i=0; i<16;i++) {
			interArrCust[i] = new Exponential(1.0/MEAN_INTER_ARR[i],
				                       	new MersenneTwister(sd.seedArrival));
		}
		NItemA = new Normal(MEAN_ITEM_A,SD_ITEM_A,new MersenneTwister(sd.seedItemA));
		NItemB = new Normal(MEAN_ITEM_B,SD_ITEM_B,new MersenneTwister(sd.seedItemB));
		ItemCat = new Uniform(0,1,new MersenneTwister(sd.seedItemCat));
		
		payMethodCat = new Uniform(0,1, new MersenneTwister(sd.seedPayMethodCat));
		checkWithCard = new Uniform(0,1, new MersenneTwister(sd.seedCheckWithCard));
		
		ScanTime = new Normal(MEAN_SCAN_TIME, SD_SCAN_TIME, new MersenneTwister(sd.seedScanTime));
		PriceCheckTime = new Normal(MEAN_PRICE_CHECK, SD_PRICE_CHECK, new MersenneTwister(sd.seedPriceCheckTime));
		priceCheck = new Uniform(0,1, new MersenneTwister(sd.seedPriceCheck));
		
		Cash = new Normal(MEAN_CASH, SD_CASH, new MersenneTwister(sd.seedCash));
		CreditCard = new Normal(MEAN_CREDIT_CARD, SD_CREDIT_CARD, new MersenneTwister(sd.seedCreditCard));
		Check = new Normal(MEAN_CHECK, SD_CHECK, new MersenneTwister(sd.seedCheck));
		
		ApprovalTime = new Normal(MEAN_APP_TIME, SD_APP_TIME, new MersenneTwister(sd.seedAppTime));
		
		BaggingTime = new Normal(MEAN_BAG_TIME, SD_BAG_TIME, new MersenneTwister(sd.seedBagTime));
	}
	
	/* Random Variate Procedure for Arrivals */
	private Exponential [] interArrCust = new Exponential[16] ;  // Exponential distribution for interarrival times
	private final double MEAN_INTER_ARR[]= {38.0/60.0, 36.0/60.0, 30.0/60.0, 24.0/60.0, 
	                                   22.0/60.0, 24.0/60.0, 22.0/60.0, 33.0/60.0, 
	                                   34.0/60.0, 38.0/60.0 ,29.0/60.0, 24.0/60.0, 
	                                   23.0/60.0, 38.0/60.0, 51.0/60.0, 1.0};
	
	protected double duCArr()  // for getting next value of duInput
	{
	    double nxtInterArr;

        nxtInterArr = interArrCust[(int)(model.getClock())/30].nextDouble();
        //System.out.println("Next customer in: "+nxtInterArr);
	    // Note that interarrival time is added to current
	    // clock value to get the next arrival time.
	    return(nxtInterArr+model.getClock());
	}
	
	/* Random Variate procedures for number of items */
	private final double MEAN_ITEM_A=27;
	private final double MEAN_ITEM_B=108;
	private final double SD_ITEM_A=8.33;
	private final double SD_ITEM_B=19;
	private final double PROB_CAT_A=0.233;
	private Normal NItemA;
	private Normal NItemB;
	private Uniform ItemCat;
	
	protected int nItems() 
	{
		if(ItemCat.nextDouble()<=PROB_CAT_A)
			return (int)NItemA.nextDouble();
		else
			return (int)NItemB.nextDouble();
	
	}
	
	/*
	 * Random variate procedure for payment method
	 */
	private final double PROB_CASH_INF20=0.45;
	private final double PROB_CASH_SUP20=0.20;
	private final double PROB_CREDIT_CARD_INF20=0.25;
	private final double PROB_CREDIT_CARD_SUP20=0.35;
	private final double PROB_CHECK_WITH_CARD=0.73;
	private Uniform payMethodCat;
	private Uniform checkWithCard;
	
	
	protected Customer.payMethods payMethod(int nItems) {
		double rand = payMethodCat.nextDouble();
		if(nItems <= 20) {
			if(rand<=PROB_CASH_INF20) {
				return Customer.payMethods.CASH;
			} else if (rand <= PROB_CASH_INF20+PROB_CREDIT_CARD_INF20) {
				return Customer.payMethods.CREDIT_CARD;
			} else {
				double rand2 = checkWithCard.nextDouble();
				if (rand2 <= PROB_CHECK_WITH_CARD) {
					return Customer.payMethods.CHECK_WITH_CARD;
				} else {
					return Customer.payMethods.CHECK_NO_CARD;
				}
			}
		} else {
			if(rand<=PROB_CASH_SUP20) {
				return Customer.payMethods.CASH;
			} else if (rand <= PROB_CASH_SUP20+PROB_CREDIT_CARD_SUP20) {
				return Customer.payMethods.CREDIT_CARD;
			} else {
				double rand2 = checkWithCard.nextDouble();
				if (rand2 <= PROB_CHECK_WITH_CARD) {
					return Customer.payMethods.CHECK_WITH_CARD;
				} else {
					return Customer.payMethods.CHECK_NO_CARD;
				}
			}
		}
	}
	
	/*
	 * Random variate procedure for scanning time
	 */
	
	private final double MEAN_SCAN_TIME = 3/60;
	private final double SD_SCAN_TIME = 0.75/60;
	private final double MEAN_PRICE_CHECK = 2.2;
	private final double SD_PRICE_CHECK = 1;
	private final double PROB_PRICE_CHECK=0.13;
	private Uniform priceCheck;
	private Normal ScanTime;
	private Normal PriceCheckTime;
	
	protected double uScanTime(int nItems) {
		double scanTime =  0.0;
		for (int i=0; i<nItems; i++) {
			scanTime += Math.max(0.0, ScanTime.nextDouble()); //we had issues where the normal distribution returned negative times for scanning.
		}
		if(priceCheck.nextDouble()<=PROB_PRICE_CHECK) 
			return scanTime+PriceCheckTime.nextDouble();
		else
			return scanTime;
	}
	
	/*
	 * Random variate procedure for payment item
	 */
	
	private final double MEAN_CASH = 0.95;
	private final double SD_CASH = 0.17;
	private final double MEAN_CREDIT_CARD = 1.24;
	private final double SD_CREDIT_CARD = 0.21;
	private final double MEAN_CHECK = 1.45;
	private final double SD_CHECK = 0.35;
	private Normal Cash;
	private Normal CreditCard;
	private Normal Check;
	
	
	protected double uPayTime(Customer.payMethods payMethod) {
		double payTime = Constants.NONE;
		switch (payMethod){
			case CASH:
				payTime = Cash.nextDouble();
				break;
			case CREDIT_CARD:
				payTime = CreditCard.nextDouble();
				break;
			case CHECK_WITH_CARD:
				payTime = Check.nextDouble();
				break;
			case CHECK_NO_CARD:
				payTime = Check.nextDouble();
				break;			
		}
		return payTime;
	}
	
	/*
	 * random variate procedure for the approval time.
	 */
	
	private final double MEAN_APP_TIME = 0.95;
	private final double SD_APP_TIME = 0.15;
	private Normal ApprovalTime;
	
	protected double uApprovalTime() {
		return ApprovalTime.nextDouble()+uPayTime(Customer.payMethods.CHECK_NO_CARD);
	}
	
	/*
	 * random variate procedure for the bagging time
	 */
	
	private final double MEAN_BAG_TIME = 1.25/60;
	private final double SD_BAG_TIME = 0.75/60;
	private Normal BaggingTime;
	
	protected double uBaggingTime(int nItems) {
		double bagTime=0;
		for(int i = 0; i<nItems; i++)
		{
			bagTime+=Math.max(0.0, BaggingTime.nextDouble());
		}
		return bagTime;
	}
	
}
