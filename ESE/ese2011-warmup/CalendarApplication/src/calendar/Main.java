package calendar;

import java.util.Scanner;

/**
 * Starts a new session, demonstrating the calendar
 * with some predefined users and events.
 * Navigation is possible with numbers, for using
 * an existing user account see below.
 */
public class Main {
	
	static Session currentSession;
	static private int stop = 0;
	static String earliestDatePossible = "01.01.1970 00:00";
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		currentSession = new Session();
		
		// add some default users
		currentSession.addUser("guest", "12345", 1);
		currentSession.addUser("admin", "qwert", 2);
		
		// add some default events
		currentSession.addEvent("Armageddon", currentSession.getFirstUser(), "21.12.2012 00:00", "21.12.2012 23:59", false);
		currentSession.addEvent("Release Date of RAGE", currentSession.getFirstUser(), "07.10.2011 00:00", "07.10.2011 23:00", false);
		currentSession.addEvent("New Years Eve 2011", currentSession.getFirstUser(), "31.12.2011 18:00", "01.01.2012 03:00", true);
		
		// begin Session
		currentSession.setSessionActive();
		
		System.out.println("\nHi and welcome to CalendarApplication!");
		System.out.println("Please log in. [Hint: \"admin\" -> \"qwert\"; \"guest\" -> \"12345\"]");
		boolean loggedIn = false;
		
		while (!loggedIn) {
			loggedIn = currentSession.logIn();
		}
		
		//Scanner in = new Scanner(System.in);
		
		int userChoice = 1;
		
		while (userChoice != stop) {
			System.out.println("\nPlease select: ");
			System.out.println("1 - show all events of a certain calender");
			System.out.println("2 - show own events of certain date");
			System.out.println("3 - add event");
			System.out.println("4 - modify event");
			System.out.println("5 - add user (only admins)");
			System.out.println("0 - quit");
			System.out.println("\n(Please note: Use the following format for dates: dd.mm.yyyy hh:mm)");
			
			userChoice = in.nextInt();
			
			switch (userChoice) {
			case 1:
				getAllEvents();
				break;
				
			case 2:
				getEventsOfDate();
				break;
				
			case 3:
				addEvent();
				break;
			
			case 4:
				updateEvent();
				break;
			
			case 5:
				addUser();
				break;
				
			case 0:
				System.out.println("So long, and thanks for all the fish!");
				break;
			}
		}
	}

	private static void getAllEvents() {
		System.out.println("You want to view events from a certain calendar. Please enter its name:");
		String name = in.next();
		System.out.println("From when on do you want to view the events? Please enter date:");
		String date = in.next();
		String time = in.next();
		date+=" "+time;
		currentSession.getAllEvents(name, date);
	}
	
	private static void getEventsOfDate() {
		System.out.println("You want to view your own events at a certain date. Please enter date:");
		String date = in.next();
		String time = in.next();
		date+=" "+time;
		currentSession.getEventsOfDate(date);
	}
	
	private static void addEvent() {
		System.out.println("You want to add an event. What's its name?");
		String name = in.next();
		System.out.println("Which Start Date?");
		String startDate = in.next();
		String startTime = in.next();
		System.out.println("Please specify End Date.");
		String endDate = in.next();
		String endTime = in.next();
		System.out.println("Do you want to make it private? (true/false)");
		String priv = in.next();
		
		boolean privateEvent = (priv.equals("true")) ? true : false;
		startDate+=" "+startTime;
		endDate+=" "+endTime;
		
		currentSession.addEvent(name, currentSession.getCurrentUser(), startDate, endDate, privateEvent);
	}
	
	private static void updateEvent() {
		System.out.println("You want to modify an event. Please enter its id:");
		currentSession.getEventsOfDate(earliestDatePossible);
		int id = in.nextInt();
		System.out.println("New Name?");
		String name = in.next();
		System.out.println("New Start Date?");
		String startDate = in.next();
		String startTime = in.next();
		System.out.println("New End Date?");
		String endDate = in.next();
		String endTime = in.next();
		
		endDate+=" "+endTime;
		startDate+=" "+startTime;
		
		currentSession.updateEvent(id, name, startDate, endDate);
	}
	
	private static void addUser() {
		System.out.println("You want to add a user. What's his/her name?");
		String name = in.next();
		System.out.println("Set a password (weak passwords such as 1234 are not recommended)");
		String pw = in.next();
		System.out.println("Set a permission level. 1 = user, 2 = admin.");
		int permissionLevel = in.nextInt();
		
		currentSession.addUser(name, pw, permissionLevel);
	}
}
