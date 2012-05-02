package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import play.*;
import play.jobs.*;
import play.test.*;
 
import models.*;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {

        if(Database.getUsers().isEmpty()) {
        	User user1;
        	Calendar calendar1;
        	Event event1, event2;
        	SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    		df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    		user1 = new User("Hans", "1");
    		user1.addCalendar("Hausaufgaben");
    		calendar1 = user1.getCalendarByName("Hausaufgaben");
    		try {
				event1 = new Event("event1", df.parse("17.1.1990 10:00"), df.parse("17.1.1990 13:00"), true);
	    		event2 = new Event("event2", df.parse("17.1.2012 00:00"), df.parse("18.1.2012 00:00"), false);
	    		calendar1.addEvent(event1);
	    		calendar1.addEvent(event2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            Database.addUser(user1);
            Database.addUser(new User("Heinrich",""));
            Database.addUser(new User("Harold",""));
            Database.addUser(new User("Heinz",""));
            Database.addUser(new User("Henniez",""));
            Database.addUser(new User("Holland",""));
            Database.addUser(new User("Harry",""));
            Database.addUser(new User("Hennrich",""));
            Database.addUser(new User("Haudegen",""));
            Database.addUser(new User("Hubble",""));
            Database.addUser(new User("Haarig",""));
        }

    }
 
}