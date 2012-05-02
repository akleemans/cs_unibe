import java.util.Arrays;
import java.util.Scanner;

public class VierGewinnt {
	
	// Enum for the different tokens (empty, O, X)
	public enum Token { 
		empty(" "),
		player1("O"),
		player2("X");
		private String representation; // string representation of value
		Token(String s) { this.representation = s; }
		public String toString() { return this.representation; }
	}
	
	private Token[][] board = new Token[7][6]; // 7 columns with 6 fields each
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
		if (opponent.toLowerCase().equals("y")) {
			players[1] = new HumanPlayer();
		} 
		else {
			players[1] = new ComputerPlayer();
		}
		players[0].setToken(Token.player1);
		players[1].setToken(Token.player2);
		// play...
		boolean solved = false;
		int currentPlayer = 0;
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
	private int insertToken(int column, Token tok) {
		
		int dropRow;
		for (dropRow = 0; dropRow < board[0].length; dropRow++)
		{
			//if the field is empty, drop the token.
			if (board[column][dropRow] == Token.empty)
			{
				board[column][dropRow] = tok;
				break;
			}	
		}
		
		return dropRow;
	}
	
	/** Checks if every position is occupied */
	private boolean isBoardFull() {
		
		int checkColumn = 0;
		int checkRow = 5;
		//just look at the top rows of each column.
		for (checkColumn = 0; checkColumn < board.length; checkColumn++)
			//if one of the top fields is empty, then the board is not full = false
			if (board[checkColumn][checkRow] == Token.empty)
			{
				return false;
			}
		//but if every top field is NOT empty, the board is full = true
		return true;
	}
	
	/** Checks for at least four equal tokens in a row in either direction,
	    starting from the given position. */
	private boolean checkVierGewinnt(int col, int row){
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				
				int horizon = 0, vertical = 0, diag1 = 0, diag2 = 0;
				for (int count = 0; count < 4; count++) {
				
					if (i+count < board.length) {
						if (board[i+count][j] == board[col][row])
							horizon++;
					}
					
					if (j+count < board[0].length) {
						if (board[i][j+count] == board[col][row])
							vertical++;
					}
					
					if ((i+count < board.length) && (j+count < board[0].length)) {
						if (board[i+count][j+count] == board[col][row])
							diag1++;
					}
					
					if ((i-count >=0) && (j+count < board[0].length)) {
						if (board[i-count][j+count] == board[col][row])
							diag2++;
					}
				}
				
				if (horizon==4 || vertical==4 || diag1==4 || diag2==4)
					return true;
			}
		}
		
		return false;
	}
	
	/** returns a "graphical" representation of the board */
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
	
	/** main mathod, starts the program */
	public static void main(String args[]){
		VierGewinnt game = new VierGewinnt();
		game.play();
	}
}