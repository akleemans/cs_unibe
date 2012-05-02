package calendar;

import java.util.Date;

/**
 * A Class to represent simple Events on
 * a time schedule. Events can be managed
 * or accumulated in the Calendar class.
 *
 */
public class Event {
	
	int id;
	String name;
	String owner;
	Date startDate;
	Date endDate;
	boolean privateEvent;
	
	/**
	 * Creates a new Event based on given inputs.
	 * @param id global id of the event
	 * @param name the name of the event
	 * @param owner the name of the owner
	 * @param startDate when the event is scheduled
	 * @param endDate when the event ends
	 */
	public Event (int id, String name, String owner, Date startDate, 
			Date endDate, boolean privateEvent) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.startDate = startDate;
		this.endDate = endDate;
		this.privateEvent = privateEvent;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public int getID() {
		return id;
	}
	
	public boolean isPrivate() {
		return privateEvent;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String toString(){
		return "id: " + this.id + "; name: " + this.name + "; start date: " + this.startDate.toString() + "; end date: " + this.endDate.toString();
	}
}
