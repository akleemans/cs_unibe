package ursuppe;

/**
 * Represents food cubes with a single color each.
 *
 */
public class FoodCube {

	private Game.Color color;
	
	public FoodCube(Game.Color color){
		this.color = color;
	}

	public Game.Color getColor() {
		return color;
	}
	
	public String toString(){
		return color.toString();
	}
}
