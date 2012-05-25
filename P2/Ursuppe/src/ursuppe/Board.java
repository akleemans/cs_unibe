package ursuppe;
import java.util.ArrayList;
import java.util.Collections;

import ursuppe.Game.Color;

/**
 * The Board-Class contains the different Squares needed,
 * the EnvironmentCard-Stack, Food-Stacks, Amoeba-Stacks and GeneCard-Stack$
 * and answers questions about their current state.
 * 
 * It takes care of the Amoebas and their position and action, 
 * handles shifting-actions. For Players, it can provide some information 
 * including their position markers and winner status.
 */
public class Board {
	private Square[][] board;
	
	private FoodStack foodStackRed = new FoodStack(Game.Color.RED);
	private FoodStack foodStackGreen = new FoodStack(Game.Color.GREEN);
	private FoodStack foodStackYellow = new FoodStack(Game.Color.YELLOW);
	
	private ArrayList<FoodStack> overallFoodStack = new ArrayList<FoodStack>();
	
	private AmoebaStack amoebaStackRed = new AmoebaStack(Game.Color.RED);
	private AmoebaStack amoebaStackGreen = new AmoebaStack(Game.Color.GREEN);
	private AmoebaStack amoebaStackYellow = new AmoebaStack(Game.Color.YELLOW);
	
	private ArrayList<AmoebaStack> overallAmoebaStack = new ArrayList<AmoebaStack>();
	
	private ArrayList<EnvironmentCard> environmentCardStack = new ArrayList<EnvironmentCard>();
	
	private ArrayList<GeneCard> geneCardStack = new ArrayList<GeneCard>();
	
	private Player[] players;
	
	public Board(Player[] players){
		board = new Square[5][5];
		this.players = players;
		
		this.fillWithGameSquares();
		this.fillEnvironmentCardStack();
		this.shuffleEnvironmentCards();
		this.fillOverallStacks();
		this.fillFoodStacks();
		this.fillAmebaStacks();
		
		this.addAmoebasToPlayers(players);
		
		this.fillSquaresWithFood();
		this.giveSquaresPositions();
		this.fillGeneCardStack();
		
		assert environmentCardStack.size() == 11;
		assert invariant();
	}
	
	private void addAmoebasToPlayers(Player[] players) {
		for(Player p : players){
			for(AmoebaStack as : this.overallAmoebaStack){
				if (p.getColor() == as.getColor())
						p.addAmoebaStack(as);
			}
		}
		
	}

	/*
	 * Recognizes in which direction to drift and triggers drifting then
	 * by calling the #drift method
	 */
	public void determineDirectionOfAmoebaDisplacement(Game.Color color, int number){
		this.determinePositionAfterAmoebaDisplacement(color, number, getDirectionOfCompass()); 
	}
	
	public void determineDirectionOfAmoebaDisplacement(Game.Color color, int number, Game.Direction direction){
		this.determinePositionAfterAmoebaDisplacement(color, number, direction); 
	}
	
	private void determinePositionAfterAmoebaDisplacement(Game.Color color, int number, Game.Direction direction){
		String position = this.getPositionOfAmebaOfColorAndNumber(color, number);
		
		if (!position.equals("empty")){
			int oldRow = Integer.parseInt(position.split(" ")[0]);
			int oldCol = Integer.parseInt(position.split(" ")[1]);
			
			int newRow = oldRow;
			int newCol = oldCol;
			
			switch(direction){
				case WEST: newCol--; break;
				case NORTH: newRow--; break;
				case EAST: newCol++; break;
				case SOUTH: newRow++; break;
			}
			
			this.amoebaDisplacement(oldRow, oldCol, newRow, newCol, color, number);
		}
	}
	
	/*
	 * Helper method to find the square where the amoeba with color
	 * color and Number number is.
	 */
	public String getPositionOfAmebaOfColorAndNumber(Game.Color color, int number){
		String returner = "empty";
		for(int row=0;row<5;row++){
			for(int col=0;col<5;col++)
				if(board[row][col].amoebaWithColorAndNumberIsOnField(color, number)){
					returner = String.valueOf(row)+" "+String.valueOf(col);
				}
		}
		return returner;
	}
	
