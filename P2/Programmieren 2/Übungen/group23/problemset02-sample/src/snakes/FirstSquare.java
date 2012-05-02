package snakes;

public class FirstSquare extends LargeSquare {

	public FirstSquare(Game game, int position) {
		super(game, position);
	}
	
	@Override
	protected String player() {
		StringBuffer buffer = new StringBuffer();
		for (Player player : players) {
			buffer.append("<" + player + ">");
		}
		return buffer.toString();
	}

}
