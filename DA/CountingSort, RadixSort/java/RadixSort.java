import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class RadixSort {

	/*
	 * Implements radix sort. Character arrays of different lengths
	 * are ordered lexicographically, for example, a<ab<b. The 
	 * implementation doesn't use counting sort as a stable sort. 
	 * Instead, it simply uses an array of queues for each character 
	 * value.  
	 * 
	 * @param A an array of character arrays with different lengths
	 * @param d the length of the longest array in A 
	 */
	public static void radixSort(char[][] A, int d)
	{
		// 27 queues for 26 characters plus 'empty' character
		LinkedList[] queues = new LinkedList[27];
		
		// for all positions from right to left
		for(int j=d-1; j>=0; j--)
		{
			// initialize empty queues
			for(int i=0; i<27; i++) queues[i] = new LinkedList();
			
			// place each character array in correct queue
			for(int i=0; i<A.length; i++)
			{
				if(j<A[i].length)
				{
					// characters 'a'-'z'
					queues[A[i][j]-'a'+1].addLast(A[i]);
				} 
				else
				{
					// character array is shorter than current position.
					// place it in 'empty' queue. 'emtpy' queue is queue 0
					// to get lexicographically correct results, i.e., a<ab.
					queues[0].addLast(A[i]);
				}
			}
			
			// traverse queues 
			int n = 0;
			for(int i=0; i<27; i++)
			{				
				while(queues[i].size() > 0) 
				{
					A[n] = (char[])queues[i].removeFirst();
					n++;
				} 
			}
		}
	}

	public static void radixSortImproved(char[][] A, int d) {
		// 26 queues for 26 characters
		LinkedList[] queues = new LinkedList[26];
		LinkedList[] lengths = new LinkedList[d];
		System.out.printf("Input:\n");
		for (int i=0; i<A.length; i++) {System.out.printf(String.valueOf(i)+"**: "+String.valueOf(A[i])+"\n");}
		
		//sort A for length
		for (int j=0; j<d; j++) {
			for (int i=0; i<d; i++) lengths[i] = new LinkedList();
			// place each character array in correct queue
			for (int i=0; i<A.length; i++) lengths[A[i].length-1].addLast(A[i]);
		}
		//pull them back again
		int n=0;
		for (int i=d-1; i>=0; i--) {	
			//add elements from queue
			while (lengths[i].size() > 0) {
				A[n] = (char[])lengths[i].removeFirst();
				n++;
			} 
		}
		System.out.printf("\nSorted for length:\n");
		for (int i=0; i<A.length; i++) {System.out.printf(String.valueOf(i)+"*: "+String.valueOf(A[i])+"\n");}
		
		// for all positions from right to left
		for (int j=d-1; j>=0; j--) {
			
			// initialize empty queues
			for (int i=0; i<26; i++) queues[i] = new LinkedList();
			
			// place each character array in correct queue
			for (int i=0; i<A.length; i++) {
				if (j >= A[i].length) break; 
				queues[A[i][j]-'a'].addLast(A[i]);
				A[i] = null;
			}
			
			//aggregate
			int idxA=0;
			for (int i=0; i<A.length;i++) {
				if (A[i] != null) {
					A[idxA] = A[i];
					idxA++;
				}
			}
			
			// traverse queues 
			for (int i=0; i<26; i++) {	
				//add elements from queue
				while (queues[i].size() > 0) {
					A[idxA] = (char[])queues[i].removeFirst();
					idxA++;
				} 
			}
		}
		System.out.printf("\nOutput:\n");
		for (int i=0; i<A.length; i++) {System.out.printf(String.valueOf(i)+": "+String.valueOf(A[i])+"\n");}
	}
	
}
