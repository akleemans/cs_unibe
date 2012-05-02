import java.util.Scanner;
import java.text.*;
import java.util.ArrayList;

public class BookStore{
	private ArrayList<IMedium> media = new ArrayList<IMedium>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	
	/** starts the menu */
	public void interactWithUser() throws java.text.ParseException {
		String answer = "";
		while (!answer.equals("6")) {
			System.out.println("\n1. Register a book         2. Register a movie");
			System.out.println("3. Register a new order    4. List all registered media");
			System.out.println("5. List all orders         6. Exit");
			Scanner scn = new Scanner(System.in);
			System.out.print("\nWhat do you want to do (1-6)? ");
			answer = scn.nextLine();
			if (answer.equals("1")) {
				Book b = new Book();
				b.input();
				registerMedium(b);
			}
			else if(answer.equals("2")) {
				Movie m = new Movie();
				m.input();
				registerMedium(m);
			}
			else if (answer.equals("3")) {
				Order order = new Order();
				listMedia();
				System.out.print("\nEnter id of ordered object (press x when done): ");
				String input = scn.nextLine();
				while (!input.equalsIgnoreCase("x")) {
					int id = Integer.parseInt(input);
					if (mediumExists(id)) {
						// if it exists, then add it to the order
						for (IMedium m : media) {
							if (m.getId()==id) {
								order.add(m);
								System.out.println("Successfully added: "+m.getShortDescription());
							}
						}
					} 
					else {
						System.out.println("A medium with this id does not exists!");
					}
					System.out.print("Enter id of ordered object (press x when done): ");
					input = scn.nextLine();
				}
				System.out.print("Enter the customer's name: ");
				order.setCustomerName(scn.nextLine());
				System.out.print("Enter the customer's address: ");
				order.setCustomerAddress(scn.nextLine());
				this.orders.add(order);
			}
			else if(answer.equals("4")) {
				this.listMedia();
				System.out.println("");
			}
			else if(answer.equals("5")) {
				this.listOrders();
			}
		}
	}
	
	/** Register a new object */
	public void registerMedium(IMedium m) {
		media.add(m);
	}
	
	/** checks if a medium with the given id exists */
	private boolean mediumExists(int id) {
		for(IMedium m : media){
			if (m.getId()==id) return true;
		}
		return false;
	}
	
	/** Output all registered media on the screen */
	private void listMedia() {
		System.out.println("");
		for (IMedium m : media) {
			System.out.println(m);
		}
	}
	
	/** Output all orders on the screen */
	private void listOrders(){
		for (Order o : this.orders) {
			String order = "\nOrder for: " + o.getCustomerName()+", "
			+o.getCustomerAddress()+"\n";
			for (IMedium m : o.getOrderedMedia()) {
				order += "* "+m.getShortDescription()+"\n";
			}
			order += "----------------------------------------------------\n";
			order += "Total price: "+o.getTotalPrice()+" CHF\n";
			order += "====================================================\n";
			System.out.println(order);
		}
	}
	
	/** Registers some objects and starts the program */
	public static void main(String[] args) throws java.text.ParseException{
		BookStore store = new BookStore();
		store.registerMedium(new Book(1, "Die Blechtrommel", "Günter Grass", 1959, 29));
		store.registerMedium(new Book(2, "Andorra", "Max Frisch", 1961, 39));
		store.registerMedium(new Book(3, "L'Etranger", "Albert Camus", 1942, 25));
		store.registerMedium(new Movie(4, "Casablanca", 1942, Movie.Format.dvd, 29));
		store.registerMedium(new Movie(5, "Into the wild", 2007, Movie.Format.blueray, 38));
		store.interactWithUser();
	}
}