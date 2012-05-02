package snakes;


/**
 * A square on a snakes and ladders game. Responsible for finding the 
 * <code>ISquare</code> that a {@link Player} will will ultimately be placed 
 * on if he steps on this square.
 * Makes sure that only one player at a time can occupy this field, 
 * and knows the {@link Player} that currently occupies it. 
 * This square also knows the fields ahead and behind it on the game, 
 * according to the standard flow of the game 
 * (not taking into account special fields like snakes and ladders), 
 * as well as its own position on the game board.
 */
public interface ISquare {
	/**
	 * Returns the position of this square on the {@link Game} board.
	 * @return the position of this square on the game board, counting starts at 1.
	 */
	public int position();
	

	
	/**
	 * Indicates whether this is the last square on the {@link Game} board. 
	 * The {@link Player} that first reaches the last square on the board wins. 
	 * @see ISquare#isFirstSquare()
	 * @return true if this is is the last square on the board; false otherwise.
	 */
	public boolean isLastSquare();
	
	/**
	 * Indicates whether this is the first square on the {@link Game} board. 
	 * All {@link Player}s' dice start the game on the first square. 
	 * @see ISquare#isLastSquare()
	 * @return true if this is is the last square on the board; false otherwise.
	 */
	public boolean isFirstSquare();
	
	/**
	 * Inform this square that <code>player</code> has entered the field. 
	 * If this square is already occupied, this may fail. If 
	 * <code>player</code> is null, the field is informed that it's empty.
	 * @see ISquare#leave
	 * @param player the player entering the field. May be null.
	 */
	public void enter(Player player);
	
	/**
	 * Informs the field that <code>player</code> is leaving this field. 
	 * Here, <code>player</code> may be null, thus making sure that
	 * the field is informed that it is empty. This method may fail, 
	 * but doesn't need to, if <code>player</code> is not currently on this square.
	 * @see ISquare#enter
	 * @param player the player entering the field. May be null.
	 */
	public void leave(Player player);
	
	/**
	 * Indicates whether any {@link Player} is currently on this square. 
	 * @return true if a {@link Player} is on this field; false otherwise.
	 */
	public boolean isOccupied();
	
	/**
	 * Returns the ISquare that a player will ultimately end upon, if he steps on this field.
	 * For example, if this field is a snake on position 3, pointing to 7, and 7 is empty, 
	 * then this method will return the <code>ISquare</code> on position 7.
	 * If this square is occupied, this method will return "home", the first field of the {@link Game}.
	 * @return the square that a player will end on, if he enters this field currently. Not null. 
	 */
	public ISquare landHereOrGoElsewhere();
	
	/**
	 * Returns the i<sup>th</sup> square ahead of this one on the game board,
	 * according to the regular game flow. Does not take into account special 
	 * special fields such as snakes and ladders. That is, if this square
	 * is a ladder on position 5 of the game, pointing to position 8, then 
	 * <code>this.moveAndLand(1)</code> will still return the square directly 
	 * after this one, namely the square on position 6. 
	 * @return the square on the i<sup>th</sup> square ahead of this one. Not null.
	 */
	public ISquare moveAndLand(int moves);

}
