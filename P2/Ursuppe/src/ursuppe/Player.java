package ursuppe;

import java.util.ArrayList;
import ursuppe.Game.Direction;

/**
 * The Player-class provides a virtual or real representation of a 
 * Player-entity, which can take several actions such as place Amoebas 
 * or roll a die, move, buy cards, choose color, and, nevertheless,
 * can provide this information given an existing instance.
 *
 */
public class Player implements Comparable<Player>{
	
	private Game.Color color;
	private String name;
	private int bp;
	private int playerMarker=0;
	private ArrayList<GeneCard> geneCards = new ArrayList<GeneCard>();
	
	private int rolledDies;
	private int rolledDies2ndAttempt;
	
	private Amoeba amoebaStack[] = new Amoeba[7];
	private int diesAvailableWhenMoving = 1;
	private int movementPoints = 1;

	
	public Player(String name){
		this.name = name;
		this.bp = 4;
	}
	
	/*
	 * Places an amoeba to a square that is not
	 * occupied at the beginning of the game.
	 * (This method is called twice before the game
	 * starts)
	 */
	public Board placeAmoeba(Board board) {
		int row; int col;
		Board returnChangedBoard = board;
		
		do {
				row = (int) (Math.random()*5);
				col = (int) (Math.random()*5);
				} while (!board.squareIsUnoccuppied(row, col) || !board.squareIsInGameSquare(row, col));
		System.out.println(this.getName()+" put a "+this.getColor()+" ameba to square "+row+"/"+col);
		returnChangedBoard.addAmebaToSquare(row, col, this.getColor());
		
		return returnChangedBoard;
	}
	
	/*
	 * All classes to determine the starting player at the beginning of the game
	 */
	public int rollDie() {
		Die die = new Die();
		return die.roll();
	}
	
	public void setRolledDies(int rolledDies){
		this.rolledDies = rolledDies;
	}
	
	public void setRolledDies2ndAttempt(int rolledDies2ndAttempt) {
		this.rolledDies2ndAttempt = rolledDies2ndAttempt;
	}
	
	public int getRolledDies2ndAttempt(){
		return this.rolledDies2ndAttempt;
	}
	
	public int getRolledDies(){
		return rolledDies;
	}
	
	/*
	 * AK You often use the `Board` parameter. You might consider to make it an instance variable instead.
	 */
	/*
	 * Decides whether to drift or move an Amoeba and calls the according method
	 */
	public Board driftOrMove(Board board) {
		
		for(int amoebaNumber=1;amoebaNumber<8;amoebaNumber++){
			/*
			 * AK "empty" as a special value is questionable and I actually doubt that 
			 * using strings to send value pairs around is the best way. I'd suggest adding
			 * a Pair or Point class or make it two different methods all together.
			 */
			if(board.getPositionOfAmebaOfColorAndNumber(this.color, amoebaNumber) != "empty"){
				if(this.bp>0 && moveDecision())
					this.move(board, amoebaNumber);
				else{
					System.out.print(this.name+" decided to make "+this.color+amoebaNumber+" drift: ");
					board.determineDirectionOfAmoebaDisplacement(this.color, amoebaNumber);
				}
			}
		}
		
		return board;
	}

	private boolean moveDecision() {
		return(Math.random()>0.5);
	}
	
	// AK no need to say what the method name says.
	/*
	 * Moves an Amoeba
	 */
	private void move(Board board, int amoebaNumber) {
		this.bp--;
		System.out.print(this.name+" decided to make "+this.color+amoebaNumber+" move.");
		try {
			for(int i=1;i<=this.movementPoints;i++){
				if (this.hasGene("Speed") && moveAgain())
					System.out.print(" "+this.name+" possesses the 'Speed' gene and moves again:");
					
				moveHelper(board, amoebaNumber);
			}
			
			
		} catch (Exception e) {
			/*
			 *  AK maybe make this one line to show that you want to ignore that case.
			 *  This looks a bit like you forgot to write the handling code, but
			 *  `catch (Exception e) { }` shows that you don't want to write anything in it.
			 */
		}
	}

