package learn.algorithm.skill.amout;

import java.util.HashSet;
import java.util.TreeSet;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 根据数据规模猜解法
 * 题目描述如下：
 * 给定一个非负数组 arr，和一个正数 m。 返回 arr 的所有子序列中累加和模上 m 之后的最大值。
 */
public class SubsequenceMaxModM {

    static int max1(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 0) {
            return -1;
        }
        HashSet<Integer> set = new HashSet<>();
        process(arr, 0, 0, set);
        int max = 0;
        for (Integer sum : set) {
            max = Math.max(max, sum % m);
        }
        return max;
    }

    /**
     * 使用递归穷举所有的累加和
     *
     * @param arr   arr
     * @param index index
     * @param sum   0~index-1 范围内得到的累加和
     * @param set   记录所有情况的累加和
     */
    private static void process(int[] arr, int index, int sum, HashSet<Integer> set) {
        if (index == arr.length) {
            set.add(sum);
        } else {
            // 可能性 1：不使用 index 位置的数
            process(arr, index + 1, sum, set);
            // 可能性 2：使用 index 位置的数
            process(arr, index + 1, sum + arr[index], set);
        }
    }

    static int max2(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 0) {
            return -1;
        }
        int sum = 0;
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }
        //  dp[i][j] 来表示在数组 0~i 位置是否能够得到累加和恰好为 j
        boolean[][] dp = new boolean[N][sum + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0]] = true;

        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                // 可能性 1：不选择 i 位置的数作为累加和的一部分，累加和能否凑到 j 取决于 dp[i-1][j] 的结果
                dp[i][j] = dp[i - 1][j];
                // 可能性 2：选择 i 位置的数作为累加和的一部分，累加和是否能够凑到 j 取决于 i-1 时累加和能否凑到 j-arr[i]
                if (j - arr[i] >= 0) {
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }
            }
        }

        int ans = 0;
        for (int j = 0; j <= sum; j++) {
            if (dp[N - 1][j]) {
                ans = Math.max(ans, j % m);
            }
        }
        return ans;
    }

    static int max3(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 0) {
            return -1;
        }
        int N = arr.length;
        // dp[i][j] 在数组 0~i 位置任意子序列的累加和模上 m 的值是否恰好为 j
        boolean[][] dp = new boolean[N][m];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0] % m] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < m; j++) {
                // dp[i][j] T or F
                dp[i][j] = dp[i - 1][j];
                int mod = arr[i] % m;
                if (mod <= j) {
                    dp[i][j] |= dp[i - 1][j - mod];
                } else {
                    dp[i][j] |= dp[i - 1][m + j - mod];
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            if (dp[N - 1][i]) {
                ans = i;
            }
        }
        return ans;
    }

    /**
     * sum 和 m 都很大时，arr 的长度相对不大
     * 使用分治的思想去解决
     */
    static int max4(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 0) {
            return -1;
        }
        if (arr.length == 1) {
            return arr[0] % m;
        }
        int mid = (arr.length - 1) / 2;
        TreeSet<Integer> sortSet1 = new TreeSet<>();
        process4(arr, 0, 0, mid, m, sortSet1);
        TreeSet<Integer> sortSet2 = new TreeSet<>();
        process4(arr, mid + 1, 0, arr.length - 1, m, sortSet2);
        int ans = 0;
        for (Integer leftMod : sortSet1) {
            ans = Math.max(ans, leftMod + sortSet2.floor(m - 1 - leftMod));
        }
        return ans;
    }

    private static void process4(int[] arr, int index, int sum, int end, int m, TreeSet<Integer> sortSet) {
        if (index == end + 1) {
            sortSet.add(sum % m);
        } else {
            process4(arr, index + 1, sum, end, m, sortSet);
            process4(arr, index + 1, sum + arr[index], end, m, sortSet);
        }
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        int m = 76;
        int testTimes = 500000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generatePositiveRandomArray(len, value);
            int ans1 = max1(arr, m);
            int ans2 = max2(arr, m);
            int ans3 = max3(arr, m);
            int ans4 = max4(arr, m);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println("Oops!");
            }
        }
        System.out.println("Finish!");
    }
}
