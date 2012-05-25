package ch.eonum.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.eonum.Logger;
import ch.eonum.Mode;
import ch.eonum.Timer;

/* Unit Test */
public class TimerTest
{
	Timer t;
	static Mode previousMode;

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

	@Before
	public void setUp()
	{
		this.t = new Timer();
	}

	@After
	public void tearDown()
	{
		this.t.reset();
	}

	@Test
	public final void testTimer()
	{
		double timeElapsed = t.timeElapsed();
		assertTrue(timeElapsed >= 0);
		assertTrue(timeElapsed < 1000);
	}

	@Test
	public final void testTimeDelta()
	{
		t.reset();
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			// Restore the interrupted status
			Thread.currentThread().interrupt();
		}
		double timeElapsed = t.timeElapsed();
		assertTrue(timeElapsed > 1990);
		assertTrue(timeElapsed < 2010);
	}

}
