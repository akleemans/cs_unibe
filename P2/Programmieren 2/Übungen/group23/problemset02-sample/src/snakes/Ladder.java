package snakes;

public class Ladder extends Square {

	protected int transport;

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
	public ISquare landHereOrGoElsewhere() {
		return this.destination().landHereOrGoElsewhere();
	}

	protected ISquare destination() {
		return game.getSquare(position+transport);
	}
}
