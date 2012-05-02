package models;

import java.util.ArrayList;

public class User {
	
	public String name;
	private String password;
	private ArrayList<MyCalendar> calendars = new ArrayList<MyCalendar>();
	
	public User(String name, String password){
		this.name = name;
		this.password = password;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPassword(){
		return this.password;
	}

	public void addCalender(String calendarName) {
		this.calendars.add(new MyCalendar(calendarName, this));
	}

	
	public MyCalendar getCalendar(String calendarName) {
		for (MyCalendar calendar : this.calendars){
			if (calendarName.equals(calendar.getName()))
					return calendar;
		}
		return null;
	}

	public void addEventToCalendar(String calendarName, String eventName, String startDate, String endDate, boolean isPrivate) {
		for (MyCalendar calendar : this.calendars){
			if (calendarName == calendar.getName())
					calendar.addEvent(eventName, startDate, endDate, isPrivate);
		}
	}
	
	public ArrayList<MyCalendar> getCalendars() {
		return this.calendars;
	}

}
