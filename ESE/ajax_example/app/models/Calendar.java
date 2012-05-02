package models;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Calendar {
	public User owner;
	public String name;
	public List<Event> events;
	
	public Calendar(User owner, String name) {
		super();
		this.owner = owner;
		this.name = name;
		this.events = new LinkedList<Event>();
	}

	public List<Event> getEventsAfter(User user, Date date) {
		List<Event> eventList = new LinkedList<Event>();
		for(Event event: events){
			if(event.getEndDate().after(date) && (event.isPublic() || this.owner.equals(user)))
				eventList.add(event);
		}
		return eventList;
	}

	public List<Event> getEventsAtDay(User user, Date date) {
		List<Event> eventList = new LinkedList<Event>();
		for(Event event: events){
			if(event.isAtDay(date) && (event.isPublic() || this.owner.equals(user)))
				eventList.add(event);
		}
		return eventList;
	}

	public String getName() {
		return name;
	}
	
	public void addEvent(String name, Date startDate, Date endDate, boolean visible){
		events.add(new Event(name, startDate, endDate, visible));
	}
	public void addEvent(Event event){
		events.add(event);
	}
}
