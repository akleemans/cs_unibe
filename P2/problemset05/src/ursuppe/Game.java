package ursuppe;

import java.util.ArrayList;


/*
 * AK You pass Ursuppe. Wielding Java proficiently you bend the computer to your
 * will, shaping the virtual world of amoebas. Few words remain to be said:
 * 
 *  	* You designed your program well with reasonable responsibilities.
 *  	* You wrote documentation, which is great!
 *  	* Your methods seem to have grown for some time. At some point, you should
 *  	  ask if you can simplify the method as a whole or split it into more atomic
 *  	  sub-functionalities.
 *  
 *  AK You pass problemset09. I like your justification text on the command pattern,
 *  you seem to have a good grasp on the ideas of OOP. Using JExample is a nice
 *  touch too.
 */

/**
 * The Game class acts as a Driver. Provides basic data structures as stated
 * by Ursuppe game rules, initializes GUI, and runs the game repeatedly
 * through the six existing phases, until a player wins.
 */
public class Game {
	
	private static Board board;
	private Player[] players = new Player[3];
	private static Player winner = null;
	
	public enum Color { 
		RED("red"),
		GREEN("green"),
		YELLOW("yellow");
		private String representation; // string representation of value
		Color(String s) { this.representation = s; }
		public String toString() { return this.representation; }
	}
	
	public enum Direction { 
		WEST("west"),
		NORTH("north"),
		EAST("east"),
		SOUTH("south");
		private String representation; // string representation of value
		Direction(String s) { this.representation = s; }
		public String toString() { return this.representation; }
	}
	
	public static void main(String[] args) {
		
		Player[] players = new Player[3];
		
		Player Robert = new Player("Robert");
		Player Hannah = new Player("Gabi");
		Player NoName = new Player("Yohanna");
		
		players[0]=Robert; players[1]=Hannah; players[2]=NoName;
		players[0].chooseColor(Color.RED); players[1].chooseColor(Color.GREEN); 
		players[2].chooseColor(Color.YELLOW);
		
		Game game = new Game(players, true); 
	}
	
	public Game(Player[] players, boolean showOutput){
		
		//initialize GUI
		OutputControl o = new OutputControl();
		if (showOutput) o.showOutput();
		
		this.players = players;
		this.setUpGame(this.players);
		
		int round = 0;
		while(winner==null){
			round++;
			if(round<10)
				announcement("ROUND "+round);
			if(round==10)
				finalRoundStatement();
			this.phaseOne();
			this.phaseTwo(round);
			this.phaseThree();
			this.phaseFour();
			this.phaseFive();
			this.phaseSix(round);
			
			if (winner!=null)
				System.out.println(winnerToString(winner));
		}
	}
	
	/*
	 * Preparates the game: Calls the methods responsible to determine the starting
	 * player, to place the amoebas at the beginning etc.
	 */
	private void setUpGame(Player[] players) {
		
		announcement("URSUPPE");
		
		System.out.println("Choosing colors:");
		for(int i=0;i<players.length;i++)
			System.out.println("Player "+players[i].getName()+" chose color: "+players[i].getColor());
		
		determineStartingPlayer(players);
		
		System.out.println("");
		this.choosePlayerMarkerPosition(players);
		java.util.Arrays.sort(players);
		
		board = new Board(players);
		
		board.setEnvironmentCard();
		System.out.println(" ");
		
		for(int i = players.length-1;i>-1;i--){
			board = players[i].placeAmoeba(board);
		}
		for(int i = 0;i<players.length;i++){
			board = players[i].placeAmoeba(board);
		}
		
		System.out.println("\nBoard after setup:");
		System.out.println(board.toString());
	}
	
	/*
	 * Determines which Player that starts the game.
	 */
	private void determineStartingPlayer(Player[] players) {
		System.out.println("\nDetermining the starting player. Please throw two dies:");
		
		int i = 1;
		while(players[0].getRolledDies() == players[1].getRolledDies() && players[1].getRolledDies() == players[2].getRolledDies()){
			if (i!=1)
				System.out.println("\nTie, please roll again!\n");
			
			ArrayList<Integer> firstRoll = new ArrayList<Integer>();
			ArrayList<Integer> secondRoll = new ArrayList<Integer>();
				
			for(int j=0;j<players.length;j++){
				firstRoll.add(players[j].rollDie());
				secondRoll.add(players[j].rollDie());
				players[j].setRolledDies(firstRoll.get(j)+secondRoll.get(j));
				System.out.println(players[j].getName()+" rolled "+firstRoll.get(j)+" and "+secondRoll.get(j)+" = "+(firstRoll.get(j)+secondRoll.get(j)));
			}
			i++;
		}
		
		sortPlayers(players);
	}
	
	/*
	 * Sorts the players according to their starting position and
	 * calls the #resort method if some players rolled the same number
	 * with the dies.
	 */
	private void sortPlayers(Player[] players) {
		
		java.util.Arrays.sort(players);

		for(int position=1; position<players.length;position++){
	    	if (players[position-1].getRolledDies() == players[position].getRolledDies())
	    		players = resort(position, players);
	    }
	}
	
