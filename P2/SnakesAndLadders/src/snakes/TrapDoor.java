package snakes;

/**
 * 
 * A TrapDoor object knows its position and is responsible to know
 * how many and which players are on it to be able to determine
 * if there are enough players on it to trigger the trap door
 * mechanism that sends all the players to a connected LargeSquare.
 * It shares information about where on the board it lies and
 * about its special graphical representation.
 * 
 * @author Adrian Akleemans & Rafael Breu
 *
 */
public class TrapDoor extends LargeSquare implements ISquare {
	
	private int transport;
	public final int MAXPLAYER=2;
	
	public TrapDoor(int transport, Game game, int position) {
		super(game, position);
		this.transport = transport;
	}
	
	@Override
	public String toString() {
		return "[" + this.squareLabel() + this.player() + "]";
	}
	
	@Override
	protected String squareLabel() {
		return this.destination().position() + "<=" + position;
	}
	
	protected ISquare destination() {
		return game.getSquare(position+transport);
	}
	
	@Override
	public void enter(Player player){
		assert !this.getPlayers().contains(player);
		this.getPlayers().add(player);
		
		if (this.getPlayers().size() >= MAXPLAYER) {
			triggerTrapDoor();
		}
	}
	
	/*
	 * AK You hard coded the number of players on the Trapdoor. It works fine
	 * for NOW, but it would be better to solve it without the magic number.
	 */
	
	/* AKL Done
	 * 
	 */
	public void triggerTrapDoor() {
		int nbrPlayers = this.getPlayers().size();
		
		for(int i = 0; i<nbrPlayers; i++ )
			movePlayersOnTrapdoor(this.getPlayers().get(0));
	}
	
	/*
	 * AK well separated responsibilities, but TrapDoor#movePlayer(player)
	 * is a bit weird. You wouldn't want to make the `player` any player, but only
	 * a player on the Trapdoor.
	 */
	/* AKL function renamed.
	 * 
	 */
	public void movePlayersOnTrapdoor(IPlayer player) {
		player.moveTo(destination());		
	}
}

