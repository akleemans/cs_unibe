package snakes;

/**
 * A Ladder object is responsible to send every player that lands here
 * to the square with position (own position + transport). This square
 * is responsible itself to check whether there is enough space for the
 * player to land, the Ladder Square just sends the player there temporarily.
 * 
 * @author Adrian Akleemans & Rafael Breu
 *
 */

public class Ladder extends Square {

	private int transport;

	private boolean invariant() {
		return isValidTransport(transport);
	}

	private boolean isValidTransport(int transport) {
		return transport != 0 && game.isValidPosition(position + transport);
	}

	public Ladder(int transport, Game game, int position) {
		super(game, position);
		assert isValidTransport(transport);
		this.transport = transport;
		assert invariant();
	}
	
	@Override
	protected String squareLabel() {
		return position + "->" + this.destination().position();
	}

	@Override
	public ISquare landHereOrGoHome() {
		return this.destination().landHereOrGoHome();
	}

	protected ISquare destination() {
		return game.getSquare(position+transport);
	}
}
