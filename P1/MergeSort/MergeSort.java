/**	
 * Class to sort a list of Comparable-objects
 */

public class MergeSort {
	/**
	 * @param array ein Array of Comparables
	 */
	public static void sort(Comparable[] array) {
		Comparable[] tmpArray = new Comparable[array.length];
		mergeSort(array, tmpArray, 0, array.length-1);
	}

	/**
	 * this method makes recursive calls
	 * @param array ein Array of Comparables
	 * @param tmpArray ein Array um das zusammengefuegte Resultat zu platzieren
	 * @param left der Index ganz links des Subarrays
	 * @param right der Index ganz rechts des Subarrays
	 */
	private static void mergeSort(Comparable[] array, Comparable[] tmpArray,
			int left, int right) {
		if(left<right){
			int center = (left+right)/2;
			mergeSort(array, tmpArray, left, center);
			mergeSort(array, tmpArray, center+1, right);
			merge(array, tmpArray, left, center+1, right);
		}
	}
	/**
	 * merges two sorted halves of a subarray
	 * @param array ein Array of Comparables
	 * @param tmpArray ein Array um das zusammengefuegte Resultat zu platzieren
	 * @param leftPos der Index ganz links des Subarrays
	 * @param rightPos der Index des Beginns der rechten Haelfte
	 * @param rightEnd der Index ganz rechts des Subarrays
	 */
	private static void merge(Comparable[] array, Comparable[] tmpArray, 
			int leftPos, int rightPos, int rightEnd) {
		int leftEnd = rightPos-1;
		int tmpPos = leftPos;
		int numElements = rightEnd - leftPos + 1;
		
		// main loop
		while (leftPos <= leftEnd && rightPos <= rightEnd)
			if(array[leftPos].compareTo(array[rightPos]) <= 0)
			/** 
			 *	same as	tmpArray[tmpPos] = array[leftPos];
			 * 		tmpPos++;
			 *		leftPos++;
			 */
			 tmpArray[tmpPos++] = array[leftPos++];
			else
				tmpArray[tmpPos++] = array[rightPos++];
		
		while (leftPos <= leftEnd) // copy rest of the first half
			tmpArray[tmpPos++] = array[leftPos++];

		while (rightPos <= rightEnd) // copy rest of the right half
			tmpArray[tmpPos++] = array[rightPos++];

		// copy tmpArray back
		for (int i = 0; i < numElements; i++, rightEnd--)
			array[rightEnd] = tmpArray[rightEnd];
	}
}