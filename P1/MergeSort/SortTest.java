import java.util.ArrayList;
import java.util.List;

public class SortTest {
	public static void main (String[] args) {
		//sort an array of strings
		String[] wordlist = {"Z", "C", "B", "A", "X", "M", "F"};
		System.out.println("Word list before sorting: ");
		for(int i=0; i<wordlist.length; i++) System.out.println(wordlist[i]);
		MergeSort.sort(wordlist);
		System.out.println("Word list after sorting: ");
		for(int i=0; i<wordlist.length; i++) System.out.println(wordlist[i]);

		//sort an array of rectangles
		Rectangle[] rectangles = {new Rectangle(100,55),
						new Rectangle(3,4),
						new Rectangle(30,24),
						new Rectangle(14,9),
						new Rectangle(90,3),
						new Rectangle(34,12),
						new Rectangle(1,2),
						new Rectangle(31,24) };
		System.out.println("\nRectangles before sorting:");
		for(int i=0; i<rectangles.length; i++) System.out.println(rectangles[i]);
		MergeSort.sort(rectangles);
		System.out.println("\nRectangles after sorting:");
		for(int i=0; i<rectangles.length; i++) System.out.println(rectangles[i]);
	}
}

class Rectangle implements Comparable<Rectangle> {
	private int length;
	private int width;
	
	public Rectangle (int length, int width){
		this.length = length;
		this.width = width;
	}

	public int getArea(){
		return this.length*this.width;
	}
	
	public String toString(){
		return length+" x "+width+", area: "+this.getArea();
	}
	
	public int compareTo(Rectangle other){
		return this.getArea() - other.getArea();
	}
}