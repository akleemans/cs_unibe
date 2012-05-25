package snakes;

/**
 * A Player object has to know its position and shares this information.
 * A Player object is further responsible to move as many
 * moves as ordered by the Die object or a TrapDoor object. 
 * 
 * @author Adrian Akleemans & Rafael Breu
 *
 */

public class Player implements IPlayer {
	
	private String name;
	private ISquare square;

	private boolean invariant() {
		return name != null
			&& square != null;
	}

	public Player(String name) {
		this.name = name;
		// invariant holds only after joining a game
	}

	/* (non-Javadoc)
	 * @see snakes.IPlayer#joinGame(snakes.Game)
	 */
	public void joinGame(Game game) {
		square = game.firstSquare();
		square.enter(this);	
		assert invariant();
	}

	/* (non-Javadoc)
	 * @see snakes.IPlayer#position()
	 */
	public int position() {
		assert invariant();
		return square.position();
	}

	/* (non-Javadoc)
	 * @see snakes.IPlayer#moveForward(int)
	 */
	public void moveForward(int moves) {
		assert moves > 0;
		square.leave(this);
		square = square.moveAndLand(moves);
		square.enter(this);
	}
	
	/*
	 * AK well done, but this method is more general than it name implies:
	 * it moves the player to an arbitrary square, how about the name 
	 * Player#moveTo(destination) ?
	 */
	
	/* AKL Done by RB
	 */
	/* (non-Javadoc)
	 * @see snakes.IPlayer#moveTo(snakes.ISquare)
	 */
	public void moveTo(ISquare destination) {
		square.leave(this);
		square = destination;
		square.enter(this);
	}
	
	public String toString() {
		return name;
	}

	/* (non-Javadoc)
	 * @see snakes.IPlayer#square()
	 */
	public ISquare square() {
		return square;
	}

	/* (non-Javadoc)
	 * @see snakes.IPlayer#wins()
	 */
	public boolean wins() {
		return square.isLastSquare();
	}

}
