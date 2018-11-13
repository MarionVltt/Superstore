package simModel;

import java.util.ArrayList;

public class CustLine {
	// Implement the queue using an ArrayList object
	protected ArrayList<Customer> custLine = new ArrayList<Customer>();  // Size is initialised to 0
	protected int n = 0; //length of the queue
	
	// getters/setters and standard procedures
	protected int getN() { 
		return(custLine.size()); 
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
