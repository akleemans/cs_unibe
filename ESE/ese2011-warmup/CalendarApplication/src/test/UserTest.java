package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calendar.MyCalendar;
import calendar.User;

public class UserTest {

	User testUser1;
	User testUser2;
	
	MyCalendar calendar1;
	MyCalendar calendar2;
		
	@Before
	public void setUp() {
		testUser1 = new User("TestUser1", "password", 1);
		testUser2 = new User("TestUser2", "password", 1);
		
		calendar1 = new MyCalendar("TestCalendar1", "TestUser1");
		calendar2 = new MyCalendar("TestCalendar2", "TestUser2");
		
		testUser1.setCalendar(calendar1);
		testUser2.setCalendar(calendar2);
	
		calendar1.addEvent(1, "Testevent11", testUser1, "12.08.2011 13:00", "12.08.2011 14:00", false);
		calendar1.addEvent(2, "Testevent12", testUser1, "13.08.2011 13:00", "15.08.2011 14:00", false);
		calendar1.addEvent(3, "Testevent13", testUser1, "14.08.2011 13:00", "19.08.2011 13:00", false);
		calendar1.addEvent(4, "Testevent14", testUser1, "17.08.2011 13:00", "19.08.2011 14:00", false);
		
	}

	@Test
	public void shouldReturnCorrectUserName() {
		assertEquals(testUser1.getName(), "TestUser1");
		assertEquals(testUser2.getName(), "TestUser2");
	}
	
	@Test
	public void shouldReturnCorrectNumberOfEventsAtCertainDate() {
		assertTrue(testUser1.getEventsOfDate("14.08.2011 14:00", "TestUser1").size() == 2);
		assertTrue(testUser1.getEventsOfDate("10.08.2011 14:00", "TestUser1").size() == 0);
		assertTrue(testUser1.getEventsOfDate("14.08.2011 10:00", "TestUser1").size() == 2); //10:00 is BEFORE 14:00, but still on the same day, thus it is also on the list
		assertTrue(testUser1.getEventsOfDate("12.08.2011 10:00", "TestUser1").size() == 1);
	}
	
	@Test
	public void shouldHaveMoreElementsBeforeMostRecentEntry() {
		assertTrue(testUser1.getAllEvents("09.08.2011 14:00", "TestUser1").hasNext());
		assertTrue(testUser1.getAllEvents("14.08.2011 12:00", "TestUser1").hasNext());
		assertFalse(testUser1.getAllEvents("20.08.2011 14:00", "TestUser1").hasNext());
	}
	
}