	private void moveHelper(Board board, int amoebaNumber) throws Exception {
		int nbrOfDice;
		
		if(this.diesAvailableWhenMoving==1)	/* We are aware of the 'inelegantness' of this if statement. However, we leave it here, because
									it fits better with the old code. Furthermore, to rebuild the old output, we would simply
									need another if-statement, if we used a loop that runs from i=i to i<=this.movementPoints*/
			{System.out.print(" The player threw a "); nbrOfDice=1;}
		else
			{System.out.print(" The player posseses the 'Movement1' gene. He threw "); nbrOfDice=2;}
		board.determineDirectionOfAmoebaDisplacement(this.color, amoebaNumber, this.determineDirection(this.rollDice(nbrOfDice)));
	}
	
	/*
	 * Some helper methods for moving an Amoeba
	 */
	
	
	private boolean moveAgain() {
		return (Math.random() >0.5);
	}

	private int rollDice(int nbrOfDice){
		
		assert (nbrOfDice==1 || nbrOfDice==2);
		
		int firstDie;
		int secondDie;
		
		if (nbrOfDice==1)
			return new Die().roll();
		else{
			firstDie = new Die().roll();
			secondDie = new Die().roll();
			System.out.print(firstDie+" & "+secondDie);
			System.out.print(" and chose ");
			if (chooseFirstDie())
				return firstDie;
			else
				return secondDie;
		}
		
	}
	
	private boolean chooseFirstDie() {
		return(Math.random()>0.5);
	}

	private Game.Direction determineDirection(int eyes) throws Exception {
		switch(eyes){
		/*
		 * AK The player and other classes have a massive output to the System.out, which makes
		 * your tests very verbose. You should use Dependency Injection or a Singleton
		 * Output class to shut it up. The simplest way to do that is probably to define a
		 * 
		 * class Output {
		 * 		private static printout = false;
		 * 		public static mute() { printout = false; }
		 * 		public static unmute() { printout = true; }
		 * 		public static print(String s) {
		 * 			if (printout) 
		 * 				System.out.print(s);
		 * 		}
		 * }
		 */
			case 1: System.out.print("1, that means WEST: "); return Game.Direction.WEST;
			case 2: System.out.print("2, that means NORTH: "); return Game.Direction.NORTH;
			case 3: System.out.print("3, that means EAST: "); return Game.Direction.EAST;
			case 4: System.out.print("4, that means SOUTH: "); return Game.Direction.SOUTH;
			case 5: System.out.println("5, that means the amoeba stays where it is."); throw new Exception(); 
						// AK is the case eyes == 5 really that exceptional? You shouldn't use Exceptions as
						//    alternative return values -- use Game.Direction.STAY or null for that.
			case 6: System.out.print("6. The player chose as direction: "); return this.randomDirection();
			default: return null;
			}
	}

	private Direction randomDirection() {
		switch((int)(Math.random()*4+1)){
		case 1: System.out.print("WEST: "); return Game.Direction.WEST;
		case 2: System.out.print("NORTH: "); return Game.Direction.NORTH;
		case 3: System.out.print("EAST: "); return Game.Direction.EAST;
		case 4: System.out.print("SOUTH: "); return Game.Direction.SOUTH;
		default: return null;
		}
	}

