package calendar;

import java.util.Iterator;
import java.util.List;

/**
 * A simple user class for user management.
 * Besides a name and (not securely saved) password,
 * users can have a certain permission level.
 * Permission Levels rank as follow:
 * 0 - no permissions; 
 * 1 - normal user;
 * 2 - admin
 */
public class User {
	
	private int permissionLevel;
	private String name;
	private String pw;
	private MyCalendar calendar;
	
	public User(String name, String pw, int permissionLevel) {
		this.name = name;
		this.pw = pw;
		this.permissionLevel = permissionLevel;
		setCalendar(new MyCalendar(name, name));
	}
	
	public String getName() {
		return name;
	}
	public String getPW() {
		return pw;
	}
	public int getPermissionlvl() {
		return permissionLevel;
	}

	public void setCalendar(MyCalendar calendar) {
		this.calendar = calendar;
	}

	public Iterator<Event> getAllEvents(String date, String user) {
		return calendar.getAllEvents(date, user);
	}
	
	public List<Event> getEventsOfDate(String date, String user) {
		return calendar.getEventsOfDate(date, user);
	}

	public MyCalendar getCalendar() {
		return calendar;
	}
	
	public void updateEvent(int id, String name, String startDate, String endDate) {
		calendar.updateEvent(id, name, startDate, endDate);
	}
	
}
