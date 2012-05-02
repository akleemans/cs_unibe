package ursuppe;

import static org.junit.Assert.*;
import ch.unibe.jexample.JExample;
import org.junit.Test;

import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class AmoebaStackTest {
	
	@Test
	public void redStackshouldBeCreated() {
		AmoebaStack testStack = new AmoebaStack(Game.Color.RED);
		
		assertFalse(testStack.getColor().equals(null));
		assertEquals(testStack.getColor(), Game.Color.RED);
	}

}