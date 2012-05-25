package ursuppe;

/**
 * Provides structured information about the cards which are
 * instantiated (gene type, price and mutation points).
 *
 */
public class GeneCard {
	
	private String gene;
	private int price;
	private int mutationPoints;
	
	public GeneCard(String gene, int price, int mutationPoints){
		this.gene = gene;
		this.price = price;
		this.mutationPoints = mutationPoints;
	}
	

	public String getGene(){
		return gene;
	}
	
	public int getMutationPoints(){
		return mutationPoints;
	}
	
	public int getPrice(){
		return price;
	}
	
	public void applyCommand(Player player){}
	
	public void undoCommand(Player player){}
	

}
