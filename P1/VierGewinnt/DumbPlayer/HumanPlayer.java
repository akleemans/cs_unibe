import java.util.Scanner;

/** A human player class */
public class HumanPlayer implements IPlayer{
	private Scanner scan = new Scanner(System.in);
	private VierGewinnt.Token token;
	
	public int getNextColumn(VierGewinnt.Token[][] board){
		System.out.println("\n"+VierGewinnt.displayBoard(board));
		int column = -1;
		while(column < 0 || column > board.length){
			System.out.print("Player "+this.token.toString()
				+" choose a column between 1 and "+board.length+": ");
			column = Integer.parseInt(scan.nextLine()) - 1;
			if(column >= 0 && column < board.length){
				int topRow = board[0].length-1;
				if(board[column][topRow] != VierGewinnt.Token.empty){
					System.out.println("This column is full!");
					column = -1;
				}
			}
		}
		return column;
	}
	
	public void setToken(VierGewinnt.Token token){
		this.token = token;
	}
	public VierGewinnt.Token getToken(){
		return this.token;
	}
	
	public String getProgrammers(){
		return "P1 Team";
	}
}