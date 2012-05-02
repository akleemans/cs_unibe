import java.util.Random;
import java.util.Scanner;

public class GuessChess {
	private int secretRow;		// secret row between 1 and 8
	private char secretColumn;	// secret column between 'a' and 'h'
	private int score;			// number of rounds won by player
	private int no_rounds;		// total number of rounds played
	
	public final int MAX_GUESSES = 3;
 	
	/** Constructor */
	public GuessChess() {
		score = 0;
		no_rounds = 0;
	}
	
	/** Main loop: play until user says stop */
	public void play() {
		showWelcomeScreen();
		String goOn = "y";
		Scanner scn = new Scanner(System.in);
		while (goOn.equals("y")){
			doRound();
			System.out.print("Play another round (y or n)? ");
			goOn = scn.next().toLowerCase();
		}
		printFinalResult();
	}

	/** Choose a field randomly and let the player guess */
	private void doRound() {
		Random generator = new Random();
		secretRow = generator.nextInt(7)+1;
		secretColumn = getColumnAsChar(generator.nextInt(7)+1);
		guessField();
		no_rounds++;
	}
	
	/** Let the player guess the right field at most MAX_GUESSES times */
	private void guessField() {
		String guess;
		// initialisierung von c & r damit zeile 93 funktioniert
		char column = 'z'; 
		int row = 0;
		int attempt = 0;
		printEmptyBoard();
		System.out.println();
		Scanner scan = new Scanner(System.in);
		// wenn max_guesses erreicht oder wenn beide (c & r) richtig, dann endet while
		while ((attempt < MAX_GUESSES) && ((column!=secretColumn) || (row!=secretRow)))	{
			System.out.print("You have " + (MAX_GUESSES-attempt) +
				" attempts left. Guess field (e.g. b3): ");
			guess = scan.nextLine();
			column = guess.charAt(0);
			row = Integer.parseInt(guess.substring(1, 2));
			
			System.out.println();
			
			if (attempt != 2) {
				if ((column != secretColumn) || (row != secretRow))
					System.out.print("" + column + row + " was wrong. Hints: ");
				if (column < secretColumn)
					System.out.print("GO RIGHT! ");
				if (column > secretColumn)
					System.out.print("GO LEFT! ");
				if (row < secretRow)
					System.out.print("GO UP! ");
				if (row > secretRow)
					System.out.print("GO DOWN! ");
			}
			
			// wenn sowohl col als auch row stimmen -> sieg; score + 1
			if (column == secretColumn)
				if (row == secretRow) {
					System.out.print("That was right! You win!");
					score++;
				}
				
			System.out.println();
			printBoard(column, row);
			// anzahl versuche um eins erhoeht, damit zeile 60 aufgeht
			attempt++;
		}
		if ((column!=secretColumn) || (row!=secretRow))
			System.out.println("You lose! The secret field was " secretColumn + secretRow);
	}

	/** Prints the final result (who won how often) */
	private void printFinalResult() {
		System.out.println("GAME OVER!");
		System.out.println("Final score: you won " + score + " out of "+
			no_rounds + " rounds.");
	}
	
	/** prints a chess board with an 'X' on the field defined by column and row */	
	private void printBoard(char column, int row) {
		int column_num = getColumnAsInt(column);
		String board = "  _ _ _ _ _ _ _ _\n";
		for (int i=8; i>0; i--) {
			board += i;
			if (row==i) {
				for(int j=1; j<=8; j++) {
					if(column_num==j) {
						board += "|X";
					} 
					else {
						board += "|_";
					}
				}
				board += "|\n";
			} 
			else {
				board += "|_|_|_|_|_|_|_|_|\n";
			}
		}
		board += "  a b c d e f g h ";
		System.out.println(board);
	}
	/** print the board without an 'X' */
	private void printEmptyBoard() {
		printBoard('z',-1);
	}
	
	private void showWelcomeScreen() {
		String s="";;
		s += "           ()\n";
		s += "         <~~~~>\n";
		s += "          \\__/\n";
		s += "         (____)                    ___/\"\"\"\n";
		s += "          |  |                    |___ 0 }\n";
		s += "          |  |    *************      /   }\n";
		s += "          |__|    *GUESS CHESS*    /~    }\n";
		s += "         /____\\   *************    \\____/\n";
		s += "        (______)                   /____\\\n";
		s += "       (________)                 (______)\n";
		System.out.println(s);
	}
	
	private int getColumnAsInt(char column) {
		switch(column){
			case 'a': return 1;
			case 'b': return 2;
			case 'c': return 3;
			case 'd': return 4;
			case 'e': return 5;
			case 'f': return 6;
			case 'g': return 7;
			case 'h': return 8;
			default: return 0;
		}
	}
	private char getColumnAsChar(int column) {
		switch(column){
			case 1: return 'a';
			case 2: return 'b';
			case 3: return 'c';
			case 4: return 'd';
			case 5: return 'e';
			case 6: return 'f';
			case 7: return 'g';
			case 8: return 'h';
			default: return 'z';
		}
	}
}