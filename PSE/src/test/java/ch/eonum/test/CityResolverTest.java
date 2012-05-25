package ch.eonum.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.eonum.City;
import ch.eonum.CityResolver;
import ch.eonum.Logger;
import ch.eonum.Mode;

/* Unit Test */
public class CityResolverTest
{
	private static Mode previousMode;
	private CityResolver cityResolver = null;

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
	public final void testGetInstance()
	{
		this.cityResolver = null;
		this.cityResolver = CityResolver.getInstance();
		assertNotNull(this.cityResolver);
	}

	@Test
	public final void testGetCoordinates()
	{
		assertNull(this.cityResolver);
		this.cityResolver = CityResolver.getInstance();

		City city = this.cityResolver.getCity("Bern");
		assertEquals("Bern", city.getName());
		Double[] testValue = city.getLocation();
		assertNotNull(testValue);
		assertEquals(2, testValue.length);
		Double[] referenceValue = new Double[] {46.94809, 7.44744};
		assertEquals(referenceValue[0], testValue[0]);
		assertEquals(referenceValue[1], testValue[1]);
	}

	@Test
	public final void testGetAllCities()
	{
		this.cityResolver = CityResolver.getInstance();

		String[] allCities = this.cityResolver.getAllCities();
		for (String name : allCities)
		{
			assertNotNull(name);
			assertNotSame(0, name.length());
		}
	}

}
