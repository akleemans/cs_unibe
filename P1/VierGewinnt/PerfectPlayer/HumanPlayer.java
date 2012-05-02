/* Programmierung 1 HS 2011 Aufgabe 4-1 */

import java.util.Scanner;

/** A human player class */
public class HumanPlayer implements IPlayer {
	private Scanner scan = new Scanner(System.in);
	private VierGewinnt.Token token;
	
	public int getNextColumn(VierGewinnt.Token[][] board){
		System.out.println("\n"+VierGewinnt.displayBoard(board));
		int column = -1;
		while(column < 0 || column >= board.length){
			System.out.print("Player "+this.token.toString()
				+" choose a column between 1 and "+board.length+": ");
			column = Integer.parseInt(scan.nextLine()) - 1;
			if(column >= 0 && column < board.length){
				int topRow = board[0].length-1;
				if(isColFull(column, board)){
					System.out.println("This column is full!");
					column = -1;
				}
			}
		}
		return column;
	}

	/** returns true if the column col is already full and false otherwise. */
	private boolean isColFull(int col, VierGewinnt.Token[][] board){
		int topRow = board[0].length-1;
		if(board[col][topRow] != VierGewinnt.Token.empty){
			return true;
		}else{
			return false;
		}
	}
	
	public void setToken(VierGewinnt.Token token){
		this.token = token;
	}
	public VierGewinnt.Token getToken(){
		return this.token;
	}
	
	public String getProgrammers(){
		return "Human";
	}
}
