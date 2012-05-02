package ancestor;

import java.util.ArrayList;
import java.util.List;

public class Generation {
	
	private int id;
	List<Person> generation;
	
	public Generation(int id) {
		populate();
		this.id = id;
	}
	
	public void populate() {
		generation = new ArrayList<Person>();
		
		for (int i=0; i < Main.POPULATION; i++) {
			int m = (int)(Math.random() * Main.POPULATION);
			int d = (int)(Math.random() * Main.POPULATION);
			generation.add(new Person(m, d));
		}
	}
	
	public int getID() {
		return id;
	}
	
	public int getDad(int id) {
		return generation.get(id).getDad();
	}
	
	public int getMom(int id) {
		return generation.get(id).getMom();
	}
}
