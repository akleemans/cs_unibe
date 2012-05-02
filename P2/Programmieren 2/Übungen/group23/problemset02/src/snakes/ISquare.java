package snakes;

public interface ISquare {
	/* AK Here, a good comment would be
	 * "The position of the square on the board relative to the first square.
	 * @return an Integer i >= 1 and i <= the size of the board."
	 */
	/**
	 * The position of the square on the board relative to the first square.
	 * 
	 * @return an Integer i >= 1 and i <= the size of the board.
	 */
	public int position();
	/**
	 * Returns the square on which the player lands after his move temporarily. 
	 * If the player can stay on that square or has to go home is checked by 
	 * the {@link landHereOrGoHome} method of the target square itself.
	 * 
	 * @param moves an Integer i >=1 and i<= 6 counting the number of steps 
	 * (moves) the current player does
	
	 * @return the square on which the player lands after his move temporarily
	 */
	public ISquare moveAndLand(int moves);
	/*
	 * AK What's the implication of being the first square? What should a
	 * Square fulfill, if it answers this as `true`?
	 */
	/**
	 * Returns if this square is the first square on the board. That means 
	 * that's the square on which all players start the game. It thus can 
	 * contain more than one player.
	 * 
	 * @return Returns true, if this square is the first square on the 
	 * board, returns false otherwise
	 */
	public boolean isFirstSquare();
	/*
	 * AK Again: What is important about the last square?
	 */
	/**
	  * Returns if this square is the last square on the board. Landing 
	  * here means that player wins the game.
	 * 
	 * @return Returns true, if this square is the last square on the 
	 * board, returns false otherwise
	 */
	public boolean isLastSquare();
	
	/*
	 * Start with a simple sentence ending with a ".". What are the border cases?
	 * What happens if I add a player to a Square, that #isOccupied()?
	 */
	/**
	 * Adds a player to this square. That means: adds him to the players-
	 * List of this square, if there is space for more than one player on 
	 * this square, or assigns this player to the Square's "player"-variable.
	 * 
	 * @param player the player that enters this square
	 */
	public void enter(Player player);
	
	/**
	 * Removes Player player from this square (i.e. from the players-
	 * List of this square, if there is space for more than one 
	 * player on this square.)
	 * 
	 * @param player the player that is removed from this square
	 */
	public void leave(IPlayer player);
	
	/**
	 * Returns if this square is occupied
	 * 
	 * @return Returns true, if a player is on this square
	 */
	public boolean isOccupied();
	/*
	 * What about TrapDoors, Snakes, Ladders?
	 */
	/**
	 * Returns the square where the player lands after his move.
	 * Either there is enough space to land on this square, then 
	 * the player lands here, or there is not enough space, then 
	 * the player has to go home (that means to the first square).
	 * 
	 * If the player lands on special squares, that can hold 
	 * multiple players (that means TrapDoors and large Squares),
	 * the landHereOrGoHome() method always accepts the player 
	 * to land on this square temporarily. The player may be 
	 * sent to another squares from a TrapDoor, this is handled
	 * by the methods of this special square by themselves.
	 * 
	 * If the player wants to land on a Snake or a Ladder (squares
	 * that immediately send the player to another square), the
	 * landHereOrGoHome() method already calls the landHereOrGoHome()
	 * method of the target square and returns the 'answer' of this 
	 * method of the target square, if the player can land there or
	 * has to go home.
	 *
	 * 
	 * @return the square where the player lands after his turn
	 */
	public ISquare landHereOrGoHome();
}
