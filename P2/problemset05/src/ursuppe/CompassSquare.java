package ursuppe;

import java.util.ArrayList;

/**
 * Special Square which holds the Compass of the Game and some Stacks
 * placed on it. For more information for the Compass, see the
 * rules of the Game Ursuppe.
 *
 */
public class CompassSquare extends Square{
	
	private ArrayList<EnvironmentCard> environmentCards = new ArrayList<EnvironmentCard>();
	
	public void addEnvironmentCard(EnvironmentCard card){
		environmentCards.add(card);
	}
	
	public Game.Direction getDirection(){
		return environmentCards.get(this.environmentCards.size()-1).getDirection();
	}
	
	public int getOzoneLayerThickness(){
		return environmentCards.get(this.environmentCards.size()-1).getOzoneLayer();
	}
	
	public boolean isIngameSquare(){
		return false;
	}
	
	/*
	 * Returns, line for line, a graphical representation of
	 * this compass square.
	 */
	public String Line(int line){
		switch(line){
		case 0: return("+--------------");
		case 1: return("| OzoneLr: "+String.valueOf(environmentCards.get(this.environmentCards.size()-1).getOzoneLayer())+"   "); 
		case 2: return("|              ");
		case 3: return("| Drction: "+environmentCards.get(this.environmentCards.size()-1).getDirection().toString().toUpperCase().charAt(0)+"   ");
		default: return("ERROR - NO LINE!");

		}
	}

}
