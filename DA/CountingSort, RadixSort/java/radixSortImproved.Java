public static void radixSortImproved(char[][] A, int d) {
	// 26 queues for 26 characters
	LinkedList[] queues = new LinkedList[26];
	LinkedList[] lengths = new LinkedList[d];
	
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
}
