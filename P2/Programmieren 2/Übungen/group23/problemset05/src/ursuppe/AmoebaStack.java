package ursuppe;

/**
 * AmoebaStack is an Arraylist which is holding Amoebas
 * of a certain color. 
 */
public class AmoebaStack extends java.util.ArrayList<Amoeba>{
	
	private static final long serialVersionUID = -4407987903029035129L;
	Game.Color color;

	public AmoebaStack(Game.Color color){
		super();
		this.color = color;
	}

	public Game.Color getColor(){
		return this.color;
	}

}
