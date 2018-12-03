
import simModel.*;
import cern.jet.random.engine.*;
import outputAnalysis.ConfidenceInterval;
import java.util.Arrays;

public class Experimentation {
	
	static final int NUMRUNS = 1000;
	static final double confidence = 0.90;
	static final double CEIL = 0.15;
	static final double startTime=0.0, endTime=480;
	static RandomSeedGenerator rsg = new RandomSeedGenerator();
	
		static public boolean waitOK(double [] propLongWait, double ceil) {
			for(int i = 0; i<16; i++) {
				if(propLongWait[i]>ceil)
					return false;
			}
			return true;
		}
	
		static public ConfidenceInterval[] runSchedule(int [] cashierSchedule, int []baggerSchedule, boolean verbose) {
		SMSuperstore store;
		ConfidenceInterval [] intervals = new ConfidenceInterval[16];
		double [][] values = new double[16][NUMRUNS];
		
		for(int i = 0; i<NUMRUNS; i++) {
			store = new SMSuperstore(startTime, endTime,cashierSchedule,baggerSchedule,new Seeds(rsg),false);
			store.runSimulation();
			double [] results = store.getPropLongWait();
			for(int j = 0; j<16; j++) {
				values[j][i] = results[j];
			}
		}
		
		for(int j =0; j< 16; j++) {
			intervals[j]=new ConfidenceInterval(values[j],confidence);
		}
		if(verbose) {
			System.out.printf("-------------------------------------------------------------------------------------\n");
			System.out.printf("Comparison    Point estimate(ybar(n))  s(n)     zeta   CI Min   CI Max |zeta/ybar(n)|\n");
			System.out.printf("-------------------------------------------------------------------------------------\n");
			for(int j=0; j<16; j++) {
				System.out.printf("Period: %2d %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n",j,
						intervals[j].getPointEstimate(), intervals[j].getVariance(), intervals[j].getZeta(), 
						intervals[j].getCfMin(), intervals[j].getCfMax(),
						Math.abs(intervals[j].getZeta()/intervals[j].getPointEstimate()));
			}
		}
		
		return intervals;
	}
	
	public static void main(String[] args) {
		int [] cashierSchedule= {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		int [] baggerSchedule= {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		double[] propLongWait = new double[16];
		double [] delta = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int start, duration;
		int run=0;
		
		ConfidenceInterval [] intervals = new ConfidenceInterval[16]; 
		System.out.println("Initiating Cashier schedule optimization...");
		intervals=runSchedule(cashierSchedule, baggerSchedule,true);
		for(int i=0; i<16; i++) {
			propLongWait[i]=intervals[i].getCfMax();
		}
		while(!waitOK(propLongWait,CEIL)) {
			run +=1;
			System.out.printf("\nRun: %d \n", run);
			System.out.printf("Current long wait proportion: %s\n", Arrays.toString(propLongWait));
			start = -1;
			do{
				start+= 1;
			}while(start < 10 && propLongWait[start]<CEIL) ;
			if(start>0) {
				start -=1;
			}
			duration=5;
			do {
				duration +=1;
			}while(duration < 10 && start+duration < 16 && propLongWait[start+duration-1]>=CEIL);
			for(int i=0; i<16; i++) {
				delta[i]=0;
			}
			for(int i=0; i<duration; i++) {
				delta[start+i]+=1;
			}
			System.out.printf("Proposed modification: %s \n", Arrays.toString(delta));
			for(int i=0; i<16; i++) {
				cashierSchedule[i]+=delta[i];
			}
			System.out.printf("New Cashier schedule: %s \n", Arrays.toString(cashierSchedule));
			intervals = runSchedule(cashierSchedule,baggerSchedule,false);
			for(int i=0; i<16; i++) {
				propLongWait[i]=intervals[i].getCfMax();
			}
			
		}
		System.out.printf("\n\n\nOptimal Cashier Schedule: %s\n", Arrays.toString(cashierSchedule));
		System.out.printf("Proportion of long wait times: %s\n", Arrays.toString(propLongWait));
		System.out.println("Cashier schedule optimization done.\nBagger schedule optimization begining...");
		for(int i=0; i<16; i++) {
			baggerSchedule[i]=0;
		}
		
		run +=1;
		intervals=runSchedule(cashierSchedule, baggerSchedule,false);
		for(int i=0; i<16; i++) {
			propLongWait[i]=intervals[i].getCfMax();
		}
		while(!waitOK(propLongWait,CEIL)) {
			run +=1;
			System.out.printf("\nRun: %d \n", run);
			System.out.printf("Current long wait proportion: %s\n", Arrays.toString(propLongWait));
			start = -1;
			do{
				start+= 1;
			}while(start < 10 && propLongWait[start]<CEIL) ;
			if(start>0) {
				start -=1;
			}
			duration=5;
			do {
				duration +=1;
			}while(duration < 10 && start+duration < 16 && propLongWait[start+duration-1]>=CEIL);
			for(int i=0; i<16; i++) {
				delta[i]=0;
			}
			for(int i=0; i<duration; i++) {
				delta[start+i]+=1;
			}
			System.out.printf("Proposed modification: %s \n", Arrays.toString(delta));
			for(int i=0; i<16; i++) {
				baggerSchedule[i]+=delta[i];
			}
			System.out.printf("New bagger schedule: %s \n", Arrays.toString(baggerSchedule));
			intervals = runSchedule(cashierSchedule,baggerSchedule,false);
			for(int i=0; i<16; i++) {
				propLongWait[i]=intervals[i].getCfMax();
			}
			
		}
		System.out.printf("\n\n\nOptimal bagger Schedule: %s\n", Arrays.toString(baggerSchedule));
		System.out.printf("Proportion of long wait times: %s\n", Arrays.toString(propLongWait));
		
		
		
		
	}

}
