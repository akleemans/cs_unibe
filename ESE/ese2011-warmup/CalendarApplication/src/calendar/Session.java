package calendar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Manages a session of CalendarApplication,
 * especially permissions and the list of active 
 * users which can operate on the system.
 */
public class Session {
	
	private int globalEventID;
	private User activeUser;
	private List<User> users;
	private boolean sessionActive = false;
	
	public Session() {
		users = new ArrayList<User>();
	}
	
	/**
	 * Logs in the specified user.
	 * @param user
	 * @return true, if successful, and false if not
	 */
	public boolean logIn() {
		Scanner in = new Scanner(System.in);
		String name;
		String pw;
		
		// Get user input
		System.out.println("Please provide your name:");
		name = in.next();
		
		System.out.println("Password:");
		pw = in.next();
		
		//loop through users
		boolean userMatch = false;
		
		for (Iterator<User> it = users.iterator(); it.hasNext();) {
			User currentUser = it.next();
			
			if (name.equals(currentUser.getName()) && pw.equals(currentUser.getPW())) {
				userMatch = true;
				activeUser = currentUser;
				break;
			}
		}

		if (userMatch) {
			System.out.println("Welcome!");
			globalEventID = 0;
			return true;
		}
		else {
			System.out.println("Username and password do not match.");
			System.out.println("Please contact your system administrator.");
			return false;
		}
	}
	
	public void addUser(String name, String pw, int permissionLevel) {
		if (!sessionActive || activeUser.getPermissionlvl() > 1) {
			users.add(new User(name, pw, permissionLevel));
			System.out.println("New user successfully created.");
		}
		else {
			System.out.println("Sorry, you are not allowed to do that :(");
		}
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public User getFirstUser() {
		assert(!users.isEmpty());
		return users.get(0);
	}
	
	public User getCurrentUser() {
		return activeUser;
	}

	public void addEvent(String name, User user, String startDate, String endDate, boolean privateEvent) {
		user.getCalendar().addEvent(globalEventID++, name, user, startDate, endDate, privateEvent);
		System.out.println("New event successfully created.");
	}

	public void updateEvent(int id, String name, String startDate, String endDate) {
		activeUser.updateEvent(id, name, startDate, endDate);
		System.out.println("Date succesfully modified.");
	}
	

	public void getAllEvents(String name, String date) {
		User user = null;
		// Loop through users to find calendar
		for (Iterator<User> u = users.iterator(); u.hasNext();) {
			User currentUser = u.next();
			if (currentUser.getName().equals(name)) {
				user = currentUser;
			}
		}
		
		if (user!=null) {
		Iterator<Event> e = user.getAllEvents(date, activeUser.getName());
		
		// Loop through List of events to show them
		System.out.println("name | owner | start date | end date");
		System.out.println("------------------------------------");
	    while (e.hasNext()){
	    	Event currentEvent = e.next();
			System.out.println(currentEvent.getName()+" | "+currentEvent.getOwner()+" | "
					+currentEvent.getStartDate()+ " | "+currentEvent.getEndDate());
	      }
		}
		else {
			System.out.println("Error: Can't find a calendar with the given name.");
		}
	}
	
	public void getEventsOfDate(String date) {
		List<Event> events = activeUser.getEventsOfDate(date, activeUser.getName());
		
		// Loop through List of events to show them
		System.out.println("name | owner | start date | end date");
		System.out.println("------------------------------------");
		for (Iterator<Event> e = events.iterator(); e.hasNext();) {
			Event currentEvent = e.next();
			System.out.println(currentEvent.getName()+" | "+currentEvent.getOwner()+" | "
					+currentEvent.getStartDate()+ " | "+currentEvent.getEndDate());
		}
	}
	
	public void setSessionActive() {
		this.sessionActive = true;
	}
}
