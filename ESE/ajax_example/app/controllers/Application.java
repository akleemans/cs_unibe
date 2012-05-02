package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import models.Calendar;

public class Application extends Controller {

    public static void index() {
    	List<User> users = Database.getUsers();
        render(users);
    }
    
    public static void showCalendars(String name){
    	User user = Database.getUserByName(name);
    	List<Calendar> calendars = user.getCalendars();
    	render(user, calendars);
    }
    
    public static void search(String query){
    	List<User> users = Database.getUsers();	
    	List<User> results = Database.search(query);
        render(results);
    }
    
    public static void editUser(String userName, String message){
    	System.out.println(message);
    	User user = Database.getUserByName(userName);
    	render(user, message);
    }

    public static void saveUser(String oldName, String newName){
    	User user = Database.getUserByName(oldName);
    	if(oldName.equals(newName))
    		editUser(oldName, null);
    	if(Database.getUserByName(newName) != null)
    		editUser(oldName, "Username schon vorhanden.");
    	if(newName.equals(""))
    		editUser(oldName, "Bitte geben Sie einen Namen ein");
    	user.setName(newName);
    	Database.addUser(user);
    	Database.removeUserByName(oldName);
    	editUser(newName, "Änderungen wurden gespeichert!");
    }
}