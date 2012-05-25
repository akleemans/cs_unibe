package ursuppe;
import java.util.ArrayList;

/**
 * Provides a basic structure of a field on the board (which is extended i.e. in
 * CompassSquare). It basically handles the Amoebas and the food cubes.
 */
public class Square{
	
	private ArrayList<Amoeba> amoebas = new ArrayList<Amoeba>();
	private ArrayList<FoodCube> foodCubes = new ArrayList<FoodCube>();
	
	//private Square[][] board;
	private int positionRow;
	private int positionColumn;

	/*
	 * Removes the indicated Amoeba from this Square
	 */
	public Amoeba removeAmoebaOfColorAndNumber(Game.Color color, int number) {
		Amoeba tempAmeba = null;
		
		for (int i=0;i<amoebas.size();i++){
			if (amoebas.get(i).getColor().equals(color) && amoebas.get(i).getNumber()==number){
				tempAmeba = amoebas.remove(i);
			}
		}
		return tempAmeba;
	}
	
	/*
	 * Helper method of #amoebaEat
	 */
	public ArrayList<FoodCube> removeEatenFoodCubes(String tempMethabolismString) {
		ArrayList<FoodCube> removedCubes = new ArrayList<FoodCube>();
		if (!tempMethabolismString.equals("noFeeding")){
			removedCubes.addAll(this.removeEatenHelper(tempMethabolismString, Game.Color.RED));
			removedCubes.addAll(this.removeEatenHelper(tempMethabolismString, Game.Color.GREEN));
			removedCubes.addAll(this.removeEatenHelper(tempMethabolismString, Game.Color.YELLOW));
		}
		
		assert removedCubes.size() == 3;
		return removedCubes;
	}
	
	/*
	 * Helper method of #removeEatenFoodCubes
	 */
	private ArrayList<FoodCube> removeEatenHelper(String tempMethabolismString, Game.Color color) {
		ArrayList<FoodCube> removedCubes = new ArrayList<FoodCube>();
		
		for(int i=0;i<3;i++){
			if (tempMethabolismString.split(" ")[i].equals(color.toString())){
				for (int j=0;j<foodCubes.size();j++){
					if (foodCubes.get(j).getColor().equals(color)){
						removedCubes.add(foodCubes.remove(j));
						break;
					}
				}	
			}
		}
	
		return removedCubes;
	}
	
	/*
	 * This method adds two foodCubes from the board's
	 * foodStack to this Square.
	 */
	public void amoebaExcrete(ArrayList<FoodCube> excretedFoodCubes){
		this.foodCubes.addAll(excretedFoodCubes);
	}
	
	/*
	 * Returns whether an Amoeba with a certain color and number
	 * is on this Square or not.
	 */
	public boolean amoebaWithColorAndNumberIsOnField(Game.Color color, int number){
		boolean returner = false;
		for (Amoeba a: amoebas){
				if (a.getColor().equals(color) && a.getNumber() == number)
				returner = true;
			}
		return returner;
	}
	
	/*
	 * Overloaded method (wrongly named), returns whether there is at least one 
	 * Amoeba with color @color on this Square.
	 */
	public boolean amoebaWithColorAndNumberIsOnField(Game.Color color){
		boolean returner = false;
		for (Amoeba a: amoebas){
				if (a.getColor().equals(color))
				returner = true;
			}
		return returner;
	}
	
	/*
	 * Orders the Amoebas on this field to die, if they have
	 * too many damage points.
	 */
	public ArrayList<Amoeba> orderAmoebasToDieIfTooMuchDamage(Player[] players) {
		ArrayList<Amoeba> deadAmebas = new ArrayList<Amoeba>();
		for(int i=0;i<amoebas.size();i++){
			if(amoebas.get(i).getDamagePoints() > amoebas.get(i).maxDamagePoints()){
				deadAmebas.add(amoebas.remove(i));
				i--;
			}
		}
		
		for (Amoeba a : amoebas){
			for (Player p: players){
				if (!p.hasGene("LifeExpectancy") && a.getColor().equals(p.getColor()))
					assert a.getDamagePoints() <= 1;
				else
					assert a.getDamagePoints() <= 2;
			}
		}
		
		

		return deadAmebas;
		}
	
