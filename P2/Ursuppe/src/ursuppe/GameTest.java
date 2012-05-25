package ursuppe;

import static org.junit.Assert.*;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import ursuppe.Game.Color;

@RunWith(JExample.class)
public class GameTest {
	
	@Test
	public Player[] newPlayersShouldBeInitialized() {
		
		Player[] playersInTestGame = new Player[3];
		
		Player Robert = new Player("Robert");
		Player Hannah = new Player("Gabi");
		Player NoName = new Player("Yohanna");
		
		playersInTestGame[0]=Robert; playersInTestGame[1]=Hannah; playersInTestGame[2]=NoName;
		playersInTestGame[0].chooseColor(Color.RED); playersInTestGame[1].chooseColor(Color.GREEN); playersInTestGame[2].chooseColor(Color.YELLOW);
		
		return playersInTestGame;
	}
	
	@Given("newPlayersShouldBeInitialized")
	public void newGameShouldBeInitialized(Player[] p) {
		Game game = new Game(p, false);
		assertEquals(game.getPlayers().length, 3);
	}

}


