/* Programmierung 1 HS 2011 Aufgabe 4-1 
 * Adrianus Kleemans [07-111-693]
 * Gino Torriani [06-128-714]
 */

import java.util.Arrays;
import java.util.Scanner;

public class VierGewinnt {
	
	public static final int COLS = 7;
	public static final int ROWS = 6;
	
	// Enum for the different tokens (empty, O, X)
	public enum Token { 
		empty(" "),
		player1("O"),
		player2("X");
		private String representation; // string representation of value
		Token(String s) { this.representation = s; }
		public String toString() { return this.representation; }
	}
	
	private Token[][] board = new Token[COLS][ROWS]; // 7 columns with 6 fields each
	private IPlayer[] players = new IPlayer[2]; // two players
	
	/** initialize board and players and start the game */
	public void play() {
		// initialize the board
		for (Token[] column : this.board) {
			Arrays.fill(column, Token.empty);
		}
		// initialize players
		players[0] = new HumanPlayer();
		System.out.print("Play against a human opponent? (y / n) ");
		String opponent = new Scanner(System.in).nextLine();
		if(opponent.toLowerCase().equals("y")){
			players[1] = new HumanPlayer();
		}else{
			players[1] = new ComputerPlayer();
		}
		players[0].setToken(Token.player1);
		players[1].setToken(Token.player2);
		// play...
		boolean solved = false;
		int currentPlayer = (new java.util.Random()).nextInt(2);  //choose randomly who begins
		System.out.println("current player: "+currentPlayer);
		int insertCol, insertRow; // starting from 0
		while (!solved && !this.isBoardFull()){
			// get player's next "move"
			insertCol = players[currentPlayer].getNextColumn(this.board);
			// insert the token and get the row where it landed
			insertRow = this.insertToken(insertCol, players[currentPlayer].getToken());
			// check if the game is over
			solved = this.checkVierGewinnt(insertCol, insertRow);
			//switch to other player
			if (!solved) currentPlayer = (currentPlayer+1) % 2;
		}
		System.out.println(displayBoard(this.board));
		if (solved) System.out.println("Player "+players[currentPlayer].getToken() + " wins!");
		else System.out.println("Draw! Game over.");
	}
	
	
	/** Inserts the token at the specified column (if possible)
	    and returns the row where the token landed */
	private int insertToken(int column, Token tok){
		int row = 0;

		// if column is full: exit
		if (!board[column][5].equals(Token.empty)) {
			System.out.println("Jemand hat versucht, col = "+column+" zu füllen, dabei ist sie voll!");
			System.exit(1);
		}
		// else: fill most upper row
		for (int r = 0; r < 6; r++) {
			if (board[column][r].equals(Token.empty)) {
				board[column][r] = tok;
				row = r;
				break;
			}
		}		

		return row;
	}
	
	/** Checks if every position is occupied */
	private boolean isBoardFull() {
		int col;
		for (col=0; col<7; col++) {
			if (board[col][5].equals(Token.empty)) {
				return false;
			}
		}
		return true;
	}
	
	/** Checks for at least four equal tokens in a row in either direction,
	    starting from the given position. */
	private boolean checkVierGewinnt(int col, int row){
		String p1 = Token.player1.toString();
		p1 = p1 + p1 + p1 + p1;
		String p2 = Token.player2.toString();
		p2 = p2 + p2 + p2 + p2;
		String str;
		
		// check row
		str = "";
		for (int i = 0; i < 7; i++) 
			str += board[i][row];
		if (str.contains(p1) || str.contains(p2)) return true;
		
		// check rol
		str = "";
		for (int i = 0; i < 6; i++) 
			str += board[col][i];
		if (str.contains(p1) || str.contains(p2)) return true;
		
		// diagonal 1
		str = "";
		int startCol = col;
		int startRow = row;
		
		for (int i = 0; i < 6; i++) {
			startCol--; startRow--;
			if (startCol < 0 || startRow < 0) {
				startCol++; startRow++;
				break;
			}
		}
		
		for (int i = 0; i < 6; i++) 
			if (startCol+i >= 0 && startCol+i < 7 && startRow+i >= 0 && startRow+i < 6)
				str += board[startCol+i][startRow+i];
		if (str.contains(p1) || str.contains(p2)) return true;
		
		// diagonal 2
		str = "";
		startCol = col;
		startRow = row;
		
		for (int i = 0; i < 6; i++) {
			startCol--; startRow++;
			if (startCol < 0 || startRow > 5) {
				startCol++; startRow--;
				break;
			}
		}
		
		for (int i = 0; i < 6; i++) 
			if (startCol+i >= 0 && startCol+i < 7 && startRow-i >= 0 && startRow-i < 6)
				str += board[startCol+i][startRow-i];
		if (str.contains(p1) || str.contains(p2)) return true;
		
		return false;
	}

	/** returns a graphical representation of the board */
	public static String displayBoard(Token[][] myBoard){
		String rowDelimiter = "+";
		String rowNumbering = " ";
		for(int col=0; col<myBoard.length; col++){
			rowDelimiter += "---+";
			rowNumbering += " "+(col+1)+"  ";
		}
		rowDelimiter += "\n";
		
		String rowStr;
		String presentation = rowDelimiter;
		for(int row=myBoard[0].length-1; row >= 0; row--){
			rowStr = "| ";
			for(int col=0; col < myBoard.length; col++){
				rowStr += myBoard[col][row].toString() + " | ";
			}
			presentation += rowStr + "\n" + rowDelimiter;
		}
		presentation += rowNumbering;
		return presentation;
	}
	
	
	
	/** main method, starts the program */
	public static void main(String args[]){
		VierGewinnt game = new VierGewinnt();
		game.play();
	}
}