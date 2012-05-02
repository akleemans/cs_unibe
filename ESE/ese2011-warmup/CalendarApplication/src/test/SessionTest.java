package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import calendar.MyCalendar;
import calendar.Session;
import calendar.User;

public class SessionTest {
	
	Session testSession;
	User testUser1;
	User testUser2;
	
	MyCalendar calendarOfTestUser1;
	MyCalendar calendarOfTestUser2;
	
	@Before
	public void setUp() {
		testUser1 = new User("TestUser1", "password1", 1);
		testUser2 = new User("TestUser2", "password2", 1);
		calendarOfTestUser1 = new MyCalendar("TestCalendar1", "TestUser1");
		calendarOfTestUser2 = new MyCalendar("TestCalendar2", "TestUser2");
		
		testSession = new Session(testUser1);
		testSession.addUser("TestUser1", "password1", 1);
		testSession.addUser("TestUser2", "password2", 1);
		
		testUser1.setCalendar(calendarOfTestUser1);
		testUser2.setCalendar(calendarOfTestUser2);
		
		calendarOfTestUser2.addEvent(1, "Testevent1", testUser2, "13.08.2011 13:00", "15.08.2011 13:00", false);
		calendarOfTestUser2.addEvent(2, "Testevent2", testUser2, "14.08.2011 13:00", "16.08.2011 13:00", false);
		calendarOfTestUser2.addEvent(3, "Testevent3", testUser2, "15.08.2011 13:00", "17.08.2011 13:00", true);
	}

	@Test
	public void shouldOnlySeePublicEvents() {
		testSession.getAllEvents("TestUser1", "10.08.2011 10:00");
		testSession.getAllEvents("TestUser2", "10.08.2011 10:00");
	}

}
