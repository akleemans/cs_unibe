/**
 * File: SortTester.java
 *
 * Einfaches Testprogramm der Sortieralgorithmen.
 */

import java.util.ArrayList;

public class SortTestApp {

	// Fuer diese Anzahl Records wird der Test durchgefuehrt:
	public static final int[] NUM_OF_RECORDS = { 100, 200, 400, 800, 1600, 3200, 
		6400, 12800, 25600, 51200, 102400, 204800, 409600, 809600, 1619200};
  
	public static void main(String[] args) {

		Timer timer = new Timer();

		DataGenerator generator = new DataGenerator();

		for (int i=0; i<NUM_OF_RECORDS.length; i++) {
      
			int numOfRecords = NUM_OF_RECORDS[i];
			System.out.println("\nAnzahl zu sortierende Records: " + numOfRecords);

			// zu sortierenden Array erstellen:
			generator.generateNewData(numOfRecords);

			// Comparator erstellen:
			java.util.Comparator<StudentIn> comp = new MatrikelNrComparator();

			// *** QuickSort:
			// zu sortierenden Array erstellen (Kopie der urspruenglichen Daten):
			ArrayList<StudentIn> array = generator.getCopyOfData();

			// Den Array sortieren und Zeit nehmen:
			timer.reset();
			QuickSort.quickSort(array, 0, array.size()-1, comp);
			long sortDuration = timer.timeElapsed();

			System.out.print("   Erster QuickSort: " + sortDuration + " ms");

			// Den Array nochmals sortieren und Zeit nehmen:
			timer.reset();
			QuickSort.quickSort(array, 0, array.size()-1, comp);
			sortDuration = timer.timeElapsed();

			System.out.print("   QuickSort auf den bereits sortierten Daten: " + sortDuration + " ms");
		}
	}
}


