package learn.algorithm.eor;

/**
 * 找出一个数组中的奇数个数的一种数或两种数。
 *
 * @author ykthree
 * 2021/6/13
 */
public class OddTimes {

    /**
     * 一个数组中，有一种数出现了奇数次，其余数均出现偶数次，找出这个数并打印。
     * 将数组中的数做异或运算，更具异或运算的性质，出现偶数次的数异或结果为 0 ，奇数次异或结果为本身。
     */
    static void once(int[] array) {
        if (array == null || array.length == 0) {
            System.out.println("Array can't be empty!");
            return;
        }
        int eor = 0;
        for (int i : array) {
            eor ^= i;
        }
        System.out.println(eor);
    }

    /**
     * 一个数组中，有两种种数出现了奇数次，其余数均出现偶数次，找出这两种数并打印。
     * 将数组中的元素做异或运算，假设这两种数分别为 a 和 b，那么根据异或运算性质，最终结果为 a^b；
     * 因为 a!=b，所以 a^b!=0, 就一定存在某位为 1，找到 a^b 右侧第一个为 1 的位置，假设为第 5 位，此时就可以将数组中的数分成两大类，
     * 即第 5 位为 0 和 第 5 位为 1；
     * a 和 b 一定分别存在这两大类中，因为异或运算，只有对应位值不相等是才能得到 1；
     * 在对其中一类的数做异或运算就能得到 a 或 b。
     */
    static void twice(int[] array) {
        if (array == null || array.length == 0) {
            System.out.println("Array can't be empty!");
            return;
        }
        int eor = 0;
        for (int i : array) {
            eor ^= i;
        }
        // 提取出最右的 1
        int rightOne = eor & (~eor + 1);

        int onlyOne = 0;
        for (int i : array) {
            if ((i & rightOne) == 0) {
                onlyOne ^= i;
            }
        }
        System.out.println(onlyOne);
        System.out.println(onlyOne ^ eor);
    }

    public static void main(String[] args) {
        int[] array = {-1, -1, 0, 0, 0, 1, 1, 1, 1};
        once(array);

        int[] array2 = {-1, -1, 0, 0, 0, 1, 1, 1, 1, 2};
        twice(array2);
    }
}
