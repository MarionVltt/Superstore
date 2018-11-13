package simModel;

import java.util.ArrayList;

public class ApproveLine {
	
	// Implement the queue using an ArrayList object
	protected ArrayList<Integer> approveLine = new ArrayList<Integer>();  // Size is initialised to 0
	protected int n = 0; //length of the queue
	
	// getters/setters and standard procedures
	protected int getN() { 
		return(approveLine.size()); 
	}
	
	protected void spInsertQue(int id) { 
		approveLine.add(id); 
	}
	
	protected int spRemoveQue() { 
		int id = Constants.NONE;
		if(approveLine.size() != 0) id = approveLine.remove(0);
		return(id);
	}
}
