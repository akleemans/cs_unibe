class TransSimulation extends Simulation {

	static final int INTMODSTRIDERANGE = (int) ((1L << 32) % 179L);
	private int ht[];
	private byte he[];
	private int stride;
	private int htindex;
	private int lock;
	protected long posed;
	protected long hits;

	public TransSimulation() {
		ht = new int[0x10059b];
		he = new byte[0x10059b];
	}

	void emptyTT() {
		for (int i = 0; i < 0x10059b; i++) {
			byte byte0;
			int j;
			if ((j = (byte0 = he[i]) & 0x1f) < 31)
				he[i] = (byte) (byte0 - (j >= 16 ? 4 : j));
		}

		posed = hits = 0L;
	}

	void hash() {
		int i = (super.columns[1] << 7 | super.columns[2]) << 7
				| super.columns[3];
		int j = (super.columns[7] << 7 | super.columns[6]) << 7
				| super.columns[5];
		long l = i <= j ? (long) (j << 7 | super.columns[4]) << 21 | (long) i
				: (long) (i << 7 | super.columns[4]) << 21 | (long) j;
		lock = (int) (l >> 17);
		htindex = (int) (l % 0x10059bL);
		stride = 0x20000 + lock % 179;
		if (lock < 0 && (stride += INTMODSTRIDERANGE) < 0x20000)
			stride += 179;
	}

	int transpose() {
		hash();
		int i = htindex;
		for (int j = 0; j < 8; j++) {
			if (ht[i] == lock)
				return he[i];
			if ((i += stride) >= 0x10059b)
				i -= 0x10059b;
		}

		return -128;
	}

	void transrestore(int i, int j) {
		if (j > 31)
			j = 31;
		posed++;
		hash();
		int l = htindex;
		for (int k = 0; k < 8; k++) {
			if (ht[l] == lock) {
				hits++;
				he[l] = (byte) (i << 5 | j);
				return;
			}
			if ((l += stride) >= 0x10059b)
				l -= 0x10059b;
		}

		transput(i, j);
	}

	void transtore(int i, int j) {
		if (j > 31)
			j = 31;
		posed++;
		hash();
		transput(i, j);
	}

	void transput(int i, int j) {
		int k = htindex;
		for (int l = 0; l < 8; l++) {
			if (j > (he[k] & 0x1f)) {
				hits++;
				ht[k] = lock;
				he[k] = (byte) (i << 5 | j);
				return;
			}
			if ((k += stride) >= 0x10059b)
				k -= 0x10059b;
		}
	}
}