package ch.eonum.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.eonum.City;
import ch.eonum.Logger;
import ch.eonum.Mode;

/* Unit Test */
public class CityTest
{
	private static Mode previousMode;

	@BeforeClass
	public static void setUpBeforeClass()
	{
		previousMode = Logger.mode;
		Logger.mode = Mode.TEST;
	}

	@AfterClass
	public static void tearDownAfterClass()
	{
		Logger.mode = previousMode;
	}

	@Test
	public final void testCity()
	{
		City Bern = new City("Bern", 46.123, 7.501);

		assertNotNull(Bern);
		assertEquals("Bern", Bern.getName());
		assertTrue(46.123 == Bern.getLocation()[0]);
		assertTrue(7.501 == Bern.getLocation()[1]);
	}
}
