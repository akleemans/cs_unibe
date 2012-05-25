package snakes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//import java.util.Queue;

/*
 * AK Your branch history is messed up, you'll need to fix it. We want you to 
 * make every commit on the master branch to be well-documented, i.e. having 
 * a good commit message. For example "point 5 done" is not enough, it would 
 * be better to make one bigger commit with more messages.
 * 		As this is a common problem, we wrote a blog post about this (and used your
 * 		branch history as example):
 * 			http://p2unibe.tumblr.com/post/3746733450/how-to-fix-your-branch-history
 * 
 * Also you'll need to redo your Javadoc, not just reformulating the method name,
 * but answering the questions about the interesting cases for the developer.
 * 
 * And your code shows some code smells:
 *  	* hard-coded numbers makes code harder to understand.
 *  	* public attributes are a topic for themselves. 
 *  I commented about that on the spot.
 */

/* AKL Javadoc, 'hard-coded numbers' and public attributes done (thanks RB)
 * TODO clean up branch history
 */

/**
 * The Game Class sets the squares at the beginning of the game and 
 * is responsible to display the actual game state during the game.
 * The Game Class finishes the game if one player is the winner.
 * It shares the information who the current player is and if the
 * game is still not over.
 * 
 */

public class Game {
	private List<ISquare> squares;
	private int size;
	private LinkedList<IPlayer> players;
	private IPlayer winner;
	
	private boolean invariant() {
		return squares.size() > 3
			&& size == squares.size()
			&& players.size() > 1;
	}

	public Game(int size, IPlayer[] args) {
		this.size = size;
		this.addSquares(size);
		this.addPlayers(args);
		assert invariant();
	}

	public boolean isValidPosition(int position) {
		return position>=1 && position<=size;
	}

	public static void main(String args[]) {
		(new SimpleGameTest()).newGame().play(new Die());
	}

	public void play(IDie die) {
		System.out.println("Initial state: " + this);
		while (this.notOver()) {
			int roll = die.roll();
			System.out.println(this.currentPlayer() + " rolls " + roll + ":  " + this);
			this.movePlayer(roll);
		}
		System.out.println("Final state:   " + this);
		System.out.println(this.winner() + " wins!");
	}

	public boolean notOver() {
		return winner == null;
	}

	public boolean isOver() {
		return !this.notOver();
	}

	public IPlayer currentPlayer() {
		return players.peek();
	}

	public void movePlayer(int roll) {
		assert roll>=1 && roll<=6;
		IPlayer currentPlayer = players.remove(); // from front of queue
		currentPlayer.moveForward(roll);
		players.add(currentPlayer); // to back of the queue
		if (currentPlayer.wins()) {
			winner = currentPlayer;
		}
		assert invariant();
	}

	public void setSquare(int position, ISquare square) {
		// Do not change the type of the first or last square!
		assert !this.getSquare(position).isLastSquare()
			&& !this.getSquare(position).isFirstSquare();
		this.initSquare(position, square);
		assert invariant();
	}

	public IPlayer winner() {
		return winner;
	}

	public ISquare firstSquare() {
		return squares.get(0);
	}

	public ISquare getSquare(int position) {
		assert this.isValidPosition(position);
		return squares.get(position - 1);
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (ISquare square : squares) {
			buffer.append(square.toString());
		}
		return buffer.toString();
	}

	private void addSquares(int size) {
		squares = new ArrayList<ISquare>();
		for(int i=0; i<size; i++) {
			Square square = new Square(this, i+1);
			squares.add(square);
		}
		this.initSquare(1, new FirstSquare(this, 1));
		this.initSquare(size, new LastSquare(this, size));
	}

	private void addPlayers(IPlayer[] args) {
		players = new LinkedList<IPlayer>();
		for (IPlayer player : args) {
			player.joinGame(this);
			players.add(player);
		}
	}

	private void initSquare(int position, ISquare square) {
		assert this.isValidPosition(position) && square != null;
		squares.set(position-1, square);
	}

	public void setSquareToLadder(int position, int transport) {
		this.setSquare(position, new Ladder(transport, this, position));
	}

	public void setSquareToSnake(int position, int transport) {
		this.setSquare(position, new Snake(transport, this, position));
	}
	
	public void setSquareToLargeSquare(int position) {
		this.setSquare(position, new LargeSquare(this, position));
	}

	public ISquare findSquare(int position, int moves) {
		assert position + moves <= 2*size - 1; // can't go more than size-1 moves backwards past end
		int target = position + moves;
		if (target > size) { // reverse direction if we go past the end
			target = size - (target - size);
		}
		return this.getSquare(target);
	}

	public void setSquareToTrapDoor(int position, int transport) {
		this.setSquare(position, new TrapDoor(transport, this, position));
		
	}

}
