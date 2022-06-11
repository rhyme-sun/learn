package learn.algorithm.basics.algorithm.bit;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Simon
 */
public class BitMap {

    private final long[] bits;

    public BitMap(int n) {
        bits = new long[(n + 64) >> 6];
    }

    public void add(int num) {
        // num / 64
        int index = num >> 6;
        // num % 64
        int offset = num & 63;
        bits[index] |= (1L << (offset));
    }

    public void delete(int num) {
        // num / 64
        int index = num >> 6;
        // num % 64
        int offset = num & 63;
        bits[index] &= ~(1L << offset);
    }

    public boolean contains(int num) {
        // num / 64
        int index = num >> 6;
        // num % 64
        int offset = num & 63;
        return ((bits[index] >> offset) & 1) == 1;
    }

    public static void main(String[] args) {
        int max = 10000;
        BitMap bitMap = new BitMap(max);
        Set<Integer> set = new HashSet<>();
        int testTime = 10000000;
        for (int i = 0; i < testTime; i++) {
            int num = (int) (Math.random() * (max + 1));
            double decide = Math.random();
            if (decide < 0.333) {
                bitMap.add(num);
                set.add(num);
            } else if (decide < 0.666) {
                bitMap.delete(num);
                set.remove(num);
            } else {
                if (bitMap.contains(num) != set.contains(num)) {
                    System.out.println("Oops!");
                    break;
                }
            }
        }
        for (int num = 0; num <= max; num++) {
            if (bitMap.contains(num) != set.contains(num)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("Finish!");
    }
}
