package snakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ch.unibe.jexample.JExample;
import ch.unibe.jexample.Given;
import org.junit.Test;

import org.junit.runner.RunWith;

/*
 * AK Very good and complete test.
 */
@RunWith(JExample.class)
public class TrapDoorTest {
	
	private Player jack;
	private Player jill;

	@Test
	public Game newGame() {
		jack = new Player("Jack");
		jill = new Player("Jill");
		Player[] args = { jack, jill };
		Game game = new Game(6, args);
		game.setSquareToTrapDoor(4, -2);
		game.setSquareToLargeSquare(2);
		assertTrue(game.notOver());
		assertTrue(game.firstSquare().isOccupied());
		assertEquals(1, jack.position());
		assertEquals(1, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
	
	@Given("newGame")
	public Game jackStartsMoving(Game game) {
		game.movePlayer(1);
		assertTrue(game.notOver());
		assertEquals(2, jack.position());
		assertEquals(1, jill.position());
		assertEquals(jill, game.currentPlayer());
		return game;
	}
	
	@Given("jackStartsMoving")
	public Game jillStartsMoving(Game game) {
		game.movePlayer(2);
		assertTrue(game.notOver());
		assertEquals(2, jack.position());
		assertEquals(3, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
	
	@Given("jillStartsMoving")
	public Game jackToTrapDoor(Game game) {
		game.movePlayer(2);
		assertTrue(game.notOver());
		assertEquals(4, jack.position());
		assertEquals(3, jill.position());
		assertEquals(jill, game.currentPlayer());
		return game;
	}
	
	@Given("jackToTrapDoor")
	public Game jillTriggersTrapDoor(Game game) {
		game.movePlayer(1);
		assertTrue(game.notOver());
		assertEquals(2, jack.position());
		assertEquals(2, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
	
	@Given("jillTriggersTrapDoor")
	public Game jackMovesAgainToTrapDoor(Game game) {
		game.movePlayer(2);
		assertTrue(game.notOver());
		assertEquals(4, jack.position());
		assertEquals(2, jill.position());
		assertEquals(jill, game.currentPlayer());
		return game;
	}
	
	@Given("jackMovesAgainToTrapDoor")
	public Game jillMovesPastTrapDoor(Game game) {
		game.movePlayer(3);
		assertTrue(game.notOver());
		assertEquals(4, jack.position());
		assertEquals(5, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
	
	
	@Given("jillMovesPastTrapDoor")
	public Game jackWins(Game game) {
		game.movePlayer(2);
		assertTrue(game.isOver());
		assertEquals(6, jack.position());
		assertEquals(5, jill.position());
		assertEquals(jill, game.currentPlayer());
		assertEquals(jack, game.winner());
		return game;
	}
}
