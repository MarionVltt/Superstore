package simModel;

import java.util.HashSet;

public class Baggers {
	// For implementing the group, use a HashSet object.
	protected HashSet<Customer> group = new HashSet<Customer>();
	private int nAvail;
	
	// Required methods to manipulate the group
	protected void insertGrp(Customer icCustomer) {	group.add(icCustomer); }
	protected boolean removeGrp(Customer icCustomer) { return(group.remove(icCustomer)); }
	protected int getN() { return group.size(); }
}
