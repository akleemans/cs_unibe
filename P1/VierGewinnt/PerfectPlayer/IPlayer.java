/*
 Programmierung 1 HS 2011
 Aufgabe 4-1
*/

/** Inferface for VierGewinnt players */
public interface IPlayer {
	
	/** returns the name(s) of the programmer(s) */
	public String getProgrammers();
	
	/** returns the next move (number of column, starting from 0) of the player */
	public int getNextColumn(VierGewinnt.Token[][] board);
	
	/** Assign the player a token (e.g. 'X' or 'O') */
	public void setToken(VierGewinnt.Token token);
	
	/** Return the token assigned to the player */
	public VierGewinnt.Token getToken();
}
