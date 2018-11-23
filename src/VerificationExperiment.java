// File: Experiment.java
// Description:

import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class VerificationExperiment
{
	 
	/*
	 * transform a list and String to be able to print it with the common methods like println
	 */
	public static String toString(double[] list) {
		String result = "[";
		for(int i=0; i<list.length-1; i++) {
			result += String.format("%.2f", list[i]) +", ";	
		}
		result += String.format("%.2f", list[list.length-1]) +"]";
		return result;
		
	}
	public static void main(String[] args){
       int i, NUMRUNS = 2; 
       double startTime=0.0, endTime=480;
       Seeds[] sds = new Seeds[NUMRUNS];
       SMSuperstore mname;  // Simulation object
       
       int [][] cashierSchedule = {{20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20}, //schedules for all the test cases
    		   						{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
    		   						{6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
    		   						{7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
    		   						{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5}};
       int [][] baggerSchedule = {{20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
									{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
									{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
									{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
    		   						{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}};
       
       int full_staff = 0;
       int no_staff = 1;
       int base_case = 2;
       int increased_case = 3;
       int decreased_case = 4; 

       // Lets get a set of uncorrelated seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       for(i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);
       
       System.out.println("Proportion of clients waiting more than 15 min, per period:\n");
       
       System.out.println("Static tests:");
       
       //see detailed CM for an explanation of the desired results.
       
       mname = new SMSuperstore(startTime,endTime,cashierSchedule[full_staff],baggerSchedule[full_staff],sds[0], false);
       mname.runSimulation();   
       System.out.println("Full staff: "+toString(mname.getPropLongWait())+"\n");
       
       mname = new SMSuperstore(startTime,endTime,cashierSchedule[no_staff],baggerSchedule[no_staff],sds[0], false);
       mname.runSimulation();   
       System.out.println("No staff: "+toString(mname.getPropLongWait())+"\n");
       
       
       System.out.println("Dynamic tests:");
       
       mname = new SMSuperstore(startTime,endTime,cashierSchedule[base_case],baggerSchedule[base_case],sds[0], false);
       mname.runSimulation();   
       System.out.println("Base Case: "+toString(mname.getPropLongWait())+"\n");
       
       mname = new SMSuperstore(startTime,endTime,cashierSchedule[increased_case],baggerSchedule[base_case],sds[1], false);
       mname.runSimulation();   
       System.out.println("Cashier increased Case: "+toString(mname.getPropLongWait())+"\n");
       
       mname = new SMSuperstore(startTime,endTime,cashierSchedule[decreased_case],baggerSchedule[base_case],sds[1], false);
       mname.runSimulation();   
       System.out.println("Cashier decreased Case: "+toString(mname.getPropLongWait())+"\n");
       
       mname = new SMSuperstore(startTime,endTime,cashierSchedule[base_case],baggerSchedule[increased_case],sds[1], false);
       mname.runSimulation();   
       System.out.println("Bagger increased Case: "+toString(mname.getPropLongWait())+"\n");
       
       mname = new SMSuperstore(startTime,endTime,cashierSchedule[base_case],baggerSchedule[decreased_case],sds[1], false);
       mname.runSimulation();   
       System.out.println("Bagger decreased Case: "+toString(mname.getPropLongWait())+"\n");
   }
}
