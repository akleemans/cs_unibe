
public class RadixSortTester {

	/**
	 * Generate a random character array of length d
	 * @param d length
	 * @return character array
	 */
	public static char[] randomCharArray(int d)
	{
		char[] s = new char[d];
		
		for(int i=0; i<d; i++)
		{
			s[i] = (char)('a'+Math.random()*26);
		}
		
		return s;
	}
	
	/**
	 * Generate an array of random character arrays of
	 * different lengths.
	 * @param n number of arrays
	 * @param d maximum length of arrays
	 * @return array of character arrays
	 */
	public static char[][] generateTestData(int n, int d)
	{
		char[][] testData = new char[n][];
		
		for(int i=0; i<n; i++)
		{
			testData[i] = randomCharArray(1+(int)Math.floor(Math.random()*d));
		}
		
		return testData;
	}
	
	public static void main(String[] args) {

		int n[] = {10000, 20000, 40000, 80000, 160000, 320000, 640000};
		int d = 100;
		
		tinyTest();
		/*
		for(int i=0; i<n.length; i++)
		{
			char[][] testData = generateTestData(n[i], d);
		
			Timer t = new Timer();
			t.reset();			
			RadixSort.radixSort(testData, d);			
			System.out.printf("RadixSort: Number of elements: %d, Time: %dms\n", n[i], t.timeElapsed());
			
			
			t.reset();			
			RadixSort.radixSortImproved(testData, d);	
			System.out.printf("Improved RadixSort: Number of elements: %d, Time: %dms\n", n[i], t.timeElapsed());
		}*/
		
	}

	private static void tinyTest() {
		char[][] testData = generateTestData(10, 5);
		RadixSort.radixSortImproved(testData, 5);
	}
}