	/*
	 * Drifts amoebas in the issued direction
	 */
	private void amoebaDisplacement(int oldRow, int oldCol, int newRow, int newCol, Game.Color color, int number) {
		try { 
			if (this.board[newRow][newCol].isIngameSquare()){
				Amoeba tempAmeba = board[oldRow][oldCol].removeAmoebaOfColorAndNumber(color, number);
				this.board[newRow][newCol].addAmeba(tempAmeba);
				System.out.println("Ameba "+color+number+" was displaced from "+oldRow+"/"+oldCol+" to "+newRow+"/"+newCol);
			}
			else{
				throw new Exception();
			}
		}
		//Also catches Array Out Of Bound Exceptions due to a newRow or newCol <0 or >4
		catch( Exception e) {  
			System.out.println("Ameba "+color+number+" was not able to be displaced from "+oldRow+"/"+oldCol+" to "+newRow+"/"+newCol);
		}  
	}

	/*
	 * Adds two food cubes of an ameba's color (its excretion)
	 * to the square of this amoeba after it has eaten 
	 */
	public void addExcretedFoodCubesToSquare(Game.Color color, int row, int col){
		
		String excreteString = "- the excreted food could not be replaced because of a shortage of food";
		
		for (FoodStack foodStackInLoop : overallFoodStack){
			if (foodStackInLoop.getColor().equals(color) && foodStackInLoop.size()>1){
				excreteString = "- the Amoeba excreted "+color.toString()+" "+color.toString();
				ArrayList<FoodCube> excretedFoodCubes = new ArrayList<FoodCube>();
				excretedFoodCubes.add(foodStackInLoop.remove(0));
				excretedFoodCubes.add(foodStackInLoop.remove(0));
				this.board[row][col].amoebaExcrete(excretedFoodCubes);
			}
		}
		
		assert invariant();
		
		System.out.println(excreteString);
	}
	
	/*
	 * Returns eaten FoodCubes to the according foodStacks
	 */
	private void returnEatenFoodToStacks(ArrayList<FoodCube> eatenCubes) {
		
		for(FoodCube fc: eatenCubes){
			for (FoodStack fs : overallFoodStack){
				if (fc.getColor().equals(fs.getColor()))
					fs.add(fc);
			}
		}
		assert invariant();
	}

	/*
	 * Adds an amoeba of the indicated color to the indicated
	 * Square on the board.
	 */
	public void addAmebaToSquare(int row, int col, Game.Color color){
		
		for (AmoebaStack as : overallAmoebaStack){
				if (as.getColor().equals(color) && !as.isEmpty())
					board[row][col].addAmeba(as.remove(0));
			}
		assert invariant();
	}
	
	/*
	 * Checks all the Squares on the board if there are amoebas
	 * that have too many damage points to survive Stage 5 when
	 * amoebas with too many damage points die.
	 */
	public void orderAmoebasToDieIfTooMuchDamage(Player[] players) {
		for (int row = 0; row <5; row++){
			for(int col = 0; col<5; col++){
				this.replaceDeadAmebas(board[row][col].orderAmoebasToDieIfTooMuchDamage(players), row, col);
			}
		}
		assert invariant();
	}
	
	/*
	 * Removes amoebas with too many damage points from the 
	 * board and puts them back to the according stack.
	 * They are replaced by two FoodCubes of each color.
	 */
	private void replaceDeadAmebas(
			ArrayList<Amoeba> deadAmoebas, int row, int col) {
		
		for(Amoeba amoebaInLoop : deadAmoebas){
			for(AmoebaStack amoebaStackInLoop : overallAmoebaStack){
				if(amoebaInLoop.getColor().equals(amoebaStackInLoop.getColor())){
					System.out.println("Ameba "+amoebaInLoop.getColor()+amoebaInLoop.getNumber()+" died on "+row+"/"+col+". (The amoeba had "+amoebaInLoop.getDamagePoints()+" damage points.)");
					amoebaInLoop.resetDamagePoints();
					amoebaStackInLoop.add(amoebaInLoop);
					for(FoodStack fs : overallFoodStack){
						if(fs.size()>1){
							board[row][col].addFoodCube(fs.remove(0));
							board[row][col].addFoodCube(fs.remove(0));
						}
					}
				}
			}
		}
		assert invariant();
	}
	
	public int getNbrOfFoodCubesOnASquare(Color color, int row, int col){
		return (board[row][col].getNbrOfFoodCubes(color));
	}

	public void removeEatenFoodCubes(String methabolismString, int row, int col) {
		this.returnEatenFoodToStacks(board[row][col].removeEatenFoodCubes(methabolismString));
	}

