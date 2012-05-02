package ancestor;

public class Person {
	
	private int mom;
	private int dad;
	
	public Person(int mum, int dad) {
		this.mom = mum;
		this.dad = dad;
	}
	
	public void setMom(int newMom) {
		this.mom = newMom;
	}
	public int getMom() {
		return mom;
	}
	
	public void setDad(int newDad) {
		this.mom = newDad;
	}
	public int getDad() {
		return dad;
	}
}
