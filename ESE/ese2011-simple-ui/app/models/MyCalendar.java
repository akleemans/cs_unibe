package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MyCalendar {
	
	public String name;
	public User owner;
	private List<Event> events = new ArrayList<Event>();
	
	public MyCalendar(String name, User owner){
		this.name=name;
		this.owner=owner;
	}
	
	public String getName(){
		return this.name;
	}
	
	public User getOwner(){
		return this.owner;
	}

	public List<Event> getEvents(User applicationUser) {
		
		List<Event> eventsToReturn = new ArrayList<Event>();
		
		for (Event event : events){
			if (applicationUser.equals(this.owner))
				eventsToReturn.add(event);
			else
				if (!event.isPrivate()){
					eventsToReturn.add(event);
				}
		}
		Collections.sort(eventsToReturn);
		return eventsToReturn;
	}

	public void addEvent(String eventName, String startDate, String endDate, boolean isPrivate) {
		
		Date startDateToAdd = this.parseStringToDate(startDate);
		Date endDateToAdd = this.parseStringToDate(endDate);
		
		events.add(new Event(eventName, startDateToAdd, endDateToAdd, isPrivate));
	}
	
	/**
	 * Parses a String to a Date representation.
	 * @param dateString date (as String)
	 * @return Date
	 */
	private Date parseStringToDate(String dateString) {
		assert(dateString.length()==14);
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		
		Date dateToReturn = null;
		try {
			dateToReturn = df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateToReturn;
	}


}