	/*(*)
	 * Tells every amoeba of this Player's color what to eat by
	 * calling the #eatingDecision method.
	 * 
	 * This method also orders the #removeEatenFoodCubes and
	 * the #addExcretedFoodCubesToSquare methods of the Board
	 * class to organize the distribution of food cubes
	 * after the metabolism of the amoeba.
	 */
	public Board makeAmoebasEat(Board board) {
		
		int row;
		int col;
		String methabolismString;
		
		for(int amoebaNumber=1;amoebaNumber<8;amoebaNumber++){
			if(board.getPositionOfAmebaOfColorAndNumber(this.getColor(), amoebaNumber) != "empty"){
				// AK @see #driftOrMove. Here you see that it makes your work actually easier to use fitting classes.
				row = Integer.parseInt(board.getPositionOfAmebaOfColorAndNumber(this.getColor(), amoebaNumber).split(" ")[0]);
				col = Integer.parseInt(board.getPositionOfAmebaOfColorAndNumber(this.getColor(), amoebaNumber).split(" ")[1]);
				methabolismString = this.eatingDecision(row,col,board);
				if (methabolismString.equals("noFeeding")){
					board.addDamagePoint(this.getColor(),amoebaNumber,row,col);
					System.out.println("Amoeba "+this.getColor()+amoebaNumber+" on "+row+"/"+col+" cound not find enough food");
				}
				else{
					System.out.print(this.getName()+" made amoeba "+this.getColor()+amoebaNumber+" on "+row+"/"+col+" eat: "+methabolismString);
					board.removeEatenFoodCubes(methabolismString, row,col);
					board.addExcretedFoodCubesToSquare(this.getColor(), row, col);
				}
			}
		}
		
		return board;
	}

	/*
	 * Decides of which color the amoeba on Square row/col shall eat
	 * two food cubes and of which color the amoeba shall eat only
	 * one food cube.
	 */
	private String eatingDecision(int row, int col, Board board) {
		
		StringBuilder methabolismString = new StringBuilder();
		int foodsource1 = 0, foodsource2 = 0;
		String food1 = null, food2 = null;
		
		switch(this.getColor()){
		case RED: foodsource1 = board.getNbrOfFoodCubesOnASquare(Game.Color.GREEN, row, col); food1="green "; foodsource2 = board.getNbrOfFoodCubesOnASquare(Game.Color.YELLOW, row, col); food2="yellow "; break; // AK You probably don't see this comment, which is kind of the point of it: This method does not fit on my screen -- which is a bad sign, as what I can see and what I can understand is often very much the same thing -- so you should write the method in a way so that one can grasp the whole code at once. 
		case GREEN: foodsource1 = board.getNbrOfFoodCubesOnASquare(Game.Color.YELLOW, row, col); food1="yellow "; foodsource2 = board.getNbrOfFoodCubesOnASquare(Game.Color.RED, row, col); food2="red "; break;
		case YELLOW: foodsource1 = board.getNbrOfFoodCubesOnASquare(Game.Color.RED, row, col); food1="red "; foodsource2 = board.getNbrOfFoodCubesOnASquare(Game.Color.GREEN, row, col); food2="green "; break;
		}
		
		if((foodsource1+foodsource2)>2 && foodsource1>=1 && foodsource2>=1){
				
			if(foodsource1>=2 && foodsource2==1)
				methabolismString.append(food1+food1+food2);
			else if(foodsource2>=2 && foodsource1==1)
				methabolismString.append(food2+food2+food1);
			else{
				if (Math.random()>0.5)
					methabolismString.append(food1+food1+food2);
				else
					methabolismString.append(food2+food2+food1);
			}
		}
			
		else
				methabolismString.append("noFeeding");
			
	return methabolismString.toString();
	}
	
