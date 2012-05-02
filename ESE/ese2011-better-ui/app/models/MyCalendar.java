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
		String time = this.parseStringToTime(startDate);
		int day = this.getDay(startDate);
		
		events.add(new Event(eventName, startDateToAdd, endDateToAdd, isPrivate, time, day));
	}
	
	private int monthToInt(String month){
		if (month.equals("Jan")){return 1;}
		else if (month.equals("Feb")){return 2;}
		else if (month.equals("Mar")){return 3;}
		else if (month.equals("Apr")){return 4;}
		else if (month.equals("May")){return 5;}
		else if (month.equals("Jun")){return 6;}
		else if (month.equals("Jul")){return 7;}
		else if (month.equals("Aug")){return 8;}
		else if (month.equals("Sep")){return 9;}
		else if (month.equals("Oct")){return 10;}
		else if (month.equals("Nov")){return 11;}
		else if (month.equals("Dec")){return 12;}
		else{return 0;}
	}
	
	public List<Integer> getEventDaysOfMonth(User applicationUser, int month){
		
		List<Event> events = this.getEvents(applicationUser);
		List<Integer> listToReturn = new ArrayList<Integer>();
		
		for (Event event : events){
			
			int startMonth = this.monthToInt(event.startDate.toString().substring(4,7));
			int endMonth = this.monthToInt(event.endDate.toString().substring(4,7));
			
			if (startMonth == month && endMonth == month){
				int startDay = Integer.valueOf(event.startDate.toString().substring(8,10));
				int endDay = Integer.valueOf(event.endDate.toString().substring(8,10));
				
				int addDay = startDay;
				while (addDay <= endDay){
					listToReturn.add(addDay);
					addDay+=1;
				}
			}
			else if (startMonth == month && endMonth != month){
				int startDay = Integer.valueOf(event.startDate.toString().substring(8,10));
				int endDay = Integer.valueOf(event.endDate.toString().substring(8,10));
				
				int addDay = startDay;
				
				while (addDay <= 31){
					listToReturn.add(addDay);
					addDay+=1;
				}
			}
			else if (startMonth != month && endMonth == month){
				int startDay = Integer.valueOf(event.startDate.toString().substring(8,10));
				int endDay = Integer.valueOf(event.endDate.toString().substring(8,10));
				
				int addDay = endDay;
				
				while (addDay >= 01){
					listToReturn.add(addDay);
					addDay-=1;
				}
			}
			else if (startMonth < month && endMonth > month){
				int addDay = 0;//= endDay;
				while (addDay <= 31){ // if a month has only 28/30 days, it does not matter for the output, when 29,30,31 are included
					listToReturn.add(addDay);
					addDay+=1;
				}
			}
		}
		
		return listToReturn;
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
	
	private String parseStringToTime(String dateString) {
		assert(dateString.length()==14);
		
		String time = dateString.substring(11);
		
		return time;
	}
	
	private int getDay(String dateString) {
		assert(dateString.length()==14);
		
		int day = Integer.valueOf(dateString.substring(0,2));

		return day;
	}

}
