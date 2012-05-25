package ursuppe;

import static org.junit.Assert.*;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import ursuppe.Game.Color;

@RunWith(JExample.class)
public class BoardTest {
	
	private Player[] testPlayers = new Player[3];
	
	@Test
	public Board newBoardShouldBeCreated() {
		
		Player player1 = new Player("TestPlayername1");
		Player player2 = new Player("TestPlayername2");
		Player player3 = new Player("TestPlayername3");
		
		testPlayers[0] = player1; testPlayers[1] = player2; testPlayers[2] = player3;
		testPlayers[0].chooseColor(Color.RED); testPlayers[1].chooseColor(Color.GREEN); testPlayers[2].chooseColor(Color.YELLOW);
		
		Board testBoard = new Board(testPlayers);
		
		return testBoard;
	}
		
		
	@Given("newBoardShouldBeCreated")
	public Board addAmebaToSquareShouldWorkProperly(Board testBoard){
		
		testBoard.addAmebaToSquare(3, 4, Game.Color.RED);
		assertEquals(testBoard.getPositionOfAmebaOfColorAndNumber(Game.Color.RED, 1), "3 4");
		assertEquals(testBoard.getPositionOfAmebaOfColorAndNumber(Game.Color.RED, 2), "empty");
		
		return testBoard;
	}
	
	
	@Given("newBoardShouldBeCreated")
	public Board shouldAddFoodCubesToSquare(Board testBoard){
		
		testBoard.addExcretedFoodCubesToSquare(Game.Color.RED, 1, 1);
		testBoard.addExcretedFoodCubesToSquare(Game.Color.YELLOW, 1, 2);
		testBoard.addExcretedFoodCubesToSquare(Game.Color.GREEN, 1, 3);
		
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.RED, 1, 1), 4);
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.RED, 1, 2), 2);
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.RED, 1, 4), 2);
		
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.YELLOW, 1, 2), 4);
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.YELLOW, 1, 1), 2);
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.YELLOW, 1, 4), 2);
		
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.GREEN, 1, 3), 4);
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.GREEN, 1, 2), 2);
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.GREEN, 1, 4), 2);
		
		return testBoard;
	}
	
	@Given("addAmebaToSquareShouldWorkProperly")
	public Board amoebasWithTooManyDamagePointsShouldDie(Board testBoard) {
		
		assertEquals(testBoard.getPositionOfAmebaOfColorAndNumber(Game.Color.RED, 1), "3 4"); 
		testBoard.addDamagePoint(Game.Color.RED,1,3,4);
		testBoard.addDamagePoint(Game.Color.RED,1,3,4);
		
		testBoard.orderAmoebasToDieIfTooMuchDamage(testPlayers);
		assertEquals(testBoard.getPositionOfAmebaOfColorAndNumber(Game.Color.RED, 1), "empty"); 
		
		return testBoard;
	}
	
	@Given("amoebasWithTooManyDamagePointsShouldDie")
	public Board deathAmoebasShouldBeReplaced(Board testBoard) {
		
		assertTrue(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.RED, 3, 4) == 4);
		assertTrue(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.YELLOW, 3, 4) == 4);
		assertTrue(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.GREEN, 3, 4) == 4);
		
		return testBoard;
	}
	
	@Given("addAmebaToSquareShouldWorkProperly")
	public Board obstacleOrSameAmebaShouldBeOnField(Board testBoard) {
		
		//Checking all the non-game fields:
		assertTrue(testBoard.obstacleOrSameAmebaOnField(0, 0, Game.Color.RED));
		assertTrue(testBoard.obstacleOrSameAmebaOnField(0, 2, Game.Color.RED));
		assertTrue(testBoard.obstacleOrSameAmebaOnField(0, 3, Game.Color.RED));
		assertTrue(testBoard.obstacleOrSameAmebaOnField(0, 4, Game.Color.RED));
		assertTrue(testBoard.obstacleOrSameAmebaOnField(2, 2, Game.Color.RED));
		assertTrue(testBoard.obstacleOrSameAmebaOnField(4, 4, Game.Color.RED));
		
		//Checking fields outside the game (non existing fields though):
		assertTrue(testBoard.obstacleOrSameAmebaOnField(0, 5, Game.Color.RED));
		assertTrue(testBoard.obstacleOrSameAmebaOnField(-1, -1, Game.Color.RED));
		assertTrue(testBoard.obstacleOrSameAmebaOnField(5, 1, Game.Color.RED));
		
		//Checking field with already a red amoeba on it
		
		assertTrue(testBoard.obstacleOrSameAmebaOnField(3, 4, Game.Color.RED));
		
		//Checking that else the the obstacleOrSameAmebaOnField boolean expression is false
		
		assertFalse(testBoard.obstacleOrSameAmebaOnField(2, 4, Game.Color.RED));
		assertFalse(testBoard.obstacleOrSameAmebaOnField(1, 3, Game.Color.RED));
		assertFalse(testBoard.obstacleOrSameAmebaOnField(3, 3, Game.Color.RED));
		
		return testBoard;
	}
	
	@Given("amoebasWithTooManyDamagePointsShouldDie")
	public Board shouldRemoveEatenFoodCubes(Board testBoard) {
		
		testBoard.removeEatenFoodCubes("red red green", 3, 4);
		
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.RED, 3, 4), 2);
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.GREEN, 3, 4), 3);
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.YELLOW, 3, 4), 4);
		
		testBoard.removeEatenFoodCubes("yellow yellow green", 3, 4);
		
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.RED, 3, 4), 2);
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.GREEN, 3, 4), 2);
		assertEquals(testBoard.getNbrOfFoodCubesOnASquare(Game.Color.YELLOW, 3, 4), 2);
		
		return testBoard;
	}
	
	@Given("addAmebaToSquareShouldWorkProperly")
	public Board squareShouldBeUnoccupiedTest(Board testBoard) {
		
		assertTrue(testBoard.squareIsUnoccuppied(1, 1));
		assertTrue(testBoard.squareIsUnoccuppied(4, 2));
		assertFalse(testBoard.squareIsUnoccuppied(3, 4));
		
		return testBoard;
	}
	
	@Given("newBoardShouldBeCreated")
	public Board squareShouldBeInGameSquare(Board testBoard) {
		
		assertTrue(testBoard.squareIsInGameSquare(1, 0));
		assertTrue(testBoard.squareIsInGameSquare(4, 2));
		assertFalse(testBoard.squareIsInGameSquare(2, 2));
		
		return testBoard;
	}
	
	@Given("newBoardShouldBeCreated")
	public Board intensiveScoringShouldBeCalculatedRight(Board testBoard){
		
		assertEquals(testBoard.getPlayers()[0].getName(),"TestPlayername1");
		assertEquals(testBoard.getPlayers()[1].getName(),"TestPlayername2");
		assertEquals(testBoard.getPlayers()[2].getName(),"TestPlayername3");
		
		testBoard.getPlayers()[0].addToPlayerMarker(3);
		testBoard.getPlayers()[1].addToPlayerMarker(2);
		testBoard.getPlayers()[2].addToPlayerMarker(1);
		
		assertEquals(testBoard.getPlayers()[0].getPlayerMarker(),3);
		assertEquals(testBoard.getPlayers()[1].getPlayerMarker(),2);
		assertEquals(testBoard.getPlayers()[2].getPlayerMarker(),1);
		
		testBoard.addAmebaToSquare(3, 4, Game.Color.GREEN);
		testBoard.addAmebaToSquare(3, 4, Game.Color.GREEN);
		testBoard.addAmebaToSquare(3, 4, Game.Color.GREEN);
		
		testBoard.scoring();
		
		assertEquals(testBoard.getPlayers()[0].getName(),"TestPlayername1");
		assertEquals(testBoard.getPlayers()[1].getName(),"TestPlayername2");
		assertEquals(testBoard.getPlayers()[2].getName(),"TestPlayername3");
		
		assertEquals(testBoard.getPlayers()[0].getPlayerMarker(),3);
		assertEquals(testBoard.getPlayers()[1].getPlayerMarker(),4);
		assertEquals(testBoard.getPlayers()[2].getPlayerMarker(),1);
		
		testBoard.addAmebaToSquare(1, 1, Game.Color.YELLOW);
		testBoard.addAmebaToSquare(1, 1, Game.Color.YELLOW);
		testBoard.addAmebaToSquare(1, 1, Game.Color.YELLOW);
		
		testBoard.scoring();
		
		assertEquals(testBoard.getPlayers()[0].getPlayerMarker(),3);
		assertEquals(testBoard.getPlayers()[1].getPlayerMarker(),5);
		assertEquals(testBoard.getPlayers()[2].getPlayerMarker(),2);
		
		testBoard.scoring();
		
		assertEquals(testBoard.getPlayers()[0].getPlayerMarker(),3);
		assertEquals(testBoard.getPlayers()[1].getPlayerMarker(),6);
		assertEquals(testBoard.getPlayers()[2].getPlayerMarker(),4);
		
		return testBoard;
	}
	
	@Given("intensiveScoringShouldBeCalculatedRight")
	public Board playerThreeShouldWinTheGame(Board testBoard){
		
		Player[] testPlayers = testBoard.getPlayers();
		
		assertTrue(testBoard.winner(1)==null);
		assertTrue(testBoard.winner(2)==null);
		assertTrue(testBoard.winner(3)==null);
		
		assertTrue(testBoard.winner(10)!=null);
		
		testPlayers[2].addToPlayerMarker(50);
		
		assertTrue(testBoard.winner(0).getName().equals("TestPlayername3"));
		
		return testBoard;
	}
	
	
	
	@Given("newBoardShouldBeCreated")
	public Board nbrOfAmoebasOnBoardTest(Board testBoard){
		assertEquals(testBoard.getNbrOfAmoebasOfAColorOnBoard(Game.Color.RED),0);
		assertEquals(testBoard.getNbrOfAmoebasOfAColorOnBoard(Game.Color.GREEN),0);
		assertEquals(testBoard.getNbrOfAmoebasOfAColorOnBoard(Game.Color.YELLOW),0);
		
		testBoard.addAmebaToSquare(3, 4, Game.Color.GREEN);
		testBoard.addAmebaToSquare(3, 4, Game.Color.GREEN);
		testBoard.addAmebaToSquare(3, 4, Game.Color.YELLOW);
		
		assertEquals(testBoard.getNbrOfAmoebasOfAColorOnBoard(Game.Color.RED),0);
		assertEquals(testBoard.getNbrOfAmoebasOfAColorOnBoard(Game.Color.GREEN),2);
		assertEquals(testBoard.getNbrOfAmoebasOfAColorOnBoard(Game.Color.YELLOW),1);
		
		return testBoard;
	}	
}