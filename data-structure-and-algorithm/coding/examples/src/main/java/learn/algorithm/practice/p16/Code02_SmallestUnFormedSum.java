package learn.algorithm.practice.p16;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 给定一个正数数组 arr，返回 arr 的子序列不能累加出的最小正数。
 * - 正常怎么做？
 * - 如果 arr 中肯定有 1 这个值，怎么做？
 */
public class Code02_SmallestUnFormedSum {

    // 递归尝试，枚举全部子序列的累加和
    static int unformedSum1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 1;
        }
        Set<Integer> set = new HashSet<>();
        process(arr, 0, 0, set);
        for (int i = 1; i != Integer.MIN_VALUE; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        return 0;
    }

    // 当前来到 i 位置，0~i-1 位置累加和为 sum，考虑 i 位置及以后的累加和
    private static void process(int[] arr, int i, int sum, Set<Integer> set) {
        if (i == arr.length) {
            set.add(sum);
            return;
        }
        process(arr, i + 1, sum, set);
        process(arr, i + 1, sum + arr[i], set);
    }

    // 严格递推优化
    static int unformedSum2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 1;
        }
        // 子序列最大正数累加和
        int max = 0;
        for (int value : arr) {
            max += value;
        }
        int n = arr.length;
        boolean[][] dp = new boolean[n][max + 1];
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }
        for (int j = 1; j <= max; j++) {
            dp[0][j] = arr[0] == j;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= max; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - arr[i]];
                }
            }
        }
        for (int j = 1; j <= max; j++) {
            if (!dp[n - 1][j]) {
                return j;
            }
        }
        return max + 1;
    }

    // 已知 arr 中肯定有 1 这个数
    static int unformedSum3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // arr[0]=0
        Arrays.sort(arr);
        int range = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > range + 1) {
                return range + 1;
            } else {
                range += arr[i];
            }
        }
        return range + 1;
    }

    public static void main(String[] args) {
        int len = 27;
        int max = 30;
        int[] arr = ArrayComparator.generatePositiveRandomArray(len, max);
        ArrayComparator.printArray(arr);
        arr[0] = 1;
        long start = System.currentTimeMillis();
        System.out.println(unformedSum1(arr));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");
        System.out.println("======================================");

        start = System.currentTimeMillis();
        System.out.println(unformedSum2(arr));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");
        System.out.println("======================================");

        start = System.currentTimeMillis();
        System.out.println(unformedSum3(arr));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");
    }
}
