public class Tests {

	/**
	 * Testes the class evaluateDNF for different inputs.
	 */
	public static void main(String[] args) {
		DNFSolver dnfSolver = new DNFSolver();
		
		String[] testData = {
				"A¬BCvBC¬BvA¬AB¬C", 		// (example) satisfiable
				"AAAAAAA¬A",			// not satisfiable
				"AvBvCvDvEvFvGvHvIvJvKvLvMvN",	// satisfiable
				"",				// satisfiable
				"A¬AvB¬BvC¬CvD¬DvE¬EvF¬FvG¬G",	// not satisfiable
				"A¬AvB¬BvC¬CvD¬DvE¬EvF¬FvG¬H",	// satisfiable
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ",	// satisfiable
				"¬A¬B¬C¬D¬E¬F¬G¬H¬I¬J¬K¬L¬M",	// satisfiable
				"¬A¬A¬AAvBBB¬B¬B¬BBB"		// not satisfiable
		};
		
		for (int i = 0; i < testData.length; i++) {
			System.out.println("Evaluation for '"+testData[i]+"' begins...");
			boolean result = dnfSolver.solve(testData[i]);
			System.out.println("Satisfiable: "+result+"\n");
		}
		
		
	}

}