	public void addDamagePoint(Color color, int amoebaNumber, int row, int col) {
		board[row][col].addDamagePoint(color,amoebaNumber);
	}

	/*
	 * Two methods that return the values of the current
	 * EnvironmentCard on the compass.
	 */
	
	public Game.Direction getDirectionOfCompass(){
		return board[2][2].getDirection();
	}
	
	public int getThicknessOfOzoneLayer() {
		return board[2][2].getOzoneLayerThickness();
	}
	
	/*
	 * Sets a new environment card in phase 2.
	 */
	public void setEnvironmentCard(){
		if(!environmentCardStack.isEmpty())
			board[2][2].addEnvironmentCard(environmentCardStack.remove(0));
	}
	
	public void checkForGeneDefects(Player[] players) {
		for (Player p : players)
			this.geneCardStack.addAll(p.payForGeneDefects(this.getThicknessOfOzoneLayer()));
	}
	
	/*
	 * Indicates whether there is an Obstacle (Border, Compass) or
	 * an amoeba with the same color on a certain field
	 */
	public boolean obstacleOrSameAmebaOnField(int row, int col, Game.Color color){
		if (row>4 || col>4 || row<0 || col<0)
			return true;
		else
			return (board[row][col].amoebaWithColorAndNumberIsOnField(color) || !board[row][col].isIngameSquare());
	}
	
	/*
	 * Two additional methods that inform about the state
	 * of a certain Square on the Board.
	 */

	public boolean squareIsUnoccuppied(int row, int col){
		return board[row][col].isUnoccupied();
	}
	
	public boolean squareIsInGameSquare(int row, int col){
		return board[row][col].isIngameSquare();
	}
	
	/*
	 * updates the order of the players as information for the
	 * Board class
	 */
	public void setPlayers(Player[] players){
		this.players = players;
	}
	
	/*
	 * Is responsible for the scoring in phase 6
	 */
	public Player[] scoring() {
		for (Player player : players){
			int oldPosition = player.getPlayerMarker();
			this.addAmoebasToScore(player);
			this.addGeneCardsToScore(player);
			while(otherPlayerMarkerAtSamePlace(player.getColor(),player.getPlayerMarker()))
				player.addToPlayerMarker(1);
			if (oldPosition != player.getPlayerMarker())
				System.out.println(player.getName()+" has "+this.getNbrOfAmoebasOfAColorOnBoard(player.getColor())+" amoebas on the board and possesses "+player.getNbrOfGeneCards()+" gene cards. The player moved the "+player.getColor()+" score marker from "+oldPosition+" to "+player.getPlayerMarker());
			else
				System.out.println(player.getName()+" has "+this.getNbrOfAmoebasOfAColorOnBoard(player.getColor())+" amoebas on the board and possesses "+player.getNbrOfGeneCards()+" gene cards. The player did not move the "+player.getColor()+" score marker though");
		}
		assert invariant();
		assert (players[0].getPlayerMarker()!=players[1].getPlayerMarker() && players[1].getPlayerMarker()!=players[2].getPlayerMarker() && players[0].getPlayerMarker()!=players[2].getPlayerMarker());
		return players;
	}

	private void addAmoebasToScore(Player player) {
		switch(determineAmoebasOnBoard(player.getColor())){
			case 3: player.addToPlayerMarker(1); break;
			case 4: player.addToPlayerMarker(2); break;
			case 5: player.addToPlayerMarker(4); break;
			case 6: player.addToPlayerMarker(5); break;
			case 7: player.addToPlayerMarker(6); break;
		}
	}
	
	private void addGeneCardsToScore(Player player) {
		switch(player.getNbrOfGeneCards()){
			case 3: player.addToPlayerMarker(1); break;
			case 4: player.addToPlayerMarker(2); break;
			case 5: player.addToPlayerMarker(3); break;
			case 6: player.addToPlayerMarker(4); break;
			case 7: player.addToPlayerMarker(4); break;
	}
		
	}
	
	/*
	 * Returns the number of Amoebas of a certain color
	 * on the board
	 */
	private int determineAmoebasOnBoard(Game.Color color) {
		int returner = 0;
		for(AmoebaStack as : overallAmoebaStack){
			if(as.getColor().equals(color))
				returner =  (7-as.size());
		}
		return returner;
	}
	
