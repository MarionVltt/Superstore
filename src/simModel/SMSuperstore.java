package simModel;

import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;

// The Simulation model Class
public class SMSuperstore extends AOSimulationModel
{
	// Constants available from Constants class
	
	/* Parameters */
    protected int[] cashierSchedule;
    protected int[] baggerSchedule;
        

	/*-------------Entity Data Structures-------------------*/
    
	protected CustLine [] qCustLines = new CustLine[20];
	protected Counter [] rcCounters = new Counter[20];
	protected Baggers rgBaggers = new Baggers();
	protected Supervisor rSupervisor = new Supervisor();
	protected ApproveLine qApproveLine = new ApproveLine();
		
	
	// References to RVP and DVP objects
	protected RVPs rvp;  // Reference to rvp object - object created in constructor
	protected DVPs dvp = new DVPs(this);  // Reference to dvp object
	protected UDPs udp = new UDPs(this);

	// Output object
	protected Output output = new Output(this);
	
	// Output values - define the public methods that return values
	// required for experimentation.
	// SSOVs
	public int[] getNumCostumers () {return output.numCustomers;};  
	public int[] getNumLongWait () {return output.numLongWait;};
	public double[] getPropLongWait () {return output.propLongWait;};


	// Constructor
	public SMSuperstore(double t0time, double tftime,int [] cashierSchedule, int[] baggerSchedule , Seeds sd, boolean logFlag)
	{
		
		// Turn trancing on if traceFlag is true
		this.logFlag = logFlag;
				
		// Initialise parameters here
		this.cashierSchedule = cashierSchedule;
		this.baggerSchedule = baggerSchedule;
		// Create RVP object with given seed
		rvp = new RVPs(this,sd);
		
		// rgCounter and qCustLine objects created in Initialise Action
		
		// Initialise the simulation model
		initAOSimulModel(t0time,tftime);  
		stopTime = tftime;

		// Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		scheduleAction(init);  // Should always be first one scheduled.
		// Schedule other scheduled actions and activities here
		Arrivals arr = new Arrivals(this);
		scheduleAction(arr);
		ApplySchedule applySched = new ApplySchedule(this);
		scheduleAction(applySched);
		
	}

	/************  Implementation of Data Modules***********/	
	/*
	 * Testing preconditions
	 */
	protected void testPreconditions(Behaviour behObj)
	{
		reschedule (behObj);
		while (scanPreconditions() == true) /* repeat */;
	}
	
	// Single scan of all preconditions
	// Returns true if at least one precondition was true.
	private boolean scanPreconditions()
	{
		boolean statusChanged = false;

		// Conditional Activities
		if (Scanning.precondition(this) == true)
		{
			Scanning act = new Scanning(this); // Generate instance
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		
		if (Payment.precondition(this) == true)
		{
			Payment act = new Payment(this); // Generate instance																// instance
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		
		if (CheckApprovalPayment.precondition(this) == true)
		{
			CheckApprovalPayment act = new CheckApprovalPayment(this); // Generate instance																// instance
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		if (Bagging.precondition(this) == true)
		{
			Bagging act = new Bagging(this); // Generate instance																// instance
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		return (statusChanged);
	}
	
	protected double stopTime; // end of observation interval

	public boolean implicitStopCondition() // termination explicit
	{
		boolean retVal = false;
		if (getClock() >= stopTime)
			retVal = true;
		return (retVal);
	}
	
	public void eventOccured()
	{			
		if(logFlag) printDebug();
	}
	
	// for Debugging
	boolean logFlag = true;
	protected void printDebug()
	{
		System.out.println("Clock = " + getClock());
		for(int id=Constants.C1; id<=Constants.C20; id++) {
			if(rcCounters[id].uOpen || rcCounters[id].customer!=null) {
				System.out.print("id: " + id + "; n queue: " + qCustLines[id].n + "; Line open: " + (rcCounters[id].uOpen?"True":"False") + "; state: " + rcCounters[id].state + "; Bagger: " + rcCounters[id].baggerPresent);
				if (rcCounters[id].customer != null)
				{	
					System.out.print("; Customer: True; paymethod: "+ rcCounters[id].customer.payMethod);
				}
				else
				{
					System.out.print("; Customer: False");
				}
				System.out.print("\n");
			}
		}
		System.out.println("Supervisor busy: "+rSupervisor.isBusy+"; QApproveLength: "+qApproveLine.n);
		System.out.println("n bagger avail: " + rgBaggers.nAvail);
		System.out.println("cash sched: " + cashierSchedule[(int) getClock()/30]);
		System.out.println("bag sched: " + baggerSchedule[(int) getClock()/30]);
		showSBL();
		System.out.println(">-----------------------------------------------------------------<");		
	}

}


