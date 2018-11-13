package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;

class RVPs 
{
	SMSuperstore model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds


	// Constructor
	protected RVPs(SMSuperstore model, Seeds sd) 
	{ 
		this.model = model; 
		// Set up distribution functions
		
		for (int i=0; i<16;i++) {
			interArrCust[i] = new Exponential(1.0/MEAN_INTER_ARR[i],
				                       	new MersenneTwister(sd.seed1));
		}
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
	    // Note that interarrival time is added to current
	    // clock value to get the next arrival time.
	    return(nxtInterArr+model.getClock());
	}

}
