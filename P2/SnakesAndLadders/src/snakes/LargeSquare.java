package snakes;


import java.util.ArrayList;
import java.util.List;

/**
 * A Large Square object is responsible to always know itself how many and which
 * players are on this square, enter it or leave it.
 * It shares the information, if there is already a player on it,
 * and tells the other squares that there is always space for another
 * player on it.
 * 
 * @author Adrian Akleemans & Rafael Breu
 *
 */

public class LargeSquare extends Square implements ISquare {
	/*
	 * AK Avoid public attributes. Speaking in contracts, it is nearly
	 * impossible to fulfill yours if anybody can just replace your attributes.
	 * Consider the 
	 * class Evil {
	 * 		public void doEvilStuff(LargeSquare square) {
	 * 			square.players = new ArrayList<Player>();
	 * 		}
	 * }
	 * 
	 * Which deletes all players on the square, almost certainly wrecking your
	 * complete game. Don't ask, why anybody would implement something like that,
	 * chances are, you'll wreck your game yourself by accident. But if an 
	 * attribute is public, we can never be sure, if we fulfill our contract, as
	 * at any point, somebody could do something weird, without us knowing.
	 * 
	 * The best solution would be to use the enter and leave methods, where possible
	 * and giving a copied list with
	 * 
	 * public List<Player> getPlayers() {
	 * 			return new ArrayList(players);
	 * 	}
	 */
	
	/*
	 * AKL Done
	 */
	
	private List<Player> players;

	public LargeSquare(Game game, int position) {
		super(game, position);
		players = new ArrayList<Player>();
	}
	
	@Override
	public ISquare landHereOrGoHome() {
		return this;
	}
	
	@Override
	public boolean isOccupied(){
		return !players.isEmpty();
	}
	
	@Override
	public void enter(Player player){
		assert !players.contains(player);
		players.add(player);
	}
	
	@Override
	public void leave(IPlayer player){
		assert players.contains(player);
		players.remove(player);
	}
	
	@Override
	protected String player(){
		StringBuffer buffer = new StringBuffer();
		for (IPlayer player : players){
			buffer.append("<" + player + ">");
		}
		return buffer.toString();
		}
	
	@Override
	protected String squareLabel() {
		return Integer.toString(position);
	}
	
	@Override
	public String toString() {
		return "{" + this.squareLabel() + this.player() + "}";
	}
	
	public List<Player> getPlayers(){
		return players;
	}

}



