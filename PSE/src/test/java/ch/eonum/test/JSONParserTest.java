package ch.eonum.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.eonum.JSONParser;
import ch.eonum.Logger;
import ch.eonum.MedicalLocation;
import ch.eonum.Mode;

/* Unit Test */
public class JSONParserTest
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
	public final void testDeserialize()
	{
		String content = "{ \"status\": \"OK\",\"results\": [ { \"name\": \"Hans Muster\","
			+ "\"address\": \"Bahnhofstrasse, 3000 Bern\", \"email\": \"\", \"tel\": \"\","
			+ "\"types\": [ \"allgemeinaerzte\" ], \"location\": { \"lat\": 46.12345, \"lng\": 7.54321 } } ] } ";

		JSONParser parser = new JSONParser();
		MedicalLocation[] locations = parser.deserializeLocations(content);

		assertNotNull(locations);
		MedicalLocation testLocation = locations[0];

		assertNotNull(testLocation);
		assertEquals("Hans Muster", testLocation.getName());
		assertEquals("Bahnhofstrasse, 3000 Bern", testLocation.getAddress());
		assertEquals("", testLocation.getEmail());
	}

	@Test
	public final void testDeserializeWithEmail()
	{
		String content = "{ \"status\": \"OK\",\"results\": [ { \"name\": \"Ernst Hasli\","
			+ "\"address\": \"Kleines Gässli 5, 1234 Beispielsdorf\", \"email\": \"ernst_58@hotmail.com\",  \"tel\": \"\","
			+ "\"types\": [ \"allergologen\" ], \"location\": { \"lat\": 45.1, \"lng\": 7.61 } } ] } ";

		JSONParser parser = new JSONParser();
		MedicalLocation[] locations = parser.deserializeLocations(content);

		assertNotNull(locations);
		MedicalLocation testLocation = locations[0];

		assertNotNull(testLocation);
		assertEquals("Ernst Hasli", testLocation.getName());
		assertEquals("Kleines Gässli 5, 1234 Beispielsdorf", testLocation.getAddress());
		assertEquals("ernst_58@hotmail.com", testLocation.getEmail());
	}
	
	@Test
	public final void testDeserializeWithTelephone()
	{
		String content = "{ \"status\": \"OK\",\"results\": [ { \"name\": \"Hans Mueller\","
			+ "\"address\": \"First Street, New York\", \"email\": \"hansi@web.de\",  \"tel\": \"031 322 22 22\","
			+ "\"types\": [ \"urologen\" ], \"location\": { \"lat\": 43.2, \"lng\": 7.56 } } ] } ";

		JSONParser parser = new JSONParser();
		MedicalLocation[] locations = parser.deserializeLocations(content);

		assertNotNull(locations);
		MedicalLocation testLocation = locations[0];

		assertNotNull(testLocation);
		assertEquals("Hans Mueller", testLocation.getName());
		assertEquals("First Street, New York", testLocation.getAddress());
		assertEquals("hansi@web.de", testLocation.getEmail());
		assertEquals("031 322 22 22", testLocation.getTelephone());
	}
	
	@Test
	public final void testDeserializeMultipleCategories()
	{
		String content = "{ \"status\": \"OK\",\"results\": [ { \"name\": \"Bruce Wayne\","
			+ "\"address\": \"Gotham City\", \"email\": \"batman@akauk.com\",  \"tel\": \"\","
			+ "\"types\": [ \"akupunkturaerzte, anaesthesisten, chirurgen\" ], \"location\": { \"lat\": 40.7129, \"lng\": -74.006 } } ] } ";
		
		JSONParser parser = new JSONParser();
		MedicalLocation[] locations = parser.deserializeLocations(content);

		assertNotNull(locations);
		MedicalLocation testLocation = locations[0];

		assertNotNull(testLocation);
		assertEquals("Bruce Wayne", testLocation.getName());
		assertEquals("Gotham City", testLocation.getAddress());
		assertEquals("batman@akauk.com", testLocation.getEmail());
	}
}
