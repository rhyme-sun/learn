package learn.algorithm.practice.p07;

import java.util.Arrays;
import java.util.HashSet;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 给定一个有序数组arr，其中值可能为正、负、0。 返回arr中每个数都平方之后不同的结果有多少种？
 * 给定一个数组arr，先递减然后递增，返回arr中有多少个绝对值不同的数字？
 */
public class Code04_Power2Diffs {

    /**
     * 方法一，使用 HashSet 去重，然后统计数量
     * 时间复杂度O(N)，额外空间复杂度O(N)
     */
    static int diff1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        HashSet<Integer> set = new HashSet<>();
        for (int cur : arr) {
            set.add(cur * cur);
        }
        return set.size();
    }

    static int diff2(int[] arr) {
        int N = arr.length;
        int L = 0;
        int R = N - 1;
        int count = 0;
        while (L <= R) {
            int leftAbs = Math.abs(arr[L]);
            int rightAbs = Math.abs(arr[R]);
            if (leftAbs < rightAbs) {
                while (R >= 0 && Math.abs(arr[R]) == rightAbs) {
                    R--;
                }
            } else if (leftAbs > rightAbs) {
                while (L < N && Math.abs(arr[L]) == leftAbs) {
                    L++;
                }
            } else {
                while (L < N && Math.abs(arr[L]) == leftAbs) {
                    L++;
                }
                while (R >= 0 && Math.abs(arr[R]) == rightAbs) {
                    R--;
                }
            }
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 500;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generateRandomArray(len, value);
            Arrays.sort(arr);
            int ans1 = diff1(arr);
            int ans2 = diff2(arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
