package calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Provides a calendar and therefore a system
 * to manage events, on which certain actions
 * can be performed (e.g. add, delete, alter).
 *
 */
public class MyCalendar {
	
	String name;
	String owner;
	List<Event> events;
	
	public MyCalendar(String name, String owner) {
		this.name = name;
		this.owner = owner;
		events = new ArrayList<Event>();
	}
	
	public void addEvent(int id, String name, User owner, String start, String end, boolean privateEvent) {
		
		Date startDate = parseStringToDate(start);
		Date endDate = parseStringToDate(end);
		
		Event event = new Event(id, name, owner.getName(), startDate, endDate, privateEvent);
		events.add(event);
	}
	
	/**
	 * Parses a String to a Date representation.
	 * @param str start date (as String)
	 * @return startDate Date
	 */
	private Date parseStringToDate(String str) {
		assert(str.length()==14);
		Date d;
		Calendar c = new GregorianCalendar();
		
		// Parse form "01.01.2011 00:00"
		int year = Integer.valueOf(str.substring(6, 10));
		int month = Integer.valueOf(str.substring(3, 5));
		int date = Integer.valueOf(str.substring(0, 2));
		int hourOfDay = Integer.valueOf(str.substring(11, 13));
		int minute = Integer.valueOf(str.substring(14, 16));
		
		c.clear();
		c.set(year, month, date, hourOfDay, minute);
		d = c.getTime();
		
		return d;
	}

	public void updateEvent(int id, String name, String startDate, String endDate) {
		Iterator<Event> e = events.iterator();
		
		while (e.hasNext()) {
			Event nextEvent = e.next();
			if (nextEvent.getID()==id) {
				nextEvent.setStartDate(parseStringToDate(startDate));
				nextEvent.setEndDate(parseStringToDate(endDate));
				nextEvent.setName(name);
				break;
			}
		}
	}
	
	public Iterator<Event> getAllEvents(String d, String user) {
		boolean ownsCalender = (user.equals(owner)) ? true : false;
		List<Event> eventsCopy = new ArrayList<Event>();
		eventsCopy.addAll(events);
		
		Date date = parseStringToDate(d);
		Iterator<Event> e = eventsCopy.iterator();
		
		while (e.hasNext()) {
			Event nextEvent = e.next();
			if ((nextEvent.isPrivate() && !ownsCalender) || isEarlier(nextEvent.startDate, date))
				e.remove();
		}
		
		Iterator<Event> eventsIterator = eventsCopy.iterator();
		
		return eventsIterator;
	}
	
	public List<Event> getEventsOfDate(String d, String user) {
		boolean ownsCalender = (user.equals(owner)) ? true : false;
		List<Event> eventsCopy = new ArrayList<Event>();
		eventsCopy.addAll(events);
		Date date = parseStringToDate(d);

		for (Iterator<Event> e = eventsCopy.iterator(); e.hasNext(); ) {
			while (e.hasNext()) {
				Event nextEvent = e.next();
				if (!ownsCalender && nextEvent.isPrivate())
					e.remove();
				else if (isEarlier(date,nextEvent.startDate) || isEarlier(nextEvent.endDate,date)) 
					e.remove();
			}
		}
		
		return eventsCopy;
	}
	
	/**
	 * Compares two dates. It checks if date1 is earlier (in days) than date2. 
	 * Returns true if date1 is earlier than date2, but not if earlier
	 * on the same day.
	 * 
	 * @param date1 the first date
	 * @param date2 the second date
	 * @return true if date1 is earlier than date2
	 */
	public boolean isEarlier(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		
		boolean earlier = 
			(cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR) ||
			(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
			cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)));

		return earlier;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getOwner(){
		return this.owner;
	}
}
