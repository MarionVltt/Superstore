package simModel;

import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;
import simulationModelling.SequelActivity;

//
// The Simulation model Class
public class SMSuperstore extends AOSimulationModel
{
	// Constants available from Constants class
	/* Parameter */
        protected int[] cashierSchedule;
        protected int[] baggerSchedule;
        

	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	// Define the reference variables to the various 
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialise Action
	protected CustLine [] qCustLines = new CustLine[20];
	protected Counter [] rcCounters = new Counter[20];
	protected Baggers rgBaggers = new Baggers();
	protected Supervisor rSupervisor = new Supervisor();
	protected ApproveLine qApproveLine = new ApproveLine();
	

	/* Input Variables */
	// Define any Independent Input Variables here
	
	
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
	public SMSuperstore(double t0time, double tftime,int [] cashierSchedule, int[] baggerSchedule , Seeds sd)
	{
		// Initialise parameters here
		this.cashierSchedule = cashierSchedule;
		this.baggerSchedule = baggerSchedule;
		// Create RVP object with given seed
		rvp = new RVPs(this,sd);
		
		// rgCounter and qCustLine objects created in Initalise Action
		
		// Initialise the simulation model
		initAOSimulModel(t0time,tftime);   

		     // Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		scheduleAction(init);  // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here
	}

	/************  Implementation of Data Modules***********/	
	/*
	 * Testing preconditions
	 */
	protected void testPreconditions(Behaviour behObj)
	{
		reschedule (behObj);
		// Check preconditions of Conditional Activities

		// Check preconditions of Interruptions in Extended Activities
	}
	
	public void eventOccured()
	{
		//this.showSBL();
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging

		// Setup an updateTrjSequences() method in the Output class
		// and call here if you have Trajectory Sets
		// updateTrjSequences() 
	}

	// Standard Procedure to start Sequel Activities with no parameters
	protected void spStart(SequelActivity seqAct)
	{
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}	

}


