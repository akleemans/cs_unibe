package ursuppe;

public class GeneCardLifeExpectancy extends GeneCard {

	public GeneCardLifeExpectancy(String gene, int price, int mutationPoints) {
		super(gene, price, mutationPoints);
	}
	
	public void applyCommand(Player player) {
		
		for(Amoeba a : player.getAmoebaStack()){
			a.setMaxDamagePoints(2);
		}
		
	}

	@Override
	public void undoCommand(Player player) {
		for(Amoeba a : player.getAmoebaStack()){
			a.setMaxDamagePoints(1);
		}
		
	}
	

}
