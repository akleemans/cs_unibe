/* Programmierung 1 HS 2011 Aufgabe 4-1 
 * Adrianus Kleemans
 * Gino Torriani
 */

import java.io.InputStream;
import java.util.Arrays;

/**
 * Plays a perfect game of 'connect four' on a standard sized (WxH: 7x6) board.
 * <p>
 * IMPORTANT: The following helper classes and files are needed!
 * <p>
 * a) Classes for the simulation of the game:	<p>
 * 	- Simulation.java					<p>
 * 	- TransSimulation.java				<p>
 * 	- ConnectFourSimulation.java		<p>
 * <p>
 * b) Data set (12 kB) <p>
 * 	- connectfour.bin (file)			
 * <p><p>
 * Summary: The program uses (a compressed version of) the free available data set 
 * for a data set based game-play. Out of a data set with ~68k key positions,
 * all other situations can be accurately evaluated (through some transformations).
 * Out of a real situation occuring, the next (best) move can be deduced 
 * from evaluating the data set with the method above.
 * <p>
 * Data Set (from http://archive.ics.uci.edu/ml/datasets/Connect-4): for a detailed 
 * description see the data set description at the provided link. 
 * In short, each instance is represented by a set of consecutive signs, like:
 * 		b,b,...,b,x,o,b,b,b,b,x,o,x,o,x,o,b,...,b,win
 * with players 'x' and 'o' and blank 'b'. The outcome of the situation
 * is described at the end of the String. 
 * The data set is highly compressed and each instance (game situation) 
 * presents itself in a field of 48 bits.
 * <p>
 * Hierarchy: ConnectFourSimulation extends TransSimulation, which extends Simulation.
 * Simulation allows some basic features like making a move or go back one move and
 * some representation with the correct dimensions.
 * TransSimulation provides the basic calculations (transformations) and
 * ConnectFourSimulation handles concrete method calls like making a move (.move)
 * and the handling with the data set (as an inputstream).
 * <p>
 * Approach: The approach is strongly based on John Tromp's work (http://homepages.cwi.nl/~tromp/)
 * which is inspired by the rule-based approach taken by Victor Allis and his thesis 
 * (http://www.connectfour.net/Files/connect4.pdf), in which he describes how his 
 * program VICTOR plays a perfect game.
 * <p>
 * 'Perfect game' means here:
 * 	(1) If it can play first, it wins.
 * 	(2) In a tournament with a equal number of matches with turns taken alternatively, 
 * 		it will always either win the tournament or force a tie.
 * 	(3) If, due to an error of the opposite player, it is possible to win the game,
 * 		it will win the game.
 * 
 * @author Adrianus Kleemans
 * @date 18.11.2011
 *
 */
public class ComputerPlayerKleemansTorriani implements IPlayer {

	ConnectFourSimulation c4;
	private Token[][] myBoard = new Token[7][6];
	int round;

	public ComputerPlayerKleemansTorriani () {
		reset();
	}

	private Token token;

	private void reset() {
		// initialize the board
		for (Token[] column : myBoard) {
			Arrays.fill(column, Token.empty);
		}

		InputStream inputstream = null;
		inputstream = getClass().getResourceAsStream("connectfour.bin");

		c4 = new ConnectFourSimulation();
		c4.loadBook(inputstream);
		round = 0;
	}

	public int getNextColumn(Token[][] board){
		// if board empty, start new game
		boolean newGame = false;
		int changes = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (!board[i][j].equals(Token.empty)) {
					changes++;
				}
			}
		}
		
		if (changes <= 1) {
			// System.out.println("Starte (intern) neues Spiel...");
			reset();	
		}		

		// first call
		if (round == 0) {
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					if (!board[i][j].equals(Token.empty)) {
						// System.out.println("Konnte diesmal nicht afangen.");
						round = 1;
					}
				}
			}
		}
		// check which move has been made by other player
		if (round != 0) {
			int changedRow = -1; int changedCol = -1;
			boolean exit = false;
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					if (!myBoard[i][j].equals(board[i][j])) {
						changedCol = i; changedRow = j; 
						exit = true;
						break;
					}
				}
				if (exit) break;
			}

			myBoard[changedCol][changedRow] = board[changedCol][changedRow];

			// make opponent's move in simulation		
			c4.makemove(changedCol+1);
		}

		// get best move and add in simulation
		int bestPossibleMove = c4.move()-1;
		c4.makemove(bestPossibleMove+1);
		round++;

		// add move on own board
		for (int r = 0; r < 6; r++) {
			if (myBoard[bestPossibleMove][r].equals(Token.empty)) {
				myBoard[bestPossibleMove][r] = getToken();
				break;
			}
		}

		return bestPossibleMove;
	}

	public void setToken(Token token){
		this.token = token;
	}

	public Token getToken(){
		return this.token;
	}

	public String getProgrammers(){
		return "Adrianus Kleemans, Gino Torriani";
	}
}
