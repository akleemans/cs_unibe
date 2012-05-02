package snakes;

import java.util.ArrayList;
import java.util.List;

public class LargeSquare extends Square {

	protected List<Player> players;

	public LargeSquare(Game game, int position) {
		super(game, position);
		players = new ArrayList<Player>();
	}

	public ISquare landHereOrGoElsewhere() {
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
	public void leave(Player player) {
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
		for (Player player : players) {
			buffer.append("{" + player + "}");
		}
		return buffer.toString();
	}
}
