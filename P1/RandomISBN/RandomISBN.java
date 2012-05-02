import java.text.DecimalFormat;
import java.util.Random;

public class RandomISBN {
	private String laendercode;
	private String bandnr;
	private String verlagsnr;
	private String checksum;

	/** Constructor */
	public RandomISBN() {
		makeNumber();
	}

	/** Returns the ISBN in its string representation XX-XXX-XX-C */
	public String toString() {
		return laendercode+"-"+bandnr+"-"+verlagsnr+"-"+checksum;
	}

	/** generates the ISBN using random numbers */
	private void makeNumber() {
		Random gen = new Random();
		
		// laendercode / verlagsnr
		DecimalFormat fmt = new DecimalFormat("00");
		
		// bandnr
		DecimalFormat fmt2 = new DecimalFormat("000");
		
		// checksum
		DecimalFormat fmt3 = new DecimalFormat("0");
		
		// Generiert eine Zahl zwischen 0 und 99 
		laendercode = fmt.format(gen.nextInt(100));
		
		// Generiert eine Zahl zwischen 100 und 999
		bandnr = fmt2.format(gen.nextInt(900) + 100);
		
		// Generiert eine Zahl zwischen 1 und 99
		verlagsnr = fmt.format(gen.nextInt(99) + 1);
		
		// Auslesen der Strings und umwandeln in Zahlen
		int L1 = Integer.parseInt(laendercode.substring(0, 1));
		int L2 = Integer.parseInt(laendercode.substring(1, 2));
		int B1 = Integer.parseInt(bandnr.substring(0, 1));
		int B2 = Integer.parseInt(bandnr.substring(1, 2));
		int B3 = Integer.parseInt(bandnr.substring(2, 3));
		int V1 = Integer.parseInt(verlagsnr.substring(0, 1));
		int V2 = Integer.parseInt(verlagsnr.substring(1, 2));
		
		// checksum
		checksum = fmt3.format((hashOp(L1)+L2+hashOp(B1)+B2+hashOp(B3)+V1+hashOp(V2))%10);
	}

	/** multiplies i with 2 and subtracts 9 if result is >= 10 */
	private int hashOp(int i) {
		int doubled = 2 * i;
		if (doubled >= 10){
			doubled = doubled - 9;
		}
		return doubled;
	}
}