package test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import calendar.Event;

public class EventTest {
	
	Event testEvent;
	Date testStartDate = new Date();
	Date testEndDate = new Date();
	
	Calendar firstDate = new GregorianCalendar(2011, Calendar.DECEMBER, 15);
	Calendar secondDate = new GregorianCalendar(2011, Calendar.DECEMBER, 25);
		
	@Before
	public void setUp() {
		
		
		testStartDate = firstDate.getTime();
		testEndDate = secondDate.getTime();
		
		testEvent = new Event(12, "Testname", "Testowner", testStartDate, testEndDate, false);
		
	}

	@Test
	public void shouldReturnCorrectEventname() {
		assertEquals(testEvent.getName(), "Testname");
	}
	
	@Test
	public void shouldReturnCorrectOwner() {
		assertEquals(testEvent.getOwner(), "Testowner");
	}
	
	@Test
	public void shouldReturnCorrectID() {
		assertEquals(testEvent.getID(), 12);
	}
	
	@Test
	public void shouldReturnCorrectStartDate() {
		assertEquals(testEvent.getStartDate(), firstDate.getTime());
	}
	
	@Test
	public void shouldReturnCorrectEndDate() {
		assertEquals(testEvent.getEndDate(), secondDate.getTime());
	}
	

}
