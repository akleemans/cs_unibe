import java.util.Date;
import java.text.*;
import java.util.Scanner;

public class BookStore {
	Book book0, book1, book2, book3, book4, book5;
	String goOn = "y";
	int bookcount = 0;
	
	public void registerBooks() throws java.text.ParseException {
		
		Scanner scn = new Scanner(System.in);
		while (goOn.equals("y") && (bookcount == 0)) {
			book1 = new Book();
			book1.input();
			bookcount++;
			System.out.println("OK");
			System.out.println("You wanna register another book (y/n)? ");
			goOn = scn.next().toLowerCase();
		}
		while (goOn.equals("y") && (bookcount == 1)) {
			book2 = new Book();
			book2.input();
			bookcount++;
			System.out.println("OK");
			System.out.println("You wanna register another book (y/n)? ");
			goOn = scn.next().toLowerCase();
		}
		while (goOn.equals("y") && (bookcount == 2)) {
			book3 = new Book();
			book3.input();
			bookcount++;
			System.out.println("OK");
			System.out.println("You wanna register another book (y/n)? ");
			goOn = scn.next().toLowerCase();
		}
		while (goOn.equals("y") && (bookcount == 3)) {
			book4 = new Book();
			book4.input();
			bookcount++;
			System.out.println("OK");
			System.out.println("You wanna register another book (y/n)? ");
			goOn = scn.next().toLowerCase();
		}
		// because 5 books is the maximum, this is the last "loop"
		while (goOn.equals("y") && (bookcount == 4)) {
			book5 = new Book();
			book5.input();
			bookcount++;
			System.out.println("OK");
			System.out.println("Maximal number of books reached! ");
		}
	}	
	
	public void listBooks() {	
		// depending on how many books were registered, the programm
		// prints the list of the registered books		
		if (bookcount == 1)
			System.out.println (book1);
		if (bookcount == 2) {
			System.out.println (book1);
			System.out.println (book2);
		}
		if (bookcount == 3) {
			System.out.println (book1);
			System.out.println (book2);
			System.out.println (book3);
		}
		if (bookcount == 4) {
			System.out.println (book1);
			System.out.println (book2);
			System.out.println (book3);
			System.out.println (book4);
		}
		if (bookcount == 5) {
			System.out.println (book1);
			System.out.println (book2);
			System.out.println (book3);
			System.out.println (book4);
			System.out.println (book5);
		}
	}

	public Book getCheapestBook() {
		// how many books are registered? take this case and evaluate
		// the cheapest book...
		switch (bookcount) {
			case 1:
			return book1;
				
			case 2:
			if (book1.getPrice() < book2.getPrice())
				return book1;
				
			else
				return book2;
				
			case 3:
			if (book1.getPrice() < book2.getPrice())
				if (book1.getPrice() < book3.getPrice())
					return book1;
					
				else
					return book3;
					
			else	
				if (book2.getPrice() < book3.getPrice())
					return book2;
						
				else
					return book3;
			case 4: 
				if (book1.getPrice() < book2.getPrice())
					if (book1.getPrice() < book3.getPrice())
						if (book1.getPrice() < book4.getPrice())
							return book1;
						else
							return book4;
					else 
						if (book3.getPrice() < book4.getPrice())
							return book3;
						else
							return book4;
				else
					if (book2.getPrice() < book3.getPrice())
						if (book2.getPrice() < book4.getPrice())
							return book2;
						else
							return book4;
					else
						if (book3.getPrice() < book4.getPrice())
							return book3;
						else
							return book4;
			case 5:
				if (book1.getPrice() < book2.getPrice())
					if (book1.getPrice() < book3.getPrice())
						if (book1.getPrice() < book4.getPrice())
							if (book1.getPrice() < book5.getPrice())
								return book1;
							else
								return book5;
						else
							if (book4.getPrice() < book5.getPrice())
								return book4;
							else
								return book5;
					else
						if (book3.getPrice() < book4.getPrice())
							if (book3.getPrice() < book5.getPrice())
								return book3;
							else
								return book5;
						else
							if (book4.getPrice() < book5.getPrice())
								return book4;
							else
								return book5;
				else
					if (book2.getPrice() < book3.getPrice())
						if (book2.getPrice() < book4.getPrice())
							if (book2.getPrice() < book5.getPrice())
								return book2;
							else
								return book5;
						else 
							if (book4.getPrice() < book5.getPrice())
								return book4;
							else 
								return book5;
					else
						if (book3.getPrice() < book4.getPrice())
							if (book3.getPrice() < book5.getPrice())
								return book3;
							else
								return book5;
						else
							if (book4.getPrice() < book5.getPrice())
								return book4;
							else
								return book5;
							
		
		
		}
		// needed for default return
		return (book0);
	}
}