	/*
	 * Pay for Gene defects in phase 2.
	 */
	public ArrayList<GeneCard> payForGeneDefects(int ozoneLayerThickness){
		ArrayList<GeneCard> geneCardsBack = new ArrayList<GeneCard>();
		int difference = getMutationPoints()-ozoneLayerThickness;
		while(difference>0){
			if (bp>difference){
				if(giveBackGeneCardsDecision()){
					difference-=this.geneCards.get(0).getMutationPoints();
					System.out.println("The ozone layer thickness is "+ozoneLayerThickness+" while "+this.name+" has "+this.getMutationPoints()+" mutation points. "+this.name+" gave back the '"+this.geneCards.get(0).getGene()+"' gene.");
					geneCardsBack.add(this.geneCards.remove(0));
				}
				else{
					System.out.println("The ozone layer thickness is "+ozoneLayerThickness+" while "+this.name+" has "+this.getMutationPoints()+" mutation points. "+this.name+" thus paid "+difference+" bps.");
					bp-=difference; difference = 0;
				}
			}
			else{
				System.out.println("The ozone layer thickness is "+ozoneLayerThickness+" while "+this.name+" has "+this.getMutationPoints()+" mutation points. "+this.name+" gave back the '"+this.geneCards.get(0).getGene()+"' gene.");
				difference-=this.geneCards.get(0).getMutationPoints();
				geneCardsBack.add(this.geneCards.remove(0));
			}
			
			
		}
		
		for(GeneCard gc : geneCardsBack){
			gc.undoCommand(this);
		}
		
		return geneCardsBack;
			
	}

	private boolean giveBackGeneCardsDecision() {
		return (Math.random()>0.9);
	}

	/*
	 * Returns the number of mutation points on the gene cards of this player
	 */
	private int getMutationPoints(){
		int mPoints=0;
		for (GeneCard gc : this.geneCards)
			mPoints+=gc.getMutationPoints();
		return mPoints;
	}
	
	/*
	 * Decides to buy any GeneCards
	 */
	public Board buyGeneCards(Board board) {
		for(int i = 0;i<board.getNbrOfGeneCards();i++){
			if(this.bp>=board.getGeneCard(i).getPrice() && buyDecision() && doesNotAlreadyHaveThisGene(board.getGeneCard(i))){
				this.bp -= board.getGeneCard(i).getPrice();
				System.out.println(this.name+" bought the '"+board.getGeneCard(i).getGene()+"' gene.");				
				this.geneCards.add(board.removeGeneCard(i));
				i--;
			}
		}
		
		for(GeneCard gc : this.geneCards){
			gc.applyCommand(this);
		}
		return board;
	}
	
	private boolean buyDecision() {
		return (Math.random()>0.9);
	}
	
	private boolean doesNotAlreadyHaveThisGene(GeneCard geneCard) {
		boolean returner = true;
		for (GeneCard gc : this.geneCards){
			if (gc.getGene().equals(geneCard.getGene()))
				returner = false; break;
		}
		return returner;
	}

