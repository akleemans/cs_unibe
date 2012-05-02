package snakes;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ch.unibe.jexample.JExample;
import ch.unibe.jexample.Given;
import org.junit.Test;

import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class LargeSquareTest {
	
	private Player jack;
	private Player jill;

	@Test
	public Game newGame() {
		jack = new Player("Jack");
		jill = new Player("Jill");
		Player[] args = { jack, jill };
		Game game = new Game(12, args);
		game.setSquareToLargeSquare(2);
		game.setSquareToLargeSquare(4);
		game.setSquareToLargeSquare(6);
		assertTrue(game.notOver());
		assertTrue(game.firstSquare().isOccupied());
		assertEquals(1, jack.position());
		assertEquals(1, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
	
	@Given("newGame")
	public Game jackToLargeSquare(Game game) {
		game.movePlayer(1);
		assertTrue(game.notOver());
		assertEquals(2, jack.position());
		assertEquals(1, jill.position());
		assertEquals(jill, game.currentPlayer());
		return game;
	}
	
	@Given("jackToLargeSquare")
	public Game jillToLargeSquare(Game game) {
		game.movePlayer(1);
		assertTrue(game.notOver());
		assertEquals(2, jack.position());
		assertEquals(2, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
	
	@Given("jillToLargeSquare")
	public Game jackPassesLargeSquare(Game game) {
		game.movePlayer(3);
		assertTrue(game.notOver());
		assertEquals(5, jack.position());
		assertEquals(2, jill.position());
		assertEquals(jill, game.currentPlayer());
		return game;
	}
	
	@Given("jackPassesLargeSquare")
	public Game jillToLargeSquareAt6(Game game) {
		game.movePlayer(4);
		assertTrue(game.notOver());
		assertEquals(5, jack.position());
		assertEquals(6, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
	

	
}