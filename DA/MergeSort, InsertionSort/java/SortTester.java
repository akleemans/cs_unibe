/**
 * File: SortTester.java
 *
 * Einfaches Testprogramm der Sortieralgorithmen.
 */

public class SortTester {

	public static void printArray(int[] array)
	{
		// Ausgabe des Arrays:
		for (int i=0; i<array.length; i++)
			System.out.print(array[i] + " ");
		System.out.println();
	}

	public static void main(String[] args) {

		// zu sortierender Array:
		int[] array = {5, 2, 9, 1, 2, 7, 3, 2, 4, 8, 1};
		int[] array2 = new int[array.length];
		java.lang.System.arraycopy(array, 0, array2, 0, array.length);

		Sorting.insertionSort(array);
		printArray(array);
		Sorting.mergeSort(array2, 0, array.length-1);
		printArray(array2);

	}
}


