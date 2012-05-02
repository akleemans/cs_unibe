package ursuppe;

import static org.junit.Assert.*;
import ch.unibe.jexample.JExample;
import org.junit.Test;

import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class AmoebaTest {
	
	@Test
	public void newAmoebaShouldBeCreated() {
		Amoeba testAmoeba = new Amoeba(Game.Color.RED,5);
		
		assertFalse(testAmoeba.getColor().equals(null));
		assertEquals(testAmoeba.getColor(), Game.Color.RED);
		assertTrue(testAmoeba.getNumber() == 5);
		assertTrue(testAmoeba.getDamagePoints() == 0);
	}

}
