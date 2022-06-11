package learn.algorithm.practice.p10;

/**
 * 给出两个整数 n 和 k，找出所有包含从 1 到 n 的数字，且恰好拥有 k 个逆序对的不同的数组的个数。
 * 逆序对的定义如下：对于数组的第 i 个和第  j 个元素，如果满 `i<j` 且 `a[i]>a[j]`，则其为一个逆序对，否则不是。
 * <p>
 * 链接：https://leetcode.cn/problems/k-inverse-pairs-array
 */
public class Code03_KInversePairs {

    static int kInversePairs(int n, int k) {
        if (n < 1 || k < 0) {
            return 0;
        }
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;
        int mod = 1000000007;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= k; j++) {
                dp[i][j] = (dp[i][j - 1] + dp[i - 1][j]) % mod;
                if (j >= i) {
                    // 加一次 mod 是为了防止出现负数
                    dp[i][j] = (dp[i][j] - dp[i - 1][j - i] + mod) % mod;
                }
            }
        }
        return dp[n][k];
    }

    static int kInversePairs2(int n, int k) {
        if (n < 1 || k < 0) {
            return 0;
        }
        // dp[i][j] 1~i 排列组成数组中逆序对恰好为 j 得数组个数
        int[][] dp = new int[n][k];
        dp[0][0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < k; j++) {
                if (i < j) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1];
                }
            }
        }
        return dp[n][k];
    }
}
