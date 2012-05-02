import java.util.*;
import java.io.*;

public class BookFileLabelled extends BookFile {
	public BookFileLabelled(String filename) {
		super(filename); // calling the constructor of the BookFile class
	} 
	
	// over-writing the toLine method, not comma-separated anymore
	protected String toLine(Book book){
		String newLine = "id:" + String.valueOf(book.getId()) + "; title:" + book.getTitle() + 
			"; author:" + book.getAuthor() + "; year:" + String.valueOf(book.getYear()) + 
			"; price: " + String.valueOf(book.getPrice());
		return newLine;
	}
	
	// over-writing parseLine method, using getValue instead of comma-delimiter.
	protected Book parseLine(String line) throws BookFileException{
				
		Book tempBook = new Book();
		try {
		tempBook.setId(Integer.parseInt(getValue(line, "id")));
		tempBook.setTitle(getValue(line, "title"));
		tempBook.setAuthor(getValue(line, "author"));
		tempBook.setYear(Integer.parseInt(getValue(line, "year")));
		tempBook.setPrice(Integer.parseInt(getValue(line, "price")));
		}
		catch (Exception a) {
			throw new BookFileException("This is not a valid format. Make sure " +
				"that you use comma-separated values (csv)");
		}
		return tempBook;
	}
	
	private String getValue(String line, String label){
		Scanner scn = new Scanner(line);
		scn.findInLine(label+"[\\s]*:[\\s]*([^;]*)");
		return scn.match().group(1).trim();
	}
}