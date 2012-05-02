public class Factorial {
	// recursive method
	public static long factorial(int n) {
		// base case, when n <= 1, n!=1
		if (n <= 1)
			return 1;
		else
			return n * factorial(n - 1);
	}

	/**
	 * calculates the factorial of the integer entered in the command line
	 * 
	 * @param args
	 *            command line arguement args is a String, so it has to be
	 *            parsed into an int-value catching the exception, if command
	 *            line arguement isn't an integer.
	 */
	public static void main(String[] args) {
		int num;
		if (args.length > 0) {
			try {
				num = Integer.parseInt(args[0]);
				System.out.println("The factorial of " + num + " is: " + factorial(num));
			} 
			catch (NumberFormatException e) {
				System.err.println("Argument must be an integer");
				System.exit(1);
			}
		}
	}
}