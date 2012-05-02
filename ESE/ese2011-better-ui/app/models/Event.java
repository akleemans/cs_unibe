package models;

import java.util.Date;

public class Event implements Comparable<Event>{
	
	public String name;
	private boolean isPrivate;
	public Date startDate;
	public Date endDate;
	public String time;
	public int day;

	public Event(String name, Date startDate, Date endDate, boolean isPrivate, String time, int day) {
		this.name = name;
		this.isPrivate = isPrivate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.time = time;
		this.day = day;
	}

	public boolean isPrivate() {
		return isPrivate;
	}
	
	@Override
	public int compareTo(Event compareEvent) {
		if (this.startDate.getTime() > compareEvent.startDate.getTime())
			return 1;
		if (this.startDate.getTime() == compareEvent.startDate.getTime())
			return 0;
		else
			return -1;
	}
}