	/*
	 * Sorts the players again, if two have rolled the same number.
	 */
	private Player[] resort(int position, Player[] players) {
		
		System.out.println("\nTie between "+players[position-1].getName()+" and "+players[position].getName()+". Please roll two dies again!");
		
		while(players[position-1].getRolledDies2ndAttempt()==players[position].getRolledDies2ndAttempt()){
			
			ArrayList<Integer> firstRoll = new ArrayList<Integer>();
			ArrayList<Integer> secondRoll = new ArrayList<Integer>();
			
			for(int j=position-1;j<=position;j++){
				firstRoll.add(players[j].rollDie());
				secondRoll.add(players[j].rollDie());
				if (j==position-1){
					players[j].setRolledDies2ndAttempt(firstRoll.get(0)+secondRoll.get(0));
					System.out.println(players[j].getName()+" rolled "+firstRoll.get(0)+" and "+secondRoll.get(0)+" = "+(firstRoll.get(0)+secondRoll.get(0)));
				}
				else{
					players[j].setRolledDies2ndAttempt(firstRoll.get(1)+secondRoll.get(1));
					System.out.println(players[j].getName()+" rolled "+firstRoll.get(1)+" and "+secondRoll.get(1)+" = "+(firstRoll.get(1)+secondRoll.get(1)));
				}
			}
		
		
			if(players[position-1].getRolledDies2ndAttempt()!=players[position].getRolledDies2ndAttempt()){
				Player tempPlayer = new Player("tempPlayer");
				tempPlayer = players[position-1];
				players[position-1]=players[position];
				players[position]=tempPlayer;
			}
		}
		return players;
	}
	
	/*
	 * Orders the players to choose the starting position of their score markers
	 */
	private void choosePlayerMarkerPosition(Player[] players){
		for(Player player : players)
			player.choosePlayerMarkerPosition(players);
	}
	
	/*
	 * The following six methods are each responsible for one phase of the game:
	 */

	private void phaseOne(){
		System.out.println("\n############ PHASE 1: MOVEMENT AND FEEDING ############\n");
		System.out.println("Compass shows: "+board.getDirectionOfCompass().toString().toUpperCase()+"\n");
		
		for(int i=players.length-1;i>-1;i--){
			board = players[i].driftOrMove(board); // AK This reassigning and manipulating in methods... are you familiar with functional programming?
		}
		
		//board.initializeDrift();
		System.out.println("");
		for(int i = players.length-1;i>-1;i--){
			board = players[i].makeAmoebasEat(board);
		}		
		
		System.out.println("\nBoard after drifting, moving, eating and excretion:");
		System.out.println(board.toString());
	}
	
	private void phaseTwo(int round){
		System.out.println("\n############ PHASE 2: ENVIRONMENT AND GENE DEFECTS ############\n");
		
		board.setEnvironmentCard();
			
		System.out.println("New environment card set, new direction of drifting: "+board.getDirectionOfCompass().toString().toUpperCase()+"\n");
		System.out.println("Thickness of the Ozone Layer: "+board.getThicknessOfOzoneLayer()+"\n");
		
		board.checkForGeneDefects(players);
	}
	
	private void phaseThree(){
		System.out.println("\n############ PHASE 3: NEW GENES ############\n");
		
		for(int i=players.length-1;i>-1;i--){
			board = players[i].buyGeneCards(board);
		}
	}
	
	private void phaseFour(){
		System.out.println("\n############ PHASE 4: CELL DIVISION ############\n");
		
		for(Player p: players)
			p.addBP(10);
		
		for(int i=players.length-1;i>-1;i--){
			board = players[i].divideAmoeba(board);
		}
		System.out.println("\nBoard after cell division:");
		System.out.println(board.toString());
		
		// nothing at this phase of development of the game.
		
	}
	
	private void phaseFive(){
		System.out.println("\n############ PHASE 5: DEATHS ############\n");
		
		board.orderAmoebasToDieIfTooMuchDamage(players);
		System.out.println("\nBoard after deaths:");
		System.out.println(board.toString());
	}
	
	private void phaseSix(int round){
		System.out.println("\n############ PHASE 6: SCORING ############\n");
		
		board.setPlayers(players);
		players = board.scoring();
		java.util.Arrays.sort(players);
		
		System.out.println("\nBoard after scoring:");
		System.out.println(board.toString());
		
		winner = board.winner(round);
	}
	
	/*
	 * Some methods responsible for the ASCII representation of the game:
	 */
	
	private void announcement(String announcement) {
		
		StringBuilder welcomeString = new StringBuilder();
		
		welcomeString.append("\n ###############\n");
		welcomeString.append(" ##           ##\n");
		welcomeString.append(" ##  "+announcement+"  ##\n");
		welcomeString.append(" ##           ##\n");
		welcomeString.append(" ###############\n");
		System.out.println(welcomeString.toString());
	}
	
	private void finalRoundStatement() {
		StringBuilder welcomeString = new StringBuilder();
		welcomeString.append("\n ################################\n");
		welcomeString.append(" ##                            ##\n");
		welcomeString.append(" ##  ROUND 10 - FINAL ROUND!!  ##\n");
		welcomeString.append(" ##                            ##\n");
		welcomeString.append(" ################################\n");
		System.out.println(welcomeString.toString());
		
	}
	
	private String winnerToString(Player winner) {

		StringBuilder winnerString = new StringBuilder();
		
		StringBuilder buffer15 = new StringBuilder();
		StringBuilder buffer24 = new StringBuilder();
		for(int i=0;i<winner.getName().length();i++){
			buffer15.append("#");
			buffer24.append(" ");
		}
		
		winnerString.append("\n ###################"+buffer15.toString()+"####\n");
		winnerString.append(" ##                 "+buffer24.toString()+"  ##\n");
		winnerString.append(" ##  The WINNER is: "+winner+"  ##\n");
		winnerString.append(" ##                 "+buffer24.toString()+"  ##\n");
		winnerString.append(" ###################"+buffer15.toString()+"####\n");
		return(winnerString.toString());
	}

	public Player[] getPlayers() {
		return players;
	}
}
