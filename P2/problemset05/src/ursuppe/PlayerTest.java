package ursuppe;

import static org.junit.Assert.*;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class PlayerTest {
	
	@Test
	public Player newPlayerShouldBeCreated() {
		Player player1 = new Player("player1");
		assertEquals(player1.getName(),"player1");
		return player1;
	}
	
	@Given("newPlayerShouldBeCreated")
	public Player playerChoosesColor(Player testPlayer) {
		testPlayer.chooseColor(Game.Color.RED);
		assertFalse(testPlayer.getColor().toString().equals("green"));
		assertEquals(testPlayer.getColor(), Game.Color.RED);
		assertTrue(testPlayer.getColor().toString().equals("red"));
		return testPlayer;
	}

}
