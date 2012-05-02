package models;
import java.util.Date;


public class Event {
	public String name;
	public Date startDate;
	public Date endDate;
	public long id;
	public static long currentId;
	public boolean visible;
	
	public Event(String name, Date startDate, Date endDate, boolean visible) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.visible = visible;
		this.id = Event.currentId;
		Event.currentId++;
	}
	
	public boolean isAtDay(Date date) {
		int factor = 1000*60*60*24;
		return (this.startDate.getTime()/factor <= date.getTime()/factor && date.getTime()/factor <= this.endDate.getTime()/factor);
	}

	public boolean isPublic(){
		return visible;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getId() {
		return id;
	}
}
