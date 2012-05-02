package calendarTests;


import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import models.Database;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import static org.junit.Assert.*;

public class DatabaseTest extends UnitTest {
	
	
	
	@Before
	public void setUp() {
		
		Database.clearAll();
		
		Database.addUser("TestUserName1", "1234");
		Database.addCalendar("Calendar of Test User", "TestUserName1");
		Database.getUserByName("TestUserName1").addEventToCalendar("Calendar of Test User","Testevent","08.10.2011 00:00", "08.10.2011 23:59", false);
	
		Database.addUser("TestUserName2", "asdf");
		Database.addUser("TestUserName3", "3333");
	}

	@Test
	public void shouldReturnCorrectUserList() {
		assertEquals(Database.getUserList().get(0).getName(),"TestUserName1");
		assertEquals(Database.getUserList().get(1).getName(),"TestUserName2");
		assertEquals(Database.getUserList().get(2).getName(),"TestUserName3");
	}
	
	@Test
	public void shouldReturnCorrectUserByName() {
		assertEquals(Database.getUserByName("TestUserName1").getName(), "TestUserName1");
		assertEquals(Database.getUserByName("TestUserName2").getName(), "TestUserName2");
		
	}
	
	@Test
	public void shouldReturnTheOtherUsers() {
		User user1 = Database.getUserByName("TestUserName1");
		User user2 = Database.getUserByName("TestUserName2");
		User user3 = Database.getUserByName("TestUserName3");
		
		assertTrue(Database.getOtherUsers(user1).contains(user2));
		assertTrue(Database.getOtherUsers(user1).contains(user3));
		assertFalse(Database.getOtherUsers(user1).contains(user1));
	}
	
	@Test
	public void shouldReturnCorrectApplicationUser() {
		Database.setApplicationUser("TestUserName3");
		assertEquals(Database.getApplicationUser().getName(),"TestUserName3");
	}
	
}