import java.util.Random;

/** A random computer player class */
public class ComputerPlayer implements IPlayer{
	private VierGewinnt.Token token;
	Random gen = new Random();
	public int getNextColumn(VierGewinnt.Token[][] board){
		System.out.println("\n"+VierGewinnt.displayBoard(board));
		int column = -1;
		while(column < 0 || column > board.length){
			System.out.print("Player "+this.token.toString()
				+" choose a column between 1 and "+board.length+": ");
			column = gen.nextInt(7);
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
		return "(Programmers)";
	}
}