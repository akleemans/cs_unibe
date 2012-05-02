package models;

import java.util.ArrayList;
import java.util.List;

public class Database {
	
	private static ArrayList<User> userList = new ArrayList<User>();
	
	private static User applicationUser;
	
	public static void addUser(String username, String password) {
		userList.add(new User(username,password));
	}
	
	public static ArrayList<User> getUserList(){
		return userList;
	}

	public static void addCalendar(String calendarName, String ownerName) {
		for (User user : userList){
			if (user.getName().equals(ownerName))
				user.addCalender(calendarName);
		}
	}

	public static User getUserByName(String userName) {
		for (User user : userList){
			if (user.getName().equals(userName) || user.getName() == userName)
				return user;
		}
		return null;
	}

	public static List<User> getOtherUsers(User user) {
		
		List<User> userListToReturn = (List<User>) userList.clone();
		
		int i = 0;
		for (User userToIterate : userList){
			if (userToIterate.equals(user))
				break;
			else
				i+=1;
		}
		
		userListToReturn.remove(i);
		return userListToReturn;
	}
	

	public static void setApplicationUser(String applicationUserName) {
		applicationUser = getUserByName(applicationUserName);
	}
	
	public static User getApplicationUser(){
		return applicationUser;
	}
	
	
	/* Just for testing purposes*/
	public static void clearAll(){
		userList.clear();
	}
	

}
