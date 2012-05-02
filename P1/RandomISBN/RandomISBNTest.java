import java.text.DecimalFormat;
import java.util.Random;

public class RandomISBNTest {
	public static void main(String[] args) {
		// Generieren von 3 ISBN-Nummern mit Hilfe von RandomISBN
		RandomISBN isbn1, isbn2, isbn3;
		
		isbn1 = new RandomISBN();
		System.out.println("1st ISBN is: " + isbn1);
		isbn2 = new RandomISBN();
		System.out.println("2nd ISBN is: " + isbn2);
		isbn3 = new RandomISBN();
		System.out.println("3rd ISBN is: " + isbn3);
	}
}