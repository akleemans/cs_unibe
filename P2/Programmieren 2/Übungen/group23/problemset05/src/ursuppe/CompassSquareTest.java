package ursuppe;

import static org.junit.Assert.*;
import ch.unibe.jexample.JExample;
import org.junit.Test;

import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class CompassSquareTest {
	
	@Test
	public void newCompassSquareShouldBeCreated() {
		CompassSquare testCompass = new CompassSquare();
		
		assertFalse(testCompass.isIngameSquare());
		assertTrue(testCompass.isUnoccupied());
	}

}
