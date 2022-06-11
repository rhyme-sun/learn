package learn.algorithm.basics.algorithm.dp.lefttoright;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 给定一个正数数组 arr，请把 arr 中所有的数分成两个集合，
 * 如果 arr 长度为偶数，两个集合包含数的个数要一样多，
 * 如果 arr 长度为奇数，两个集合包含数的个数必须只差一个。请尽量让两个集合的累加和接近。
 * <p>
 * 返回：最接近的情况下，较小集合的累加和。
 */
public class Code09_SplitSumClosedSizeHalf {

    /**
     * 求数组个数平分下，平分后的两个数组累加和最接近时较小的那个累加和
     */
    static int sumClosedHalfSize(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        if ((arr.length & 1) == 0) { // 数组个数为偶数
            return process(arr, 0, arr.length / 2, sum / 2);
        } else { // 数组个数为奇数
            return Math.max(process(arr, 0, arr.length / 2, sum / 2),
                    process(arr, 0, arr.length / 2 + 1, sum / 2));
        }
    }

    /**
     * arr[i....]自由选择，挑选的个数一定要是 picks 个，返回离 rest 最近的累加和（不超过 rest）
     *
     * @param arr   样本数组
     * @param i     样本位置
     * @param picks 挑选数量
     * @param rest  基准值
     * @return 离 rest 最近的累加和
     */
    private static int process(int[] arr, int i, int picks, int rest) {
        if (i == arr.length) {
            // -1 表示无效值
            return picks == 0 ? 0 : -1;
        } else {
            // 第一种可能，不要 i 位置的数
            int p1 = process(arr, i + 1, picks, rest);
            // 第二种可能，要 i 位置的数
            int p2 = -1;
            int next = -1;
            if (arr[i] <= rest) {
                next = process(arr, i + 1, picks - 1, rest - arr[i]);
            }
            if (next != -1) {
                p2 = arr[i] + next;
            }
            return Math.max(p1, p2);
        }
    }

    /**
     * 动态规划优化
     */
    static int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum /= 2;
        int n = arr.length;
        int m = (n + 1) / 2;
        int[][][] dp = new int[n + 1][m + 1][sum + 1];
        // 全部初始化为无效值
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        for (int rest = 0; rest <= sum; rest++) {
            dp[n][0][rest] = 0;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int picks = 0; picks <= m; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    int p1 = dp[i + 1][picks][rest];
                    // 就是要使用arr[i]这个数
                    int p2 = -1;
                    int next = -1;
                    if (picks - 1 >= 0 && arr[i] <= rest) {
                        next = dp[i + 1][picks - 1][rest - arr[i]];
                    }
                    if (next != -1) {
                        p2 = arr[i] + next;
                    }
                    dp[i][picks][rest] = Math.max(p1, p2);
                }
            }
        }
        if ((arr.length & 1) == 0) {
            return dp[0][arr.length / 2][sum];
        } else {
            return Math.max(dp[0][arr.length / 2][sum], dp[0][(arr.length / 2) + 1][sum]);
        }
    }

    /**
     * 去除迭代行为
     */
    static int dp2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum >>= 1;
        int n = arr.length;
        int m = (arr.length + 1) >> 1;
        int[][][] dp = new int[n][m + 1][sum + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int k = 0; k <= sum; k++) {
                dp[i][0][k] = 0;
            }
        }
        for (int k = 0; k <= sum; k++) {
            dp[0][1][k] = arr[0] <= k ? arr[0] : Integer.MIN_VALUE;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= Math.min(i + 1, m); j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];
                    if (k - arr[i] >= 0) {
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - 1][k - arr[i]] + arr[i]);
                    }
                }
            }
        }
        return Math.max(dp[n - 1][m][sum], dp[n - 1][n - m][sum]);
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = ArrayComparator.generatePositiveRandomArray(len, maxValue);
            int ans1 = sumClosedHalfSize(arr);
            int ans2 = dp(arr);
            int ans3 = dp2(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                ArrayComparator.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("Finish!");
    }
}
