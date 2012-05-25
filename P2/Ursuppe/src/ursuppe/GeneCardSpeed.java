package ursuppe;

public class GeneCardSpeed extends GeneCard {

	public GeneCardSpeed(String gene, int price, int mutationPoints) {
		super(gene, price, mutationPoints);
	}
	
	public void applyCommand(Player player) {
		player.setMovementPoints(2);
	}

	@Override
	public void undoCommand(Player player) {
		player.setMovementPoints(1);
	}

}
