import java.util.*;

public class DictTest {
	/** Runs the program, taking the name of the dictionary file as a command line argument. */
	public static void main(String[] args){
		if (args.length == 0){
			System.err.println("Usage: java MobileDictionary <textfile>");
			return;
		}

		MobileDictionary mobileDict = new MobileDictionary();
		try { 
			mobileDict.loadDictionary(args[0]); 
		}
		catch (java.io.IOException e) {
			System.err.println("Error loading file" + args[0]);
			return;
		}

		Scanner scn = new Scanner(System.in);
		String code;
		while (true) {
			System.out.print("Enter a numeric code (ctrl+c to abort): ");
			code = scn.nextLine();
			List<String> wordList = mobileDict.decode(code);
			if (wordList == null) {
				System.out.println("No entry for " + code);
			} 
			else{
				System.out.println(wordList);
			}
		}
	}
}