	/*
	 * Decides how many Amoebas to divide.
	 * Calls the #directionOfDivision method then to determine
	 * in which direction a certain Amoeba shall be devided
	 * and returns the board after this player has done all his
	 * amoeba divisions.
	 */
	public Board divideAmoeba(Board board) {
		int row = 0;
		int col = 0;
		
		Board returnChangedBoard = board;
		
		for(int i=1;i<=7-board.getNbrOfAmoebasOfAColorOnBoard(this.getColor());i++){
			
			if (divideDecision() && this.bp>=6 && board.getNbrOfAmoebasOfAColorOnBoard(this.getColor())>0){
				assert bp>= 6; assert board.getNbrOfAmoebasOfAColorOnBoard(this.getColor())<7;
				
				String numberAndPosition = this.chooseAmoebaToDivide(board);
				
				row = Integer.parseInt(numberAndPosition.split(" ")[1]);
				col = Integer.parseInt(numberAndPosition.split(" ")[2]);
				
				if (board.getNbrOfAmoebasOfAColorOnBoard(this.color)==1 || this.hasGene("Spores")){
					System.out.print(this.getName()+" ("+this.getColor()+") divided an amoeba to ");
					do {
						row = (int) (Math.random()*5);
						col = (int) (Math.random()*5);
						} while (board.obstacleOrSameAmebaOnField(row, col, this.color));
					if(board.getNbrOfAmoebasOfAColorOnBoard(this.color)==1)
						System.out.println(row+"/"+col+" (does not have to be an adjacent square, because there is only one "+this.getColor()+" ameba on the board)");
					if(this.hasGene("Spores"))
						System.out.println(row+"/"+col+" (does not have to be an adjacent square, because this player possesses the 'Spores' gene)");
					returnChangedBoard.addAmebaToSquare(row, col, this.getColor());
					this.removeBP(6);
				}
				else{
					
					Game.Direction direction = this.directionOfDivision(board, row, col);
					
					if(direction!=null){
						System.out.print(this.getName()+" ("+this.getColor()+") divided an amoeba to ");
						switch(direction){
							case WEST: System.out.println(row+"/"+(col-1)); returnChangedBoard.addAmebaToSquare(row, col-1, this.getColor()); break;
							case NORTH: System.out.println((row-1)+"/"+col); returnChangedBoard.addAmebaToSquare(row-1, col, this.getColor()); break;
							case EAST: System.out.println(row+"/"+(col+1)); returnChangedBoard.addAmebaToSquare(row, col+1, this.getColor()); break;
							case SOUTH: System.out.println((row+1)+"/"+col); returnChangedBoard.addAmebaToSquare(row+1, col, this.getColor()); break;
						}
					this.removeBP(6);
					}
				}
			}
			else{
				if (board.getNbrOfAmoebasOfAColorOnBoard(this.getColor())==0){
					System.out.println("No "+this.getColor()+" amebas on board. No "+this.getColor()+" amebas to divide though.");
					break;
				}
			}
		}
		return returnChangedBoard;
		/*
		 * AK This method is too long, you could either try to simplify it or split it into different methods that
		 * are small enough to understand as a whole and in which their names document their purpose. Use
		 * Eclipses
		 * 		1) Mark lines of code
		 * 		2) Refactor - Extract Method...
		 * to ease the job.   
		 */
	}
	
	

	/*
	 * Decision to put another amoeba to the board during phase 4
	 * of the game
	 */
	public boolean divideDecision(){
		return (Math.random()>0.3);
	}
	
	/*
	 * Decides which Amoeba to divide
	 */
	private String chooseAmoebaToDivide(Board board) {
		
		String positionString = "empty";
		int randomNumberOneToSeven = 0;
		
		while (positionString.equals("empty")){
			randomNumberOneToSeven = ((int)((Math.random()*7))+1);
			positionString = board.getPositionOfAmebaOfColorAndNumber(this.color, randomNumberOneToSeven);
		}
		
		return Integer.toString(randomNumberOneToSeven)+" "+positionString;
	}
	
	/*
	 * Decides in which direction the Amoeba shall be divided.
	 */
	private Game.Direction directionOfDivision(Board board, int row, int col) {
		
		Game.Direction returner = null;
		
		if(!board.obstacleOrSameAmebaOnField(row, col-1, this.color) || !board.obstacleOrSameAmebaOnField(row-1, col, this.color) || !board.obstacleOrSameAmebaOnField(row, col+1, this.color) || !board.obstacleOrSameAmebaOnField(row+1, col, this.color)){
			while(returner==null){	
				// AK This is of course not very elegant, but Java makes it very hard to do something
				//    fancy here. I guess, making a list of viable alternatives and choosing one
				//    randomly is even more verbose... but might be more readable and less boring to write
				//    anyway! Also... `returner`?
				if (((int)((Math.random()*5))) == 1 && !board.obstacleOrSameAmebaOnField(row, col-1, this.color))
					returner = Game.Direction.WEST;
				if (((int)((Math.random()*5))) == 2 && !board.obstacleOrSameAmebaOnField(row-1, col, this.color))
					returner = Game.Direction.NORTH;
				if (((int)((Math.random()*5))) == 3 && !board.obstacleOrSameAmebaOnField(row, col+1, this.color))
					returner = Game.Direction.EAST;
				if (((int)((Math.random()*5))) == 4 && !board.obstacleOrSameAmebaOnField(row+1, col, this.color))
					returner = Game.Direction.SOUTH;
			}
		}
		return returner;
	}
	
