package simModel;

import java.util.ArrayList;

/*
 * Queue containing the ids of the counters waiting for supervisor's approval of a payment 
 * with a check and no card
 *
 */
public class ApproveLine {
	
	// Implement the queue using an ArrayList object
	protected ArrayList<Integer> approveLine = new ArrayList<Integer>(); 
	protected int n = 0; //length of the queue
	
	// getters/setters and standard procedures
	/*
	 * @return n, the size of the queue
	 */
	protected int getN() { 
		return(approveLine.size()); 
	}
	
	/*
	 * @param id, the id of the counter added to the queue
	 */
	protected void spInsertQue(int id) { 
		approveLine.add(id); 
		n+=1;
	}
	
	/*
	 * @return id, the first element of the queue if it's not empty 
	 * else returns NONE
	 */
	protected int spRemoveQue() { 
		int id = Constants.NONE;
		if(approveLine.size() != 0) id = approveLine.remove(0); //delete and return the first element
		n-=1;
		return(id);
	}
}
