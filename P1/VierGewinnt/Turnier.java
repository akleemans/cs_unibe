/* Programmierung 1 HS 2011 Vier Gewinnt Turnier zu Aufgabe 4-1 */

import java.util.*;
import java.io.*;

public class Turnier {

	private Token[][] board = new Token[7][6]; // 7 columns with 6 fields each
	private IPlayer[] players = {
					new SmartComputerPlayer(),
					new ComputerPlayerAndonie(),
					new ComputerPlayerBibersteinWalter(),
					new ComputerPlayerBurnVonRotz(),
					new ComputerPlayerDizerens(),
					new ComputerPlayerFriedliMetushi(),
					new ComputerPlayerFellerMumenthaler(),
					new ComputerPlayerFrischknecht(),
					new ComputerPlayerGadowNeu(),
					new ComputerPlayerHaechlerMateo(),
					new ComputerPlayerHermidas(),
					new ComputerPlayerHirsbrunnerFiave(),
					new ComputerPlayerJenni(),
					new ComputerPlayerJoosRohlfs(),
					new ComputerPlayerKellerGerberNeu(),
					new ComputerPlayerKleemansTorriani(),
					new ComputerPlayerKochWong(),
					new ComputerPlayerKohlerDiener(),
					new ComputerPlayerMatter(),
					new ComputerPlayerMeier(),
					new ComputerPlayerRadunzStapleton(),
					new ComputerPlayerRoethlisbergerNeu(),
					new ComputerPlayerRosa(),
					new ComputerPlayerSchmid(),
					new ComputerPlayerSchneebergerGasser(),
					new ComputerPlayerSchuerch(),
					new ComputerPlayerStutz(),
					new ComputerPlayerVonFeltenVoegtlin(),
					new ComputerPlayerWittwer(),
					new ComputerPlayerZahnd(),
					new ComputerPlayerZaugg(),
					new ComputerPlayerZyssetHess()
				};
	
	private int[] score = new int[players.length];

	private int gameCounter = 0;

	private String logBuffer;
	
	private HashMap<String,Long> cpuTime = new HashMap<String,Long>(); //measures CPU time for each player
	
