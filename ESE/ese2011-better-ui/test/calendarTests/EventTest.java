package calendarTests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import models.Database;
import models.Event;
import models.MyCalendar;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class EventTest extends UnitTest {
	
	private Event event;

	@Before
	public void setUp() {

		User user = new User("Testname","1234");
		MyCalendar calendar = new MyCalendar("CalendarName", user);
		
		String startDate = "13.04.2045 13:00";
		String endDate = "15.02.2055 14:00";
		
		calendar.addEvent("EventName", startDate, endDate, false);
		this.event = calendar.getEvents(user).get(0);
	}

	@Test
	public void shouldReturnCorrectEventSpecification() {
		assertEquals(event.name,"EventName");
	}
	
}