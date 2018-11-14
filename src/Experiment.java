// File: Experiment.java
// Description:

import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class Experiment
{
   public static void main(String[] args)
   {
       int i, NUMRUNS = 1; 
       double startTime=0.0, endTime=60.0;
       Seeds[] sds = new Seeds[NUMRUNS];
       SMSuperstore mname;  // Simulation object
       int [] cashierSchedule = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
       int [] baggerSchedule = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
       // Lets get a set of uncorrelated seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       for(i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);
       
       // Loop for NUMRUN simulation runs for each case
;
       for(i=0 ; i < NUMRUNS ; i++)
       {
          mname = new SMSuperstore(startTime,endTime,cashierSchedule,baggerSchedule,sds[i], true);
          mname.runSimulation();
          // See examples for hints on collecting output
          // and developping code for analysis
          //System.out.println(mname.getPropLongWait());
       }
   }
}
