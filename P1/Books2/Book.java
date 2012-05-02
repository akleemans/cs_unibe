import java.util.Date;
import java.text.*;
import java.util.Scanner;

public class Book {
	private int id, price;
	private String title;
	private String author;
	private Date dateOfPublication;

	public static final String DATE_FORMAT = "dd.MM.yyyy";

	public Book(int tmpId, String tmpTitle, String tmpAuthor, Date tmpDateOfPubl, int tmpPrice) {
		id = tmpId;
		title = tmpTitle;
		author = tmpAuthor;
		dateOfPublication = tmpDateOfPubl;
		price = tmpPrice;
	}

	public Book() {
		id = 0;
		title = "";
		author = "";
		dateOfPublication = new Date();
		price = 0;
	}

	/** Returns the age of the book in days since publication */
	public int age() {
		Date now = new Date();
		long age_ms = now.getTime() - dateOfPublication.getTime(); //age in milliseconds
		return (int) (age_ms/((long)1000*3600*24*365));	//convert to years
	}

	/** Returns a String representation of the book */
	public String toString() {
		return id + " " + title + ", by " + author +
			", published " + dateToString(dateOfPublication) +
			", " + price + " CHF";
	}

	/** Reads all book data from user input */
	public void input() throws ParseException {
		Scanner scn = new Scanner(System.in);
		System.out.print("Please enter id: ");
		id = Integer.parseInt(scn.nextLine());
		System.out.print("Please enter the title: ");
		title = scn.nextLine();
		System.out.print("Please enter the author's first and lastname: ");
		author = scn.nextLine();
		System.out.print("Please enter date of publication (e.g. 01.12.2007): ");
		dateOfPublication = stringToDate(scn.nextLine());
		System.out.print("Please enter the price of the book: ");
		price = scn.nextInt();
	}

	public int getId() {
		return id;
	}
	public void setId(int newId) {
		id = newId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String newAuthor) {
		author = newAuthor;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String newTitle) {
		title = newTitle;
	}
	public Date getDateOfPublication() {
		return dateOfPublication;
	}
	public void setDateOfPublication(Date newDate) {
		dateOfPublication = newDate;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int newPrice) {
		price = newPrice;
	}

	/** Converts the Date object d into a String object */
	private String dateToString(Date d) {
		SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
		return fmt.format(d);
	}

	/** Converts the String object s into a Date object */
	private Date stringToDate(String s) throws ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
		return fmt.parse(s);
	}
}
