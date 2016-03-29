package grammer.集合;

import java.util.HashMap;
import java.util.Map;

public class TestHashMap {
	static int hashSeed = 0;
	public static void test1() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		int i = -99999;
		int seed = 0;
		System.out.println(-1 & 1);
	}
	public static void main(String[] args) {
		test1();
	}
	static int hash(int k) {
        int h = hashSeed;
        h ^= k;

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
}
