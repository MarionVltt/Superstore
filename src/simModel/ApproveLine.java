package simModel;

import java.util.ArrayList;

public class ApproveLine {
	
	// Implement the queue using an ArrayList object
	private ArrayList<Customer> approveLine = new ArrayList<Customer>();  // Size is initialised to 0
	private int n = 0; //length of the queue
	
	// getters/setters and standard procedures
	protected int getN() { 
		return(approveLine.size()); 
	}
	
	protected void setN(int n) {
		this.n = n;
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
