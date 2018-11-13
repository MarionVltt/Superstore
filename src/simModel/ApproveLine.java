package simModel;

import java.util.ArrayList;

public class ApproveLine {
	
	// Implement the queue using an ArrayList object
	protected ArrayList<Customer> approveLine = new ArrayList<Customer>();  // Size is initialised to 0
	protected int n = 0; //length of the queue
	
	// getters/setters and standard procedures
	protected int getN() { 
		return(approveLine.size()); 
	}
	
	protected void spInsertQue(Customer cust) { 
		approveLine.add(cust); 
	}
	
	protected Customer spRemoveQue() { 
		Customer cust = null;
		if(approveLine.size() != 0) cust = approveLine.remove(0);
		return(cust);
	}
}