	/*
	 * AK Is this just for the initial setup? And what about is it exactly?
	 */
	/*
	 * Methods about the score marker of this player:
	 */
	
	public void choosePlayerMarkerPosition(Player[] players){
		int tempPlayerMarker = 0;
		do {
			tempPlayerMarker = (int)(1+Math.random()*3); // AK this point is as good as any to tell you about the Random#nextInt(int upper) method. It's rad!
			} while (otherPlayerMarkerWithThatValue(tempPlayerMarker, players));
		
		this.playerMarker = tempPlayerMarker;
		System.out.println(this.getName()+" set the "+this.color+" Score marker to "+this.playerMarker);
	}
	
	private boolean otherPlayerMarkerWithThatValue(int playerMarker,
			Player[] players) {
		boolean returner = false;
		for(Player player : players){
			if (player.getPlayerMarker() == playerMarker)
				returner = true;
		}
		return returner;
	}
	
	public int getPlayerMarker(){
		return this.playerMarker;
	}
	
	public void addToPlayerMarker(int addedPoints){
		this.playerMarker += addedPoints;
	}
		
	
	/*
	 * Next two methods return the name and color of this Player
	 * AK I guess so. Otherwise `getName` and `getColor` might be a bit
	 * of a misnomer. You don't have to comment on things that are that
	 * trivial.
	 */
	
	public String getName(){
		return name;
	}
	
	public Game.Color getColor(){
		return color;
	}
	
	/*
	 * Sets the color of this Player at the beginning of the game
	 */
	public void chooseColor(Game.Color color) {
		this.color = color;
	}
	
	/*
	 * Removes and adds BP's for this Player:
	 */
	
	public void addBP(int number){
		this.bp+=number;
	}
	
	public void removeBP(int number){
		this.bp-=number;
	}
	
	public boolean hasGene(String gene){
		boolean returner = false;
		if (this.geneCards.size()!=0){
			for (GeneCard gc : geneCards){
				if (gc.getGene().equals(gene))
					returner=true;
			}
		}
		return returner;
	}
	
	/*
	 * AK Idiomatically, you'd call this method something like
	 * `getGeneCardCount()` or
	 * `countGeneCards()`
	 */
	public int getNbrOfGeneCards(){
		return this.geneCards.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * The toString method returns the name of this
	 * Player
	 */	
	public String toString(){
		return this.getName();
	}
	
	/*
	 * AK This is a cool idea. You could also use two Comparator<Player> to implement
	 * two different methods for the different cases.
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * Compare-Method. Before the first round players
	 * shall be compared according to the number of eyes their dies
	 * show. Afterwards, they shall be compared by the position
	 * of their score marker.
	 */
	@Override
	public int compareTo(Player comparePlayer) {
		if (this.playerMarker==0){
			if( this.getRolledDies() < comparePlayer.getRolledDies() )
	            return 1;
	        if( this.getRolledDies() > comparePlayer.getRolledDies() )
	            return -1;
		}
		else{
			if( this.getPlayerMarker() > comparePlayer.getPlayerMarker())
		        return -1;
		    if( this.getPlayerMarker() < comparePlayer.getPlayerMarker())
		        return 1;
		}
			return 0;
	}

	public Amoeba[] getAmoebaStack(){
		return this.amoebaStack;
	}

	public void addAmoebaStack(AmoebaStack as) {
		for (int i=0;i<7;i++)
			this.amoebaStack[i]=as.get(i);
	}

	public void setDiesAvailableWhenMoving(int i) {
		this.diesAvailableWhenMoving = i;
		
	}

	public void setMovementPoints(int i) {
		this.movementPoints = i;
		
	}

	

}
