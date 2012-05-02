package ursuppe;
/**
 * This class represents 'Amoebas' on the game,
 * saving their color, number and damage points.
 */
public class Amoeba {
	
	private Game.Color color;
	private int number;
	private int damagePoints;
	private int maxDamagePoints = 1;
	
	public Amoeba(Game.Color color, int number){
		this.color = color;
		this.number = number;
	}
	
	public Game.Color getColor() {
		return this.color;
	}
	
	public int getNumber(){
		return this.number;
	}
	
	public void setDamagePoint(){
		damagePoints++;
	}
	
	public int getDamagePoints(){
		return damagePoints;
	}

	public void resetDamagePoints() {
		damagePoints=0;
	}

	public int maxDamagePoints() {
		return maxDamagePoints;
	}
	
	public void setMaxDamagePoints(int maxDamagePoints){
		this.maxDamagePoints = maxDamagePoints;
	}

}
