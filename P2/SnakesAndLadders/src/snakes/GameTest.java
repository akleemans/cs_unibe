package snakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class GameTest {
    Mockery context = new JUnit4Mockery();  
	private IPlayer bart;
	private IPlayer lisa;
	private IPlayer mockPlayer;
	
	private Game setUpGame() {
		bart = new Player("Bart");
		lisa = new Player("Lisa");
		IPlayer[] args = { bart, lisa };
		Game game = new Game(12, args);
		game.setSquareToLadder(2, 4);
		game.setSquareToLadder(7, 2);
		game.setSquareToSnake(11, -6);
		game.setSquareToLargeSquare(4);
		game.setSquareToLargeSquare(3);
		game.setSquareToTrapDoor(8, -5);
		return game;
	}
    
    @Test 
    public void theDieWasThrown() {
        final IDie die = context.mock(IDie.class);
		Game game = setUpGame();
		
		final int VALUE = 3;
		
        // expectations
        context.checking(new Expectations() {{
        	atLeast(1).of(die).roll(); will(returnValue(VALUE));
        }});

        // execute and passing the mock object
        game.play(die);
        
		assertTrue(game.isOver());
		assertEquals(12, bart.position());
		assertEquals(1, lisa.position());
		assertEquals(lisa, game.currentPlayer());
		assertEquals(bart, game.winner());
    }
    
    @Test
    public void someSquareTest() {
    	mockPlayer = context.mock(IPlayer.class);
		//set up game manually with a mock player
    	bart = new Player("Bart");
    	lisa = new Player("Lisa");
		IPlayer[] args = { bart, lisa, mockPlayer }; 
		/*
		 * AK this test does not work: At this point, the
		 * mockplayer is not allowed to do anything, including
		 * joining the game, thus the failure. To fix this,
		 * you can just move the expectation up before the
		 * declaration. Then fix stuff by adding allowed 
		 * methods as they keep throwing AsserionErrors.
		 * 
		 * Besides that it is a pretty good test!
		 */
		Game game = new Game(12, args);
		
        // expectations
        context.checking(new Expectations() {{
			allowing(mockPlayer).joinGame(with(any(Game.class)));
			/*
			 * AK this says "the method Player#wins must not be
			 * called more often than one time." But it is called 
			 * every turn to check if the player won. Obviously
			 * the test must fail. On the other hand, actually
			 * playing with a mocked player is quite exhausting,
			 * you might want to make him declare himself to be 
			 * the winner right away.
			 */
        	atMost(1).of(mockPlayer).wins();
			atLeast(1).of(mockPlayer).position();
        }});
    	
    	game.movePlayer(4);
    	Die die = new Die();
    	game.play(die);
    }
	
}
