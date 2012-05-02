import java.util.*;

public class FileTest {
	public static void main(String[] args) throws java.io.IOException {
		// Create a list of Book-objects
		Book book1 = new Book(1, "Atonement", "Ian McEwan", 2007, 29);
		Book book2 = new Book(2, "Andorra", "Max Frisch", 1961, 38);
		Book book3 = new Book(3, "1984", "George Orwell", 1949, 25);
		ArrayList<Book> list = new ArrayList<Book>();
		list.add(book1);
		list.add(book2);
		list.add(book3);
		BookFile file;
		
		/* ------------ Testing comma-separated files ------------ */
		// save books to a comma-separated file
		file = new BookFile("books_out.csv");
		file.save(list);
		System.out.println("\nSaved comma-separated file books_out.csv.\n");
		
		// load books from a comma-separated file
		System.out.println("Reading file books.csv...");
		file = new BookFile("books.csv");
		ArrayList<Book> result = new ArrayList<Book>();
		try {
			result = file.load();
			System.out.println("Loaded the following objects:");
			for (Book book : result) 
				System.out.println(book);
		}
		catch(BookFileException e) {
			System.out.println("\nInvalid file.\n" + e.getMessage());
		}
		
		
		/* ------------ Testing labelled files ------------ */
		// save books to a labelled file
		file = new BookFileLabelled("books_out.txt");  // note the usage of polymorphism!
		file.save(list);
		System.out.println("\nSaved labelled file books_out.txt.\n");
		
		// load books from a labelled file
		System.out.println("Reading file books.txt...");
		file = new BookFileLabelled("books.txt");
		try {
			result = file.load();
			System.out.println("Loaded the following objects:");
			for(Book book : result)	System.out.println(book);
			System.out.println("");
		}
		catch(BookFileException e) {
			System.out.println("\nInvalid file.\n");
		}
		
		// An example of how exceptions are useful
		file = new BookFile("books.txt"); // trying to use BookFile, but wrong format... Exception...
		try {
			result = file.load();
			System.out.println("Loaded the following objects:");
			for(Book book : result) System.out.println(book);
		}
		catch(BookFileException e) {
			// try other format
			System.out.println("Invalid file. Checking if file is labelled..." + e.getMessage());
			file = new BookFileLabelled("books.txt");
			try {
				result = file.load();
				System.out.println("Loaded the following objects:");
				for(Book book : result) System.out.println(book);
			}
			catch(BookFileException e2) {  
				System.out.println("\nInvalid file.\n");  
			}
		}
	}
}