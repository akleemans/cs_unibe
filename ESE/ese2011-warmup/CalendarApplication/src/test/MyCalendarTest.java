package test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import calendar.MyCalendar;
import calendar.User;

public class MyCalendarTest {
	
	MyCalendar myCalendar;
	Calendar cal1;
	Calendar cal2;
	User testUser;
	
	@Before
	public void setUp() {
		testUser = new User("TestUser", "passwort", 2);
		myCalendar = new MyCalendar("TestCalendar", "TestUser");
		cal1 = new GregorianCalendar();
		cal2 = new GregorianCalendar();
		
		myCalendar.addEvent(10, "Testevent", testUser, "13.08.2011 13:00", "15.08.2011 13:00", false);
	}

	@Test
	public void shouldReturnCorrectName() {
		assertEquals(myCalendar.getName(), "TestCalendar");
	}
	
	@Test
	public void shouldReturnCorrectOwner() {
		assertEquals(myCalendar.getOwner(), "TestUser");
	}
	
	@Test
	public void shouldReturnCorrectSingleEvent() {
		assertTrue(myCalendar.getEventsOfDate("14.08.2011 14:00", "TestUser").size() == 1);
		assertEquals(myCalendar.getEventsOfDate("13.08.2011 14:00", "Testuser").get(0).toString(),"id: 10; name: Testevent; start date: Tue Sep 13 13:00:00 CEST 2011; end date: Thu Sep 15 13:00:00 CEST 2011");
	}
	
	@Test
	public void shouldReturnCorrectMultipleEvents() {
		
		myCalendar.addEvent(22, "Testevent2", testUser, "13.08.2011 15:00", "15.08.2011 15:00", false);
		
		assertTrue(myCalendar.getEventsOfDate("14.08.2011 14:00", "TestUser").size() == 2);
		assertEquals(myCalendar.getEventsOfDate("13.08.2011 14:00", "Testuser").get(0).getStartDate().toString(), "Tue Sep 13 13:00:00 CEST 2011");
		assertEquals(myCalendar.getEventsOfDate("13.08.2011 14:00", "Testuser").get(1).getStartDate().toString(), "Tue Sep 13 15:00:00 CEST 2011");
	}
	
	@Test
	public void firstDateShouldBeEarlier() throws Exception {
		cal1.set(2010, 1, 1, 0, 0);
		cal2.set(2011, 1, 1, 0, 0);
		Date date1 = cal1.getTime();
		Date date2 = cal2.getTime();
		
		assertTrue(date1.getTime() < date2.getTime());
		assertTrue(myCalendar.isEarlier(date1, date2));
		assertFalse(myCalendar.isEarlier(date2, date1));
	}
	
	@Test
	public void firstDateIsEarlierButOnSameDay() throws Exception {
		cal1.set(2011, 1, 1, 10, 0);
		cal2.set(2011, 1, 1, 0, 0);
		Date date1 = cal1.getTime();
		Date date2 = cal2.getTime();
		
		assertTrue(date1.getTime() > date2.getTime());
		assertFalse(myCalendar.isEarlier(date1, date2));
		assertFalse(myCalendar.isEarlier(date2, date1));
	}
	
}