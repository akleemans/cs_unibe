package snakes;


public class TrapDoor extends Ladder implements ISquare {
	
	public TrapDoor(int transport, Game game, int position) {
		super(transport, game, position);
	}

	
	@Override
	protected String squareLabel() {
		return this.destination().position() + "~~~>" + position;
	}
	
	@Override
	public ISquare landHereOrGoElsewhere() {
		if(player == null) 
			return this;
		Player thePlayer = player; //Needed for post-condition
		
		player.placeOn(this.destination());
		assert thePlayer.position() == position+transport;
		assert player == null;
		return this.destination().landHereOrGoElsewhere();
	}
	
	protected ISquare destination() {
		return game.getSquare(position+transport);
	}	
}

