package simModel;

import java.util.ArrayList;

public class CustLine {
	// Implement the queue using an ArrayList object
	private ArrayList<Customer> custLine = new ArrayList<Customer>();  // Size is initialised to 0
	private int n = 0; //length of the queue
	
	// getters/setters and standard procedures
	protected int getN() { 
		return(custLine.size()); 
	}
	
	protected void setN(int n) {
		this.n = n;
	}
	
	protected void spInsertQue(Customer cust) { 
		custLine.add(cust); 
	}
	
	protected Customer spRemoveQue() { 
		Customer cust = null;
		if(custLine.size() != 0) cust = custLine.remove(0);
		return(cust);
	}
}
