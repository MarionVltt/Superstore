package simModel;

import java.util.HashSet;

public class Counter {
	
	private boolean uOpen;
	private boolean isBusy;
	private Customer customer;
	private boolean baggerPresent;
	private String state;
	
	// getters / setters
	public boolean isuOpen() {
		return uOpen;
	}
	public void setuOpen(boolean uOpen) {
		this.uOpen = uOpen;
	}
	public boolean isBusy() {
		return isBusy;
	}
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public boolean isBaggerPresent() {
		return baggerPresent;
	}
	public void setBaggerPresent(boolean baggerPresent) {
		this.baggerPresent = baggerPresent;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
