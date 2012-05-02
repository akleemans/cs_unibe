import java.util.Date;
import java.text.*;
import java.util.Scanner;

public class Movie implements IMedium {
	public enum Format {
		dvd("DVD"),
		blueray("Blueray Disc");
		private String representation; // string representation of value
		Format(String s) { this.representation = s; }
		public String toString() { return this.representation; }
	}
	
	private int id;
	private String title;
	private int year;
	private int price; // CHF
	private Format format;
	
	public Movie(int id, String title, int year, Format format, int price) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.format = format;
		this.price = price;
	}
	public Movie() {
	}
	
	public String toString(){
		return id + ", " + title + ", " + year + ", " + format.toString() + ", " +
			price + " CHF";
	}
	public void input() throws java.text.ParseException {
		Scanner scn = new Scanner(System.in);
		System.out.print("Please enter id: ");
		id = Integer.parseInt(scn.nextLine());
		System.out.print("Please enter the title: ");
		title = scn.nextLine();
		System.out.print("Please enter year of publication (e.g. 2007): ");
		year = Integer.parseInt(scn.nextLine());
		String answer = "";
		while (!(answer.equals("1") || answer.equals("2"))) {
			System.out.print("Please give the type of format: 1. Blueray Disc 2. DVD (1-2): ");
			answer = scn.nextLine();
		}
		if (answer.equals("1"))
			format = Format.blueray;
		else
			format = Format.dvd;
		System.out.print("Please enter price in CHF as a whole number: ");
		price = Integer.parseInt(scn.nextLine());
	}
	
	public int getId(){ return this.id; }
	public void setId(int id){ this.id = id; }
	
	public String getTitle(){ return this.title; }
	public void setTitle(String title){ this.title = title; }
	
	public int getYear(){ return this.year; }
	public void setYear(int year){ this.year = year; }
	
	public int getPrice(){ return this.price; }
	public void setPrice(int price){ this.price = price; }
	
	public String getShortDescription() { 
		return (this.title + " (" + this.format + "), " +
			this.year + ", " + this.price + " CHF"); 
	}
}