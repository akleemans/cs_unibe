package snakes;

/**
 * Every Square object must know its own position and
 * send players home if there is not enough space to 
 * land on it - It therefore has also always to know
 * how many and which players are on it.
 * It shares its position and the information if there
 * is already a player on it and if there is space for
 * more players. Additionally it provides the Game class
 * with a graphical representation of itself.
 * 
 * @author Adrian Akleemans & Rafael Breu
 *
 */

public class Square implements ISquare {

	protected int position;
	protected Game game;
	private IPlayer player;

	private boolean invariant() {
		return game != null
			&& game.isValidPosition(position);
	}

	public Square(Game game, int position) {
		this.game = game;
		this.position = position;
		assert invariant();
	}

	public int position() {
		return this.position;
	}

	public ISquare moveAndLand(int moves) {
		assert moves >= 0;
		return game.findSquare(position, moves).landHereOrGoHome();
	}

	protected ISquare nextSquare() {
		return game.getSquare(position+1);
	}

	protected ISquare previousSquare() {
		return game.getSquare(position-1);
	}

	public ISquare landHereOrGoHome() {
		return this.isOccupied() ? game.firstSquare() : this;
	}

	public String toString() {
		return "[" + this.squareLabel() + this.player() + "]";
	}
	
	protected String squareLabel() {
		return Integer.toString(position);
	}
	
	public boolean isOccupied() {
		return player != null;
	}

	public void enter(Player player) {
		assert this.player == null;
		this.player = player;
	}

	public void leave(IPlayer player) {
		assert this.player == player;
		this.player = null;
	}

	public boolean isFirstSquare() {
		return false;
	}

	public boolean isLastSquare() {
		return false;
	}

	protected String player() {
		return this.isOccupied() ? ("<" + this.player + ">") : "";
	}
}
