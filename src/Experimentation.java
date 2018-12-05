

import simModel.*;
import cern.jet.random.engine.*;
import outputAnalysis.ConfidenceInterval;
import java.util.Arrays;
import java.util.ArrayList;

public class Experimentation {

	static final int NUMRUNS = 1000;
	static final double confidence = 0.90;
	static final double CEIL = 0.10;
	static final double startTime=0.0, endTime=480;
	static RandomSeedGenerator rsg = new RandomSeedGenerator();

	static public boolean waitOK(double [] propLongWait, double ceil) {
		for(int i = 0; i<16; i++) {
			if(propLongWait[i]>ceil)
				return false;
		}
		return true;
	}

	static public ConfidenceInterval[] runSchedule(int [] cashierSchedule, int []baggerSchedule,Seeds[] seeds, boolean verbose) {
		SMSuperstore store;
		ConfidenceInterval [] intervals = new ConfidenceInterval[16];
		double [][] values = new double[16][NUMRUNS];

		for(int i = 0; i<NUMRUNS; i++) {
			store = new SMSuperstore(startTime, endTime,cashierSchedule,baggerSchedule,seeds[i],false);
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
		boolean backtrackFail=false;
		Seeds [] seeds = new Seeds[NUMRUNS];
		int randomSeed=(int) System.currentTimeMillis()%10;
		for(int i=0; i<randomSeed; i++) {
			for(int j=0; j<NUMRUNS;j++) {
				rsg.nextSeed();
			}
		}
		for(int i=0; i<NUMRUNS;i++) {
			seeds[i]=new Seeds(rsg);
		}
		ConfidenceInterval [] intervals = new ConfidenceInterval[16]; 
		System.out.println("Initiating Cashier schedule optimization...");
		intervals=runSchedule(cashierSchedule, baggerSchedule, seeds, true);
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
			intervals = runSchedule(cashierSchedule,baggerSchedule, seeds, false);
			for(int i=0; i<16; i++) {
				propLongWait[i]=intervals[i].getCfMax();
			}

		}
		System.out.printf("\n\n\nOptimal Cashier Schedule: %s\n", Arrays.toString(cashierSchedule));
		System.out.printf("Proportion of long wait times: %s\n", Arrays.toString(propLongWait));
		System.out.println("Cashier schedule optimization done.\nBagger schedule optimization begining...");
		for(int i=0; i<16; i++) {
			baggerSchedule[i]=1 ;
		}

		run +=1;
		intervals=runSchedule(cashierSchedule, baggerSchedule, seeds, false);
		ArrayList<Integer> backtrackingStart = new ArrayList<Integer>();
		ArrayList<Integer> backtrackingDuration = new ArrayList<Integer>();
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
				start -= 1;
			}
			duration=5;
			do {
				duration +=1;
			}while(duration < 10 && start+duration < 16 && propLongWait[start+duration-1]>=CEIL);

			backtrackingStart.add(start);
			backtrackingDuration.add(duration);

			for(int i=0; i<16; i++) {
				delta[i]=0;
			}
			for(int i=0; i<duration; i++) {
				delta[start+i]+=1;
			}

			for(int i=0; i<16; i++) {
				baggerSchedule[i]+=delta[i];
			}

			int problemStart=0;
			do {
				problemStart++;
			}while(problemStart<16 && baggerSchedule[problemStart]<=cashierSchedule[problemStart]);

			System.out.printf("Proposed bagger schedule: %s \n", Arrays.toString(baggerSchedule));
			if (! backtrackFail && problemStart<16) {
				System.out.printf("original  problmeStart: %d\n", problemStart);
				while(problemStart>0 && cashierSchedule[problemStart-1]==baggerSchedule[problemStart-1]) {
					problemStart--;
				}
				if(problemStart==0) {
					backtrackFail=true;
					System.out.println("backtrack failed, defaulting to cashier schedule");
					for(int i=0; i<16; i++) {
						baggerSchedule[i]=cashierSchedule[i];
					}
				}
				else {
					System.out.printf("updated problmeStart: %d\n", problemStart);
					System.out.println("backtracking start" + Arrays.toString(backtrackingStart.toArray()));
					System.out.println("backtracking duration" + Arrays.toString(backtrackingDuration.toArray()));
					int beginning = 0;
					for(int i=0; i<backtrackingStart.size(); i++) {
						if(backtrackingStart.get(i)>=problemStart) {
							if(beginning==0) beginning=i;
							for(int j=0; j<backtrackingDuration.get(i); j++) {
								baggerSchedule[problemStart+j] --;
							}
						}
					}
					int newDuration = backtrackingDuration.get(beginning);
					for(int i=0; i<newDuration; i++) {
						baggerSchedule[problemStart-1+i] ++;
					}

					int nbToRemove = backtrackingStart.size()-beginning;
					for(int i=nbToRemove; i>0; i--) {
						backtrackingStart.remove(beginning+i-1);
						backtrackingDuration.remove(beginning+i-1);
					}
					backtrackingStart.add(problemStart-1);
					backtrackingDuration.add(newDuration);
					System.out.println("backtracking start" + backtrackingStart);
					System.out.println("backtracking duration" + backtrackingDuration);
				}
			}
			System.out.printf("Proposed modification: %s \n", Arrays.toString(delta));
			System.out.printf("Cashier schedule: %s\n", Arrays.toString(cashierSchedule));
			System.out.printf("New bagger schedule: %s \n", Arrays.toString(baggerSchedule));
			intervals = runSchedule(cashierSchedule,baggerSchedule, seeds, false);
			for(int i=0; i<16; i++) {
				propLongWait[i]=intervals[i].getCfMax();
			}

		}
		System.out.printf("\n\n\nOptimal bagger Schedule: %s\n", Arrays.toString(baggerSchedule));
		System.out.printf("Proportion of long wait times: %s\n", Arrays.toString(propLongWait));

		System.out.println("\n\nFinal schedules: ");
		System.out.printf("Cashier schedule: %s\n", Arrays.toString(cashierSchedule));
		System.out.printf("Bagger schedule:  %s\n", Arrays.toString(baggerSchedule));

	}

}
