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
			
    	showEvents(ownerCalendar.getOwner().getName(), applicationUserName, ownerCalendar.getName(), permissionMessage, eventNameMessage, startDateMessage, endDateMessage);
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

	public static void showCalendars(String applicationUserName, String calendarOwnerName){
    	User calendarOwner = Database.getUserByName(calendarOwnerName);
    	
    	List<MyCalendar> calendars = calendarOwner.getCalendars();
    	
    	render(applicationUserName, calendarOwner, calendars);
    }
}