import java.io.*;
import java.util.Random;

public class ConnectFourSimulation extends TransSimulation {

	InputStream bookstream;
	int buf5;
	int nrle;
	int history[][] = {
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 4, 2, 1, 0, -1, 1,
					3, 5, 7, 5, 3, 1, -1, 2, 5, 8, 10, 8, 5, 2, -1, 2, 5, 8,
					10, 8, 5, 2, -1, 1, 3, 5, 7, 5, 3, 1, -1, 0, 1, 2, 4, 2, 1,
					0 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 4, 2, 1, 0, -1, 1,
					3, 5, 7, 5, 3, 1, -1, 2, 5, 8, 10, 8, 5, 2, -1, 2, 5, 8,
					10, 8, 5, 2, -1, 1, 3, 5, 7, 5, 3, 1, -1, 0, 1, 2, 4, 2, 1,
					0 } };
	static Random rng = new Random();
	long nodes;
	long msecs;

	int bookin() {
		if (buf5 == 1) {
			if (nrle != 0) {
				buf5 = 242;
				nrle--;
			} else {
				try {
					buf5 = bookstream.read();
				} catch (IOException _ex) {
					System.out.println("Book corrupt.\n");
					System.exit(0);
				}
				if (buf5 > 242) {
					nrle = buf5 - 242;
					buf5 = 242;
				}
			}
			buf5 += 243;
		}
		int i = 2 * (buf5 % 3) - 2;
		buf5 /= 3;
		return i;
	}

	void loadBook(InputStream inputstream) {
		bookstream = inputstream;
		nodes = 0L;
		msecs = System.currentTimeMillis() - 1L;
		dataset();
		msecs = System.currentTimeMillis() - msecs;
		try {
			if (inputstream.read() != -1)
				throw new IOException();
		} catch (IOException _ex) {
			System.out.println("Book not depleted.\n");
			System.exit(0);
		}
		try {
			inputstream.close();
			return;
		} catch (IOException _ex) {
			System.out.println("Book couldn't be closed.\n");
		}
		System.exit(0);
	}

	int dataset() {
		nodes++;
		int i2 = transpose();
		if (i2 != -128)
			return i2 >> 5;
		int l2;
		int k2 = (l2 = super.plycnt & 1) ^ 1;
		if (super.plycnt >= 8) {
			int k1;
			int j2 = k1 = 0;
			int l;
			while (++k1 <= 7)
				if ((l = super.height[k1]) <= 6) {
					if (wins(k1, l, 1 << k2)
							|| Simulation.colthr[super.columns[k1]] == 1 << k2)
						return 2;
					if (wins(k1, l, 1 << l2)
							|| Simulation.colthr[super.columns[k1]] != 0)
						j2 = j2 << 3 | k1;
				}
			if (j2 > 7)
				return -2;
			if (j2 == 0) {
				int i = bookin();
				if (k2 == 0)
					i = -i;
				transtore(i, 31);
				return i;
			} else {
				makemove(j2);
				int j = -dataset();
				backmove();
				return j;
			}
		}
		int j1 = 0x80000000;
		int i3 = 8;
		for (int l1 = 4; (l1 = i3 - l1) != 0;) {
			int i1;
			if ((i1 = super.height[l1]) <= 6)
				if (wins(l1, i1, 1 << k2)
						|| Simulation.colthr[super.columns[l1]] == 1 << k2) {
					j1 = 2;
				} else {
					makemove(l1);
					int k;
					if ((k = -dataset()) > j1)
						j1 = k;
					backmove();
				}
			i3 = 15 - i3;
		}

		transtore(j1, 1);
		return j1;
	}

	int move() {
		int ai[] = new int[8];
		int ai1[] = new int[8];
		int ai2[] = new int[8];
		nodes = 0L;
		msecs = 1L;
		int i3;
		int l2 = (i3 = super.plycnt & 1) ^ 1;
		int k;
		int j3;
		int k3;
		int l3;
		for (int i = j3 = k3 = l3 = 0; ++i <= 7;)
			if ((k = super.height[i]) <= 6) {
				if (wins(i, k, 1 << l2)
						|| Simulation.colthr[super.columns[i]] == 1 << l2)
					return i;
				if (wins(i, k, 1 << i3)
						|| Simulation.colthr[super.columns[i]] != 0)
					ai1[k3++] = i;
				else if (k + 1 <= 6 && wins(i, k + 1, 1 << i3))
					ai2[l3++] = i;
				else
					ai[j3++] = i;
			}

		if (k3 > 0)
			return ai1[(rng.nextInt() & 0x7fffffff) % k3];
		if (j3 == 0)
			if (l3 == 0)
				return 0;
			else
				return ai2[(rng.nextInt() & 0x7fffffff) % l3];
		int l;
		if ((l = transpose()) != -128 && l >> 5 == -2)
			return ai[(rng.nextInt() & 0x7fffffff) % j3];
		msecs = System.currentTimeMillis() - 1L;
		long l4 = super.posed;
		int j1;
		int i2 = j1 = 0;
		int j = 0;
		for (int i1 = -2; j < j3 && i1 < 2; j++) {
			int k1 = j;
			int k2 = -128;
			for (; k1 < j3; k1++) {
				int l1 = ai[k1];
				int j2 = history[l2][super.height[l1] << 3 | l1];
				if (j2 > k2) {
					k2 = j2;
					i2 = k1;
				}
			}

			k1 = ai[i2];
			ai[i2] = ai[j];
			ai[j] = k1;
			makemove(k1);
			k2 = -ab(-2, 2);
			backmove();
			if (k2 >= i1) {
				if (k2 > i1) {
					i1 = k2;
					j1 = 0;
				}
				ai[j] = ai[j1];
				ai[j1++] = k1;
			}
		}

		if (super.posed - l4 >= 0x100000L)
			emptyTT();
		msecs = System.currentTimeMillis() - msecs;
		return ai[(rng.nextInt() & 0x7fffffff) % j1];
	}

	int ab(int i, int j) {
		int ai[] = new int[8];
		nodes++;
		if (super.plycnt == 41)
			return 0;
		int i5;
		int k4 = (i5 = super.plycnt & 1) ^ 1;
		int j4;
		int l = j4 = 0;
		while (++l <= 7) {
			int l1;
			if ((l1 = super.height[l]) > 6)
				continue;
			if (wins(l, l1, 3) || Simulation.colthr[super.columns[l]] != 0) {
				if (l1 + 1 <= 6 && wins(l, l1 + 1, 1 << i5))
					return -2;
				ai[0] = l;
				while (++l <= 7)
					if ((l1 = super.height[l]) <= 6
							&& (wins(l, l1, 3) || Simulation.colthr[super.columns[l]] != 0))
						return -2;
				j4 = 1;
				break;
			}
			if (l1 + 1 > 6 || !wins(l, l1 + 1, 1 << i5))
				ai[j4++] = l;
		}
		if (j4 == 0)
			return -2;
		if (j4 == 1) {
			makemove(ai[0]);
			int l2 = -ab(-j, -i);
			backmove();
			return l2;
		}
		int k3;
		if ((k3 = transpose()) != -128) {
			int i3 = k3 >> 5;
			if (i3 == -1) {
				if ((j = 0) <= i)
					return i3;
			} else if (i3 == 1) {
				if ((i = 0) >= j)
					return i3;
			} else {
				return i3;
			}
		}
		long l4 = super.posed;
		int k;
		int j2 = k = 0;
		int j3 = 0x80000000;
		for (int i1 = 0; i1 < j4; i1++) {
			int k1 = i1;
			int k2 = 0x80000000;
			for (; k1 < j4; k1++) {
				int i2 = ai[k1];
				int l3 = history[k4][super.height[i2] << 3 | i2];
				if (l3 > k2) {
					k2 = l3;
					j2 = k1;
				}
			}

			k1 = ai[j2];
			if (i1 != j2) {
				ai[j2] = ai[i1];
				ai[i1] = k1;
			}
			makemove(k1);
			k2 = -ab(-j, -i);
			backmove();
			if (k2 <= j3)
				continue;
			k = i1;
			if ((j3 = k2) <= i || (i = k2) < j)
				continue;
			if (j3 == 0 && i1 < j4 - 1)
				j3 = 1;
			break;
		}

		if (k > 0) {
			for (int j1 = 0; j1 < k; j1++)
				history[k4][super.height[ai[j1]] << 3 | ai[j1]]--;

			history[k4][super.height[ai[k]] << 3 | ai[k]] += k;
		}
		l4 = super.posed - l4;
		int i4;
		for (i4 = 1; (l4 >>= 1) != 0L; i4++)
			;
		if (k3 != -128) {
			if (j3 == -(k3 >> 5))
				j3 = 0;
			transrestore(j3, i4);
		} else {
			transtore(j3, i4);
		}
		return j3;
	}

	public ConnectFourSimulation() {
		buf5 = 1;
	}
}