	public void startTournament() throws IOException, Exception{
		log("\nPlayers:\n********");
		for(IPlayer p : players){
			log(p.getProgrammers());
		}
		int noGames = players.length*(players.length-1);
		Scanner scn = new Scanner(System.in);
		System.out.print("\nPress enter to start the "+noGames+" games.");
		scn.nextLine();
		
		
		for(int i=0; i<players.length; i++){
			for(int j=i+1; j<players.length; j++){
				int result = play(players[i], players[j]);
				if(result==0){      // remis, both get 1 point
					score[i] += 1;
					score[j] += 1;
				}else if(result==1){
					score[i] += 3;
				}else if(result==2){
					score[j] += 3;
				}
				// let the other player begin
				result = play(players[j], players[i]);
				if(result==0){      //remis, both get 1 point
					score[i] += 1;
					score[j] += 1;
				}else if(result==1){
					score[j] += 3;
				}else if(result==2){
					score[i] += 3;
				}
			}
		}
		
		PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("ergebnis.csv")));
		log("\nErgebnis:");
		file.println("Spieler(in);Punkte;CPU time (ms)");
		for(int i=0; i<score.length; i++){
			log(players[i].getProgrammers()+"; points: "+score[i]+"; CPU time: "+cpuTime.get(players[i].getProgrammers()));
			file.println(players[i].getProgrammers()+";"+score[i]+";"+cpuTime.get(players[i].getProgrammers()));
		}
		file.close();
		log("");
		flush();
	}
	
	private void log(String line){
		System.out.println(line);
		logBuffer = logBuffer+"\n"+line;
	}
	/** print logfile */
	private void flush() throws IOException{
		PrintWriter logfile = new PrintWriter(new BufferedWriter(new FileWriter("log.txt")));
		logfile.print(logBuffer);
		logfile.close();
	}
	
	
	
	/** initialize board and players and start the game 
	    returns 0 in case of draw, 1 in case player1 won, 2 otherwise */
	public int play(IPlayer player1, IPlayer player2) throws Exception{

		int noOfGames = players.length*(players.length-1);
		gameCounter++;
		// initialize the board
		for (Token[] column : this.board) {
			Arrays.fill(column, Token.empty);
		}
		IPlayer[] players = new IPlayer[2];
		players[0] = player1;
		players[1] = player2;
		players[0].setToken(Token.player1);
		players[1].setToken(Token.player2);

		// play...
		boolean solved = false;
		int currentPlayer = 0;
		int insertCol, insertRow;

		while (!solved && !this.isBoardFull()){
			
			// get player's next "move"

			ThreadMove moveThr = new ThreadMove(getCopyOfBoard(), players[currentPlayer]);
			moveThr.retValue = -1;

			long msBefore = System.currentTimeMillis();

			moveThr.start();
			long time = 0;
			// stop thread if after 30sec. the player has not chosen a column
			while(true){
				time = System.currentTimeMillis() - msBefore;
				if(moveThr.retValue != -1){
					//System.out.println("PLAYER "+players[currentPlayer].getProgrammers()+" has chosen col "+moveThr.retValue);
					break;
				}
				if(time > 30000){
					log("PLAYER "+players[currentPlayer].getProgrammers()+" HAS USED UP THE 30 SECONDS!");
					//System.out.println(moveThr.retValue);
					moveThr.stop();
					break;
				}
				Thread.sleep(10); // dummy
			}
			// either the thread has ended naturally or it was forced to end (then its return value is an illegal move)
			// let's look at the move of the player
			insertCol = moveThr.retValue;

			long msDiff =  System.currentTimeMillis() - msBefore;
			Long myCpuTime = cpuTime.get(players[currentPlayer].getProgrammers());
			if(myCpuTime == null){
				myCpuTime = 0l;
			}
			cpuTime.put(players[currentPlayer].getProgrammers(), myCpuTime+msDiff);
			
			boolean illegalMove=false;
			if(insertCol<0 || insertCol>=board.length){ // illegal move! other player wins!
				log("Warning! Player "+players[currentPlayer].getProgrammers()+" chose a non-existing column: "+(insertCol));
				illegalMove = true;
			}else if(checkColumnFull(insertCol)){	// illegal move! other player wins!
				log("Warning! Player "+players[currentPlayer].getProgrammers()+" chose an already full column!");
				illegalMove = true;
			}
			if(illegalMove){
				log("*******************************************************");
				log("*    "+players[(currentPlayer+1)%2].getProgrammers() + " wins!");
				log("*******************************************************");
				if(currentPlayer==0) return 2;
				if(currentPlayer==1) return 1;
			}
			// insert the token and get the row where it landed
			insertRow = this.insertToken(insertCol, players[currentPlayer].getToken());
			// check if the game is over
			solved = this.checkVierGewinnt(insertCol, insertRow);
			//switch to other player
			if (!solved) currentPlayer = (currentPlayer+1) % 2;
			
			log("\nGame "+gameCounter+"/"+noOfGames+": "+player1.getProgrammers()+" ("+player1.getToken()+")   VERSUS   "+
							player2.getProgrammers()+" ("+player2.getToken()+")");
			log(displayBoard(this.board));
		}
		if (solved){
			log("*******************************************************");
			log("*    "+players[currentPlayer].getProgrammers() + " wins!");
			log("*******************************************************");
			return (currentPlayer==0 ? 1 : 2);
		}else{
			log("Draw!");
			//log(displayBoard(this.board));
			return 0;
		}
	}

	/** returns true if the given column is full */
	private boolean checkColumnFull(int col){
		return !board[col][board[0].length-1].equals(Token.empty);
	}
	
	
	/** Inserts the token at the specified column (if possible)
	    and returns the row where the token landed */
	private int insertToken(int column, Token tok){
		for(int row=0; row<this.board[0].length; row++){
			if(this.board[column][row] == Token.empty){
				this.board[column][row] = tok;
				return row;
			}
		}
		// should never happen...
		log("Player "+tok+" chose invalid column "+(column+1)+". Exiting...");
		System.exit(1);
		return -1;
	}
	
	/** returns true iff board is full */
	private boolean isBoardFull(){
		for(int col=0; col<board.length; col++){
			if(board[col][board[col].length-1] == Token.empty)
				return false;
		}
		return true;
	}	
	
	
	/** Checks for at least four equal tokens in a row in either direction,
	    starting from the given position. */
	private boolean checkVierGewinnt(int col, int row){
		Token tok = this.board[col][row];
		return ((this.countHorizontally(tok, col, row) >= 4)
			|| (this.countVertically(tok, col, row) >= 4)
			|| (this.countDiagonallyBLtoTR(tok, col, row) >= 4)
			|| (this.countDiagonallyTLtoBR(tok, col, row) >= 4));
	}
	private int countHorizontally(Token tok, int col, int row) {
		int counter = 0;
		int tmpCol = col;
		// check to the left...
		while (tmpCol >= 0 && this.board[tmpCol][row] == tok){
			counter++;
			tmpCol--;
		}
		// and to the right...
		tmpCol = col + 1;
		while (tmpCol < this.board.length && this.board[tmpCol][row] == tok) {
			counter++;
			tmpCol++;
		}
		return counter;
	}
	private int countVertically(Token tok, int col, int row){
		int counter = 0;
		int tmpRow = row;
		// check downwards...
		while (tmpRow >= 0 && this.board[col][tmpRow] == tok) {
			counter++;
			tmpRow--;
		}
		// and upwards...
		tmpRow = row + 1;
		while (tmpRow < this.board[col].length && this.board[col][tmpRow] == tok) {
			counter++;
			tmpRow++;
		}
		return counter;
	}
	private int countDiagonallyBLtoTR(Token tok, int col, int row){
		int counter = 0, tmpCol = col, tmpRow = row;
		// check to bottom left...
		while (tmpCol >=0 && tmpRow >= 0 && this.board[tmpCol][tmpRow] == tok) {
			counter++;
			tmpRow--;
			tmpCol--;
		}
		// and top right...
		tmpRow = row + 1;
		tmpCol = col + 1;
		while (tmpCol < this.board.length && tmpRow < this.board[0].length 
				&& this.board[tmpCol][tmpRow] == tok) {
			counter++;
			tmpRow++;
			tmpCol++;
		}
		return counter;
	}
	private int countDiagonallyTLtoBR(Token tok, int col, int row) {
		int counter = 0, tmpCol = col, tmpRow = row;
		// check top left...
		while (tmpCol >= 0 && tmpRow < board[0].length && this.board[tmpCol][tmpRow] == tok) {
			counter++;
			tmpRow++;
			tmpCol--;
		}
		// and bottom right...
		tmpRow = row - 1;
		tmpCol = col + 1;
		while (tmpCol < board.length && tmpRow >=0 && this.board[tmpCol][tmpRow] == tok) {
			counter++;
			tmpRow--;
			tmpCol++;
		}
		return counter;
	}
	
	/** Returns a (deep) copy of the board array */	
	private Token[][] getCopyOfBoard(){
		Token[][] copiedBoard = new Token[this.board.length][this.board[0].length];
		for(int i=0; i<copiedBoard.length; i++){
			for(int j=0; j<copiedBoard[i].length; j++){
				copiedBoard[i][j] = this.board[i][j];
			}
		}
		return copiedBoard;
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
	public static void main(String args[]) throws IOException, Exception{
		Turnier game = new Turnier();
		game.startTournament();
	}

	/** two inner classes */
	class ThreadMove extends Thread{

		private Token[][] board;
		private IPlayer player;

		public int retValue;

		public ThreadMove(Token[][] board, IPlayer player){
			this.board = board;
			this.player = player;
		}

		public void setBoard(Token[][] board){
			this.board = board;
		}
		public void setPlayer(IPlayer player){
			this.player = player;
		}

		public void run() {
			//log("        thread "+this+" starts with priority "+Thread.currentThread().getPriority());
			//try{
				int col = player.getNextColumn(board);
				this.retValue = col;
				//System.out.println("thread says: player has chosen col "+ col);
			/*}catch(Exception e){  // other player should win, so make an illegal move...
				// set an illegal value different from -1 (so that the caller knows that the thread has ended).
				// This is quick & dirty. Don't try this at home!
				this.retValue = -100;
				log("\nWarning! Player "+player.getProgrammers()+" caused an exception: "+e.toString());
				//log("board:");
				//log(displayBoard(this.board));
			}*/
			//log("         thread "+this+" ends...");
		}
	}
}