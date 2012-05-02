import java.util.*;
import java.io.*;

public class BookFile{

	public String filename; 
	
	public BookFile(String filename){
		this.filename = filename;
	} 
	
	//makes a comma-seperated string out of a book-object (csv)
	protected String toLine(Book book){
		String newLine = String.valueOf(book.getId()) + ", " + book.getTitle() + 
			", " + book.getAuthor() + ", " + String.valueOf(book.getYear()) + 
			", " + String.valueOf(book.getPrice());
	return newLine;
	}
	
	//makes a book-object out of a comma-seperated string (csv)
	protected Book parseLine(String line) throws BookFileException{
		Scanner scn = new Scanner(line);
		scn.useDelimiter(",");
		Book tempBook = new Book();
		try{
		tempBook.setId(Integer.parseInt(scn.next().trim()));
		tempBook.setTitle(scn.next().trim());
		tempBook.setAuthor(scn.next().trim());
		tempBook.setYear(Integer.parseInt(scn.next().trim()));
		tempBook.setPrice(Integer.parseInt(scn.next().trim()));
		}
		catch (Exception a) {
			throw new BookFileException("This is not a valid format. Make sure " +
				"that you use comma-separated values (csv)");
		}
		return tempBook;
	}
	
	//saves the objects in ''books'' in ''filename'' using the toLine-method
	public void save(ArrayList<Book> books) throws IOException{
		PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		for (Book savedBooks : books)
			file.println(toLine(savedBooks));
		file.close();
	}
	
	/*gets line per line of ''filename'' and makes one book-object out of each line
		using the parseLine-method */
	public ArrayList<Book> load()throws FileNotFoundException, BookFileException{
		// String line;
		ArrayList<Book> bookList = new ArrayList<Book>();
		Scanner fileScan = new Scanner(new File(filename));
		while (fileScan.hasNextLine()) {
			//line = fileScan.nextLine();
			//Book newBook = parseLine(line);
			//bookList.add(newBook);
			bookList.add(parseLine(fileScan.nextLine()));
		}
		return bookList;
	}
}