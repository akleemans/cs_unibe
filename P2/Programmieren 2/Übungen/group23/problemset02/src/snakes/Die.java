package snakes;

/**
 * The Die Class is responsible for returning a number between 1 and 6.
 * @author  Adrianus Kleemans & Rafael Breu
 *
 */
public class Die implements IDie {
	static final int FACES = 6;
	
	/* (non-Javadoc)
	 * @see snakes.IDie#roll()
	 */
	public int roll() {
		int result = 1 + (int) (FACES * Math.random());
		assert result >= 1 && result <= FACES;
		return result;
	}
}
