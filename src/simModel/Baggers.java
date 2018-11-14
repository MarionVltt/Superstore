package simModel;

import java.util.HashSet;

/*
 * Resource Group representing the baggers
 */
public class Baggers {
	// For implementing the group, use a HashSet object.
	protected HashSet<Customer> group = new HashSet<Customer>();
	/* nAvail value is decremented each time a bagger is assigned to a counter 
	* and incremented when a bagger is freed from a counter. 
	* At schedule change, the number may become negative to show that some baggers 
	* are working overtime.
	*/
	protected int nAvail;
	
	// Required methods to manipulate the group
	protected void insertGrp(Customer icCustomer) {	group.add(icCustomer); }
	protected boolean removeGrp(Customer icCustomer) { return(group.remove(icCustomer)); }
	protected int getN() { return group.size(); }
}
