/**
 * File: Sorting.java
 *
 * Implementation von Insertion Sort (Sortieren durch Einfügen) und.
 * Merge Sort (Sortieren durch Mischen).
 */


public class Sorting {

	/** 
	 * Vertauscht im array die beiden Werte an pos1 und pos2. 
	 * */
	public static void swap(int[] array, int pos1, int pos2) {
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}

	/** 
	 * Sortiert den array mit Insertion Sort. 
	 * */
	public static void insertionSort(int[] array) {
		for (int i=1; i<array.length; i++)      // insert i-th record
			for (int j=i; (j>0) && (array[j]<array[j-1]); j--)
				swap(array, j, j-1);                // bubble into correct position
	}

	/**
	 * Fügt zwei sortierte Sequenzen zusammen (merge Schritt in Merge Sort). 
	 * Achtung: Der Pseudocode im Buch verwendet Felder, wo das erste 
	 * Element den Index 1 hat. In Java Arrays hat das erste Element
	 * aber Index 0. 
	 */
	public static void merge(int[] array, int p, int q, int r)
	{
		int i, j;
		int n1 = q-p+1;
		int n2 = r-q;
		int[] L = new int[n1+1];
		int[] R = new int[n2+1];
		for(i=0; i<n1; i++)
		{
			L[i] = array[p+i];
		}
		for(j=0; j<n2; j++)
		{
			R[j] = array[q+j+1];
		}
		L[n1] = Integer.MAX_VALUE;
		R[n2] = Integer.MAX_VALUE;
		i = 0;
		j = 0;
		for(int k=p; k<=r; k++)
		{
			if(L[i]<=R[j])
			{
				array[k] = L[i];
				i++;
			} else
			{
				array[k] = R[j];
				j++;
			}
		}
	}
	
	/**
	 * Sortiert den array mit Merge Sort.
	 */
	public static void mergeSort(int[] array, int p, int r) {
		if(p<r)
		{
			int q=(p+r)/2;
			mergeSort(array, p, q);
			mergeSort(array, q+1, r);
			merge(array, p, q, r);
		}
	}

}