	/*
	 * Returns true if there is a score marker of another player at the
	 * indicated position
	 */
	public boolean otherPlayerMarkerAtSamePlace(Game.Color color, int playerMarkerInt) {
		boolean returner = false;
		for (Player player : players){
			if(!player.getColor().equals(color) && player.getPlayerMarker()==playerMarkerInt)
				returner = true;
		}
		return returner;
	}
			
	/*
	 * Returns the winner of the game (if one player reached more than 42 points
	 * or if the last environment has turned over)
	 */
	public Player winner(int round){
		
		Player maxPlayer = maxplayer(players);
		
		if (maxPlayer.getPlayerMarker() >42)
			return maxPlayer;
		else if(round==10)
			return maxPlayer;
		else
			return null;
	}
	
	/*
	 * Returns the player with the highest position on the scoring ladder
	 */
	private Player maxplayer(Player[] players){
		
		Player maxPlayer = players[0];
		for (int i=1; i<players.length;i++){
			if (players[i].getPlayerMarker() > maxPlayer.getPlayerMarker())
				maxPlayer = players[i];
		}
		return maxPlayer;
	}
	
	/*
	 * Helper methods to fill the square with all the game stuff
	 * at the beginning of the game.
	 */
	
	private void fillWithGameSquares() {
		for(int row=0;row<5;row++){
			for(int col=0;col<5;col++)
				board[row][col] = new Square();
		}
		
		board[0][0] = new EmptySquare();
		board[0][2] = new EmptySquare();
		board[0][3] = new EmptySquare();
		board[0][4] = new EmptySquare();
		board[4][4] = new EmptySquare();
		
		board[2][2] = new CompassSquare();
	}
	
	private void fillEnvironmentCardStack() {
		for (Game.Direction direction : Game.Direction.values()){
			int i = 1;
			if (direction.equals(Game.Direction.SOUTH)) //Arbitrary, because there are only 11 cards in the game and I do not know the cards of the real game.
				i = 2;
			
			while(i<4){
				environmentCardStack.add(new EnvironmentCard(direction, i*2+2)); //Arbitrary, I just defined the ozone layer thickness to be between 4 and 10, because I do not know the real game values.
				i++;
			}
		}
	}
	
	private void shuffleEnvironmentCards() {
		Collections.shuffle(environmentCardStack);
	}

	private void fillOverallStacks() {
		overallFoodStack.add(foodStackRed); overallFoodStack.add(foodStackGreen); overallFoodStack.add(foodStackYellow);
		overallAmoebaStack.add(amoebaStackRed); overallAmoebaStack.add(amoebaStackGreen); overallAmoebaStack.add(amoebaStackYellow);
	}
	
	private void fillFoodStacks() {
		for(int i=0;i<55;i++){
			foodStackRed.add(new FoodCube(Game.Color.RED));
			foodStackGreen.add(new FoodCube(Game.Color.GREEN));
			foodStackYellow.add(new FoodCube(Game.Color.YELLOW));
		}
	}
	
	private void fillAmebaStacks() {
		for(int i=0;i<7;i++){
			amoebaStackRed.add(new Amoeba(Game.Color.RED,i+1));
			amoebaStackGreen.add(new Amoeba(Game.Color.GREEN,i+1));
			amoebaStackYellow.add(new Amoeba(Game.Color.YELLOW,i+1));
			}
	
	}

	private void fillSquaresWithFood() {
		for(int row=0;row<5;row++){
			for(int col=0;col<5;col++){
				if(board[row][col].isIngameSquare()){
					for (FoodStack fc : this.overallFoodStack){
						board[row][col].addFoodCube(fc.remove(0));
						board[row][col].addFoodCube(fc.remove(0));
					}
				}
			}	
		}
	}
	
	private void giveSquaresPositions() {
		for(int row=0;row<5;row++){
			for(int col=0;col<5;col++)
				board[row][col].setPosition(board,col,row);
		}
	}
	
	private void fillGeneCardStack() {
		this.geneCardStack.add(new GeneCardMovement1("Movement1",3,2));
		this.geneCardStack.add(new GeneCardMovement1("Movement1",3,2));
		this.geneCardStack.add(new GeneCard("Spores",3,3));
		this.geneCardStack.add(new GeneCardSpeed("Speed",4,3));
		this.geneCardStack.add(new GeneCardLifeExpectancy("LifeExpectancy",5,5));
		
		assert geneCardStack.get(0).getMutationPoints() == 2; // AK this might be better to keep in a test.
	}
	
