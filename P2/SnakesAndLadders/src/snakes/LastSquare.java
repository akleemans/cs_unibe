package snakes;


/**
 * A LastSquare object has to know that it is the last square
 * of the game and shares this information.
 * 
 * @author Adrian Akleemans & Rafael Breu
 *
 */
public class LastSquare extends Square {

	public LastSquare(Game game, int position) {
		super(game, position);
	}

	@Override
	public boolean isLastSquare() {
		return true;
	}
}
