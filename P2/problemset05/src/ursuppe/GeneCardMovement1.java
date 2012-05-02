package ursuppe;

public class GeneCardMovement1 extends GeneCard {

	public GeneCardMovement1(String gene, int price, int mutationPoints) {
		super(gene, price, mutationPoints);
	}
	
	public void applyCommand(Player player) {
		player.setDiesAvailableWhenMoving(2);
	}

	@Override
	public void undoCommand(Player player) {
		player.setDiesAvailableWhenMoving(1);
	}

}