	/*
	 * Returns the number of FoodCubes on this Square
	 */
	public int getNbrOfFoodCubes(Game.Color color){
		return nbrOfFoodCubesOfAColor(color);
	}
	
	/*
	 * indicates if this Square is a normal square or an empty
	 * or a compass square, and an obstacle for Amoebas though
	 */
	public boolean isIngameSquare(){
		return true;
	}
	
	/*
	 * Returns if this square is occupied by an Amoeba
	 */
	public boolean isUnoccupied(){
		return (amoebas.size() == 0);
	}
	
	/*
	 * Two methods that give back how many Amoebas and FoodCubes
	 * that are on this Square
	 */
	
	private int nbrOfAmebasOfAColor(Game.Color color){
		int amebasOfColor = 0;
		for(Amoeba a : amoebas) {
		    if (a.getColor().equals(color)){
		    	amebasOfColor++;
		    }
		}
		return amebasOfColor;
	}
	
	private int nbrOfFoodCubesOfAColor(Game.Color color){
		int foodCubesOfColor = 0;
		for(FoodCube f : foodCubes) {
		    if (f.getColor().equals(color))
		    	foodCubesOfColor++;
		}
		return foodCubesOfColor;
	}
	
	/*
	 * Some small methods, getters and setters etc.
	 */
	public void addFoodCube(FoodCube foodCube){
		foodCubes.add(foodCube);
	}
	
	public ArrayList<Amoeba> getAmoebas(){
		return this.amoebas;
	}
	
	public String getPosition(){
		return positionRow+" "+positionColumn;
	}
	
	public void addAmeba(Amoeba ameba){
		amoebas.add(ameba);
	}
	
	/*
	 * Method to determine the position of this square by the Board class
	 * at the beginning of the game.
	 */
	public void setPosition(Square[][] board, int column, int row){
		this.positionColumn = column;
		this.positionRow = row;
	}
	
	/*
	 * Adds a damage point to the indicated Amoeba on this Square
	 */
	public void addDamagePoint(Game.Color color, int amoebaNumber) {
		for(int i=0;i<amoebas.size();i++){
			if (amoebas.get(i).getColor().equals(color) && amoebas.get(i).getNumber()==amoebaNumber)
				this.amoebas.get(i).setDamagePoint();
		}
	}
	
	/*
	 * Methods to display this Square in ASCII format, line for line
	 */
	public String Line(int line){
		if (line==0)
			return this.firstLine();
		if (line==1)
			return this.secondLine();
		if (line==2)
			return this.thirdLine();
		if (line==3)
			return this.fourthLine();
		else
			return "error";
	}
	
	private String firstLine(){
		return("+--------------");
	}
	
	private String secondLine(){
		int R = this.nbrOfAmebasOfAColor(Game.Color.RED);
		int r = this.nbrOfFoodCubesOfAColor(Game.Color.RED);
		
		String pr;
		if (this.nbrOfFoodCubesOfAColor(Game.Color.RED) < 10)
			pr = "0";
		else
			pr = "";
		
		return("| R: "+R+",  r: "+pr+r+" ");
	}
	
	private String thirdLine(){
		int G = this.nbrOfAmebasOfAColor(Game.Color.GREEN);
		int g = this.nbrOfFoodCubesOfAColor(Game.Color.GREEN);
		
		String pg;
		if (this.nbrOfFoodCubesOfAColor(Game.Color.GREEN) < 10)
			pg = "0";
		else
			pg = "";
		
		return("| G: "+G+",  g: "+pg+g+" ");
	}
	
	private String fourthLine(){
		int Y = this.nbrOfAmebasOfAColor(Game.Color.YELLOW);
		int y = this.nbrOfFoodCubesOfAColor(Game.Color.YELLOW);
		
		String py;
		if (this.nbrOfFoodCubesOfAColor(Game.Color.YELLOW) < 10)
			py = "0";
		else
			py = "";
		
		return("| Y: "+Y+",  y: "+py+y+" ");
	}
	
	/*
	 * Three methods that are not used in the Square class, but only
	 * in the child class CompassSquare actually.
	 */
	
	public void addEnvironmentCard(EnvironmentCard card){
	}
	
	public Game.Direction getDirection(){
		return null;
	}

	public int getOzoneLayerThickness() {
		return 0;
	}

	public ArrayList <EnvironmentCard> removeEnvironmentCards() {
		return null;
	}

}
