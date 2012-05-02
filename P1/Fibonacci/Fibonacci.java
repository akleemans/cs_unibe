public class Fibonacci {

	public static void main(String[] args) {
		long previous = -1;
		long result = 1;
		for (int i = 0; i <= 50; i++) {
			long sum = result + previous;
			previous = result;
			result = sum;
			System.out.println(result);
		}
	}
}