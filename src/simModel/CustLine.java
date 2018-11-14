package simModel;

import java.util.ArrayList;

/*
 * Queue set, each member represents a queue at a counter
 */
public class CustLine {
	// Implement the queue using an ArrayList object
	protected ArrayList<Customer> custLine = new ArrayList<Customer>(); 
	protected int n = 0; //length of the queue
	
	// getters/setters and standard procedures
	protected int getN() { 
		return(custLine.size()); 
	}
	
	
	protected void spInsertQue(Customer cust) { 
		custLine.add(cust);
		n+=1;
	}
	
	protected Customer spRemoveQue() { 
		Customer cust = null;
		if(custLine.size() != 0) cust = custLine.remove(0); // delete and return the first element
		n-=1;
		return(cust);
	}
}
