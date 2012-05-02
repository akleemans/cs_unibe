package models;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class User {
	public String name;
	public String password;
	public List<Calendar> calendars;
	
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		this.calendars = new LinkedList<Calendar>();
	}
	
	public void addCalendar(String name){
		this.calendars.add(new Calendar(this, name));
	}
	
	public List<Event> getEventsAfter(Calendar calendar, Date date){
		return calendar.getEventsAfter(this, date);
	}
	
	public List<Event> getEventsAtDay(Calendar calendar, Date date){
		return calendar.getEventsAtDay(this, date);
	}
	
	public Calendar getCalendarByName(String name){
		for(Calendar calendar: calendars)
			if(calendar.getName().equals(name))
				return calendar;
		return null;
	}
	
	@Override
	public boolean equals(Object o){
		return false;
	}
	
	public boolean equals(User u){
		return this.name.equals(u.getName());
	}
	
	///Getters&Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Calendar> getCalendars() {
		return calendars;
	}

	
}
