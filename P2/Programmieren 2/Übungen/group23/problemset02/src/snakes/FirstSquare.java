package snakes;

import java.util.ArrayList;
import java.util.List;

/**
 * A FirstSquare object is responsible to register
 * if a player enters or leaves this square.
 * It shares the information if it is occupied and
 * that it is the first square.
 * 
 * @author Adrian Kleemans & Rafael Breu
 *
 */

public class FirstSquare extends Square {

	private List<Player> players;

	public FirstSquare(Game game, int position) {
		super(game, position);
		players = new ArrayList<Player>();
	}

	public ISquare landHereOrGoHome() {
		return this;
	}

	@Override
	public boolean isOccupied() {
		return !players.isEmpty();
	}

	@Override
	public void enter(Player player) {
		assert !players.contains(player);
		players.add(player);
	}

	@Override
	public void leave(IPlayer player) {
		assert players.contains(player);
		players.remove(player);
	}

	@Override
	public boolean isFirstSquare() {
		return true;
	}

	@Override
	protected String player() {
		StringBuffer buffer = new StringBuffer();
		for (IPlayer player : players) {
			buffer.append("<" + player + ">");
		}
		return buffer.toString();
	}
}
