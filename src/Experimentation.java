
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
		ConfidenceInterval [] intervals = new ConfidenceInterval[16]; 
		intervals=runSchedule(cashierSchedule, baggerSchedule,true);
		for(int i=0; i<16; i++) {
			propLongWait[i]=intervals[i].getPointEstimate();
		}
		while(!waitOK(propLongWait,CEIL)) {
			
		}
		
		
		
		
	}

}
