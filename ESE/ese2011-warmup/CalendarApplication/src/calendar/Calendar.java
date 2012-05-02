package calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Provides a calendar and therefore a system
 * to manage events, on which certain actions
 * can be performed (e.g. add, delete, alter).
 * @author Adrianus Kleemans
 *
 */
public class Calendar {
	
	String name;
	String owner;
	List<Event> events;
	
	public Calendar(String name, String owner) {
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
	 * @param start Date (as String)
	 * @return startDate Date
	 */
	private Date parseStringToDate(String str) {
		assert(str.length()==14);
		Date d;
		java.util.Calendar c = new GregorianCalendar();
		
		int year = Integer.valueOf(str.substring(6, 10));
		int month = Integer.valueOf(str.substring(3, 5));
		int date = Integer.valueOf(str.substring(0, 2));
		int hourOfDay = Integer.valueOf(str.substring(0, 2));
		int minute = Integer.valueOf(str.substring(0, 2));
		
		c.clear();
		c.set(year, month, date, hourOfDay, minute);
		d = c.getTime();
		
		return d;
	}

	public void updateEvent(String name, String startDate, String endDate) {
		//for(Iterator )
		//if (name, owner, startDate) {
			
		//}
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
		
		return e;
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
		java.util.Calendar cal1 = java.util.Calendar.getInstance();
		java.util.Calendar cal2 = java.util.Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		
		boolean earlier = 
			(cal1.get(java.util.Calendar.YEAR) < cal2.get(java.util.Calendar.YEAR) ||
			(cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR) &&
			cal1.get(java.util.Calendar.DAY_OF_YEAR) < cal2.get(java.util.Calendar.DAY_OF_YEAR)));

		return earlier;
	}
	
	
}
