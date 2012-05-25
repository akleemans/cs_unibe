package snakes;

/**
 * A Snake object needs to know its own position and sends players that
 * try to land on this square to the square (this.position + transport).
 * It shares, as any type of square, information about its position on
 * the board.
 * 
 * @author Adrian Akleemans & Rafael Breu
 *
 */

public class Snake extends Ladder {

	public Snake(int transport, Game game, int position) {
		super(transport, game, position);
	}

	@Override
	protected String squareLabel() {
		return this.destination().position() + "<-" + position;
	}

}
