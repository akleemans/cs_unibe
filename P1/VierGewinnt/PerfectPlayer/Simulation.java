class Simulation {

	static int colthr[];
	static boolean colwon[];
	int moves[];
	int plycnt;
	int rows[];
	int dias[];
	int columns[];
	int height[];

	static {
		colthr = new int[128];
		for (int i = 8; i < 128; i += 8) {
			colthr[i] = 1;
			colthr[i + 7] = 2;
		}

		colwon = new boolean[128];
		for (int j = 16; j < 128; j += 16)
			colwon[j] = colwon[j + 15] = true;
	}

	public Simulation() {
		moves = new int[44];
		rows = new int[8];
		dias = new int[19];
		columns = new int[8];
		height = new int[8];
		reset();
	}
	
	void reset() {
		plycnt = 0;
		for (int i = 0; i < 19; i++)
			dias[i] = 0;

		for (int j = 0; j < 8; j++) {
			columns[j] = 1;
			height[j] = 1;
			rows[j] = 0;
		}

	}

	final boolean wins(int i, int j, int k) {
		k <<= 2 * i;
		int l = rows[j] | k;
		int i1 = l & l << 2;
		if ((i1 & i1 << 4) != 0)
			return true;
		l = dias[5 + i + j] | k;
		i1 = l & l << 2;
		if ((i1 & i1 << 4) != 0)
			return true;
		l = dias[(5 + i) - j] | k;
		i1 = l & l << 2;
		return (i1 & i1 << 4) != 0;
	}

	void backmove() {
		int l = plycnt & 1;
		int k = moves[plycnt--];
		int j = --height[k];
		columns[k] >>= 1;
		int i = ~(1 << 2 * k + l);
		rows[j] &= i;
		dias[5 + k + j] &= i;
		dias[(5 + k) - j] &= i;
	}

	void makemove(int i) {
		moves[++plycnt] = i;
		int l = plycnt & 1;
		int k = height[i]++;
		columns[i] = (columns[i] << 1) + l;
		int j = 1 << 2 * i + l;
		rows[k] |= j;
		dias[5 + i + k] |= j;
		dias[(5 + i) - k] |= j;
	}
}