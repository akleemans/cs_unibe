import java.util.Date;
import java.text.*;
import java.util.Scanner;

public class Book {
	private int id;
	private String title;
	private String author;
	private Date dateOfPublication;

	public static final String DATE_FORMAT = "dd.MM.yyyy";

	public Book() throws ParseException {
		input();
	}


	/** Returns the age of the book in days since publication */
	public int age() {
		long todaysDateMS, dateOfPublicationMS, diffMS, diffDays;
		Date todaysDate = new Date();
		todaysDateMS = todaysDate.getTime();
		dateOfPublicationMS = dateOfPublication.getTime();
		diffMS = todaysDateMS - dateOfPublicationMS;
		diffDays = diffMS/1000/60/60/24;
		return (int)diffDays; 
	}

	/** Returns a String representation of the book */
	public String toString() {
		String result = id+", "+title+", "+author+", "+
			dateToString(dateOfPublication);
		return result;
	}

	/** Reads all book data from user input */
	public void input() throws ParseException {
		Scanner scn = new Scanner(System.in);
		
		System.out.print ("Please enter id: ");
		id = scn.nextInt();
		scn.nextLine();
		System.out.print ("Please enter the title: ");
		title = scn.nextLine();
			
		System.out.print ("Please enter the name of the author: ");
		author = scn.nextLine();
		
		System.out.print ("Please enter the date of publication in "
			+ "the format " + DATE_FORMAT + ": ");
		String s = scn.nextLine();
		
		dateOfPublication = stringToDate(s);
	}

	public void setId (int bookid) {
		id = bookid;
	}
	public int getId() {
		return id;
	}
	public void setTitle (String booktitle) {
		title = booktitle;
	}
	public String getTitle() {
		return title;
	}
	public void setAuthor (String bookauthor) {
		author = bookauthor;
	}
	public String getAuthor() {
		return author;
	}
	public void setDateOfPublication (String bookdate) throws ParseException {
		dateOfPublication = stringToDate(bookdate);
	}
	public Date getDateOfPublication() {
		return dateOfPublication;
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