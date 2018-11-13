package simModel;

public class Customer {
	
	private int nItems;
	private String payMethod;
	private double startWait;
	
	// getters / setters
	public int getnItems() {
		return nItems;
	}
	public void setnItems(int nItems) {
		this.nItems = nItems;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public double getStartWait() {
		return startWait;
	}
	public void setStartWait(double startWait) {
		this.startWait = startWait;
	}	
}
