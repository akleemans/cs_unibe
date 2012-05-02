package ursuppe;

/**
 * The Class provides a composite data type, holding all information
 * needed for representing a "environment card" (see Ursuppe game rules).
 * Direction and ozone layer thickness are stored and can be provided.
 *
 */
public class EnvironmentCard {

	private Game.Direction direction;
	private int ozoneLayerThickness;
	
	public EnvironmentCard(Game.Direction direction, int ozoneLayerThickness){
		this.direction = direction;
		this.ozoneLayerThickness = ozoneLayerThickness;
	}
	
	public Game.Direction getDirection(){
		return direction;
	}
	
	public int getOzoneLayer(){
		return ozoneLayerThickness;
	}
	
}
