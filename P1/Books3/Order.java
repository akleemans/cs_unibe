import java.util.*;
import java.util.Scanner;

public class Order {
	
	private String customerName, customerAddress;
	private long id;
	private ArrayList<IMedium> orderedObjects = new ArrayList<IMedium>();
	private static long counter = 1;
	
	public Order() {
		this.id = counter;
		counter ++;
	}
	
	public void add(IMedium m) {
		orderedObjects.add(m);
	}

	public ArrayList<IMedium> getOrderedMedia() {
		return orderedObjects;
	}
	
	public int getTotalPrice() {
		int total = 0;
		for(IMedium m : orderedObjects) {
			total += m.getPrice();
		}
		return total;
	}
	
	public void setCustomerName(String newCustomerName) {
		customerName = newCustomerName;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerAddress(String newCustomerAddress) {
		customerAddress = newCustomerAddress;
	}
	
	public String getCustomerAddress() {
		return customerAddress;
	}
}