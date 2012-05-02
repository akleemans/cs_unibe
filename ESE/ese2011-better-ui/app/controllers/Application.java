package controllers;

import play.*;
import play.mvc.*;
import models.MyCalendar;
import models.Database;
import models.Event;
import models.MyCalendar;
import models.User;
import play.data.validation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {

    public static void index(){
        String applicationUserName = Security.connected();
        Database.setApplicationUser(applicationUserName);
        
        List<MyCalendar> calendars = Database.getApplicationUser().getCalendars();
        
        List<User> otherUsers = Database.getOtherUsers(Database.getApplicationUser());
        
        render(applicationUserName, calendars, otherUsers);
    }
    
    public static void showCalendar(String calendarOwnerName, String applicationUserName, String calendarName, String permissionMessage, String eventNameMessage, String startDateMessage, String endDateMessage, int selectedDay){
    	
    	User calendarOwner = Database.getUserByName(calendarOwnerName);
    	MyCalendar ownerCalendar = calendarOwner.getCalendar(calendarName);
    	
    	List<Event> events = ownerCalendar.getEvents(Database.getApplicationUser());
    	
		Calendar rightNow = Calendar.getInstance();
    	int month = rightNow.get(Calendar.MONTH);
    	
    	SimpleDateFormat dfs = new SimpleDateFormat();
    	String thisMonth = dfs.getDateFormatSymbols().getMonths()[month];
    	
    	int today = rightNow.get(Calendar.DAY_OF_MONTH);
        List<Integer> daysFromLastMonth = new ArrayList<Integer>();
        List<Integer> daysFromThisMonth = new ArrayList<Integer>();
        List<Integer> daysFromNextMonth = new ArrayList<Integer>();
        
        daysFromLastMonth =	getDaysFromLastMonth(month);
        daysFromThisMonth = getDaysFromThisMonth(month);
        daysFromNextMonth = getDaysFromNextMonth(month);
        
        int startOfLastMonth = daysFromLastMonth.get(0);
        
        int monthInt = rightNow.get(Calendar.MONTH);
        List<Integer> EventDaysOfMonth = new ArrayList<Integer>();
        User applicationUser = Database.getUserByName(applicationUserName);
        
        EventDaysOfMonth.addAll(calendarOwner.getCalendar(calendarName).getEventDaysOfMonth(applicationUser, monthInt+1));
        
        //List<Integer> specialDays = new ArrayList<Integer>();
        //specialDays.add(today);
        //List<Integer> EventsOfMonthCopy = EventDaysOfMonth;
        //specialDays.addAll(EventsOfMonthCopy);
        //boolean todayIsSpecialDay = EventDaysOfMonth.contains(today);

        //System.out.println("Today = "+today);
        //System.out.println("Monat = "+monthInt);
        //System.out.println("Events heute = "+EventDaysOfMonth.size());
        render(applicationUserName, calendarOwner, ownerCalendar, events, permissionMessage, eventNameMessage, startDateMessage, endDateMessage, startOfLastMonth, daysFromLastMonth, thisMonth, daysFromThisMonth, daysFromNextMonth, today, EventDaysOfMonth, selectedDay);
    }
    
    public static void showEvents(String calendarOwnerName, String applicationUserName, String calendarName, String permissionMessage, String eventNameMessage, String startDateMessage, String endDateMessage){
    	
    	User calendarOwner = Database.getUserByName(calendarOwnerName);
    	MyCalendar ownerCalendar = calendarOwner.getCalendar(calendarName);
    	
    	List<Event> events = ownerCalendar.getEvents(Database.getApplicationUser());
    	
        render(applicationUserName, calendarOwner, ownerCalendar, events, permissionMessage, eventNameMessage, startDateMessage, endDateMessage);
    }
    
    public static void addEvent(String applicationUserName, String calendarOwnerName, String calendarName, String eventName, String eventStart, String eventEnd, boolean isPrivate){
    	
    	User applicationUser = Database.getUserByName(applicationUserName);
    	
    	User calendarOwner = Database.getUserByName(calendarOwnerName);
    	MyCalendar ownerCalendar = calendarOwner.getCalendar(calendarName);
    	
    	String permissionMessage = " ", eventNameMessage = " ", startDateMessage = " ", endDateMessage = " ";
    	
    	if (userIsAllowedToAddEvents(applicationUserName, ownerCalendar.getOwner().getName())){
    		if (eventNameValid(eventName) && eventStartValid(eventStart) && eventEndValid(eventEnd))
    			ownerCalendar.addEvent(eventName, eventStart, eventEnd, isPrivate);
    		if (!eventNameValid(eventName))
    			eventNameMessage = "invalid event name";
    		if (!eventStartValid(eventStart))
    			startDateMessage = "invalid start date";
    		if (!eventEndValid(eventEnd))
    			endDateMessage = "invalid end date";
    	}
    	else
    		permissionMessage = "You do not have permission to add events to this calendar!";
		
    	showCalendar(ownerCalendar.getOwner().getName(), applicationUserName, ownerCalendar.getName(),permissionMessage, eventNameMessage, startDateMessage, endDateMessage, 0);
    }
    
    private static boolean userIsAllowedToAddEvents(String applicationUserName,
			String calendarOwnerName) {
		return applicationUserName.equals(calendarOwnerName);
	}

	private static boolean eventNameValid(String eventName){
    	return !(eventName == "") && !(eventName.length() == 0);
    }
    
    private static boolean eventStartValid(String eventStart) {
    	return dateValid(eventStart);
    }
    
    private static boolean eventEndValid(String eventEnd) {
    	return dateValid(eventEnd);
    }

	private static boolean dateValid(String eventStart) {
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    	try{
			df.parse(eventStart);
			return true;
		}
		catch (ParseException e) {
			return false;
		}
	}
	
	public static List<Integer> getDaysFromThisMonth(int month) {
		Calendar cal = new GregorianCalendar();
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		List<Integer> l = new ArrayList<Integer>();
		for (int i=0; i < max; i++) {
			l.add(i+1);
		}
		
		return l;
	}
	
	public static List<Integer> getDaysFromLastMonth(int month) {
		List<Integer> l = new ArrayList<Integer>();
		Calendar thisMonth = new GregorianCalendar();
		Calendar lastMonth = new GregorianCalendar();
		thisMonth.set(Calendar.MONTH, month);
		lastMonth.set(Calendar.MONTH, month-1);
		
		int max = lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		thisMonth.setFirstDayOfWeek(Calendar.MONDAY);
		int start = thisMonth.get(Calendar.DAY_OF_WEEK);
		
		for (int i=max-start+1; i < max; i++) {
			l.add(i+1);
		}
		return l;
	}
	
	public static List<Integer> getDaysFromNextMonth(int month) {
		List<Integer> l = new ArrayList<Integer>();
		Calendar thisMonth = new GregorianCalendar();
		Calendar nextMonth = new GregorianCalendar();
		thisMonth.set(Calendar.MONTH, month);
		nextMonth.set(Calendar.MONTH, month+1);
		
		int start = nextMonth.get(Calendar.DAY_OF_WEEK);
		int max = 7-start;
		
		System.out.println(">>Start: "+start);
		
		for (int i=0; i <= max; i++) {
			l.add(i+1);
		}
		
		return l;
	}

	public static void showCalendars(String applicationUserName, String calendarOwnerName){
    	User calendarOwner = Database.getUserByName(calendarOwnerName);
    	
    	List<MyCalendar> calendars = calendarOwner.getCalendars();
    	
    	render(applicationUserName, calendarOwner, calendars);
    }
}