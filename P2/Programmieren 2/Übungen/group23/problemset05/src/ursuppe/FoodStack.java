package ursuppe;

public class FoodStack extends java.util.ArrayList<FoodCube>{
	
	/**
	 * Holds an array of FoodCube elements.
	 */
	private static final long serialVersionUID = -3107620093314973011L;
	Game.Color color;

	public FoodStack(Game.Color color){
		super();
		this.color = color;
	}

	public Game.Color getColor(){
		return this.color;
	}

}
