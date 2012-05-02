public class BookStoreTest {
	public static void main(String args[]) throws java.text.ParseException {
		BookStore store = new BookStore();
		store.registerBooks();
		System.out.println("\nList of books:");
		store.listBooks();
		System.out.println("\nCheapest book:");
		System.out.println(store.getCheapestBook());
	}
}