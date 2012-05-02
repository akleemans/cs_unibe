package ursuppe;

import static org.junit.Assert.*;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class FoodCubeTest {
	
	@Test
	public void newFoodCubeShouldBeCreated() {
		FoodCube greenCube = new FoodCube(Game.Color.GREEN);
		
		assertFalse(greenCube.getColor().equals(null));
		assertTrue(greenCube.getColor().toString().equals("green"));
	}
	
	@Test
	public void secondNewFoodCubeShouldBeCreated() {
		FoodCube greenCube = new FoodCube(Game.Color.RED);
		
		assertFalse(greenCube.getColor().equals(null));
		assertTrue(greenCube.getColor().toString().equals("red"));
	}

}
