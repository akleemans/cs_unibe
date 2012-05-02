import java.util.Date;
import java.text.*;
import java.util.Scanner;

public class Book implements IMedium {
	private int id;
	private String title;
	private String author;
	private int year;
	private int price;

	/** constructor */
	public Book(int id, String title, String author, int year, int price) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.year = year;
		this.price = price;
	}
	/** default constructor */
	public Book() {
	}
	
	public String getShortDescription() {
		return title + ", " + year + ", " + price + " CHF";
	}

	/** Returns a String representation of the book */
	public String toString() {
		return id + " " + title + ", by " + author +
			", published " +year+", "+price+" CHF";
	}

	/** Reads all book data from user input */
	public void input() throws java.text.ParseException {
		Scanner scn = new Scanner(System.in);
		System.out.print("Please enter id: ");
		id = Integer.parseInt(scn.nextLine());
		System.out.print("Please enter the title: ");
		title = scn.nextLine();
		System.out.print("Please enter the author's first and lastname: ");
		author = scn.nextLine();
		System.out.print("Please enter year of publication (e.g. 2007): ");
		year = Integer.parseInt(scn.nextLine());
		System.out.print("Please enter price in CHF as a whole number: ");
		price = Integer.parseInt(scn.nextLine());
	}

	// Get-/Set-methods
	public int getId(){ return this.id; }
	public void setId(int id){ this.id = id; }
	
	public String getAuthor(){ return this.author; }
	public void setAuthor(String author){ this.author = author; }
	
	public String getTitle(){ return this.title; }
	public void setTitle(String title){ this.title = title; }
	
	public int getYear(){ return this.year; }
	public void setYear(int year){ this.year = year; }
	
	public int getPrice(){ return this.price; }
	public void setPrice(int price){ this.price = price; }
}