	/*
	 * toString() method to give a graphical representation of the
	 * board in ASCII graphics
	 */
	public String toString(){
		
		StringBuilder boardString = new StringBuilder();
		
		boardString.append("       0              1              2              3              4        \n");
		for (int row = 0; row <5; row++){
			for(int line = 0; line<4; line++){
				for(int column = 0; column<5;column++){
					if (column==0 && line ==2)
						boardString.append(row);
					if (column==0 && line!=2)
						boardString.append(" ");
					
					boardString.append(board[row][column].Line(line));
					if (line == 0 && column ==4)
						boardString.append("+");
					if (line != 0 && column ==4)
						boardString.append("|");
				}
			boardString.append("\n");
			
			}
		}
		boardString.append(" +--------------+--------------+--------------+--------------+--------------+\n");
		boardString.append("\n Marker of "+players[0].getName()+": "+players[0].getPlayerMarker()+"/42, Marker of "+players[1].getName()+": "+players[1].getPlayerMarker()+"/42, Marker of "+players[2].getName()+": "+players[2].getPlayerMarker()+"/42");
		
		return boardString.toString();
	}

	/*
	 * AK Cool!
	 */
	protected boolean invariant(){
		int totalNbrOfAmebasRed = this.amoebaStackRed.size() + getNbrOfAmoebasOfAColorOnBoard(Game.Color.RED);
		int totalNbrOfAmebasGreen = this.amoebaStackGreen.size() + getNbrOfAmoebasOfAColorOnBoard(Game.Color.GREEN);
		int totalNbrOfAmebasYellow = this.amoebaStackYellow.size() + getNbrOfAmoebasOfAColorOnBoard(Game.Color.YELLOW);
		
		int totalNbrOfFoodCubesRed = this.foodStackRed.size() + this.getNbrOfFoodCubesOfAColorOnBoard(Game.Color.RED);
		int totalNbrOfFoodCubesGreen = this.foodStackGreen.size() + this.getNbrOfFoodCubesOfAColorOnBoard(Game.Color.GREEN);
		int totalNbrOfFoodCubesYellow = this.foodStackYellow.size() + this.getNbrOfFoodCubesOfAColorOnBoard(Game.Color.YELLOW);
		
		return(totalNbrOfAmebasRed==7 && totalNbrOfAmebasGreen==7 && totalNbrOfAmebasYellow==7 && totalNbrOfFoodCubesRed==55 && totalNbrOfFoodCubesGreen==55 && totalNbrOfFoodCubesYellow == 55);
	}
	
	/*
	 * Helper method for the #invariant
	 */
	
	private int getNbrOfFoodCubesOfAColorOnBoard(Game.Color color) {
		int nbrOfFoodCubesOfAColor = 0;
		for (int row = 0; row <5; row++){
			for(int col = 0; col<5; col++)
				nbrOfFoodCubesOfAColor += (board[row][col].getNbrOfFoodCubes(color));
		}
		return nbrOfFoodCubesOfAColor;
	}

	public int getNbrOfAmoebasOfAColorOnBoard(Game.Color color){
		ArrayList<Amoeba> tempAmoebas = new ArrayList<Amoeba>();
		int nbrOfAmebasOfAColor = 0;
		for (int row = 0; row <5; row++){
			for(int col = 0; col<5; col++)
				tempAmoebas.addAll(board[row][col].getAmoebas());
		}
		
		for (Amoeba a: tempAmoebas){
			if (a.getColor().equals(color))
				nbrOfAmebasOfAColor++;
		}
		return nbrOfAmebasOfAColor;
	}
	
	/*
	 * Getter to see the gene cards
	 */
	public ArrayList<GeneCard> getGeneCards(){
		return this.geneCardStack;
	}
	
	public GeneCard getGeneCard(int index){
		return this.geneCardStack.get(index);
	}
	
	public GeneCard removeGeneCard(int index){
		return this.geneCardStack.remove(index);
	}
	
	public void returnGeneCards(GeneCard gc){
		this.geneCardStack.add(gc);
	}
	
	public void returnGeneCards(ArrayList<GeneCard> agc){
		this.geneCardStack.addAll(agc);
	}
	
	public int getNbrOfGeneCards(){
		return this.geneCardStack.size();
	}
	
	/*
	 * Just for the tests to work properly:
	 */
	
	public Player[] getPlayers(){
		return this.players;
	}

	
}
