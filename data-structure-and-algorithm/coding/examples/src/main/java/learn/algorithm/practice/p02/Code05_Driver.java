package learn.algorithm.practice.p02;

import java.util.Arrays;

/**
 * 题目描述如下：
 * 现有司机 `2N` 人，调度中心会将所有司机平分给 A、B 两个区域。现有一个 `2N*2` 的矩阵 income 表示司机去 A 或 B 的收入，
 * 第 i 个司机去 A 可得收入为 `income[i][0]`，去 B 可得收入为 `income[i][1]`，
 * 要求调度给 A 和 B 区域的人数相同，返回所有调度方案中能使所有司机总收入最高的方案，是多少钱。
 */
public class Code05_Driver {

    static int maxMoney(int[][] income) {
        if (income == null || income.length == 0 || (income.length & 1) == 1) {
            return 0;
        }
        int n = income.length >> 1;
        return process(income, 0, n);
    }

    /**
     * 考虑当前来到 index 位置，还剩 rest 名额去 A，返回 index 往后所有司机调度完毕的最大收入
     */
    private static int process(int[][] income, int index, int rest) {
        if (index == income.length) {
            return 0;
        }
        // 去 B 的名额没有了（只剩下去 A 的名额）
        if (income.length - index == rest) {
            return income[index][0] + process(income, index + 1, rest - 1);
        }
        // 去 A 的名额没有了
        if (rest == 0) {
            return income[index][1] + process(income, index + 1, rest);
        }
        // 选择去 A
        int p1 = income[index][0] + process(income, index + 1, rest - 1);
        // 选择去 B
        int p2 = income[index][1] + process(income, index + 1, rest);
        return Math.max(p1, p2);
    }

    /**
     * 记忆化搜友优化
     */
    static int maxMoney2(int[][] income) {
        if (income == null || income.length == 0 || (income.length & 1) == 1) {
            return 0;
        }
        int n = income.length >> 1;
        int[][] dp = new int[income.length + 1][n + 1];
        return process(income, 0, n, dp);
    }

    /**
     * 考虑当前来到 index 位置，还剩 rest 名额去 A，返回 index 往后所有司机调度完毕的最大收入
     */
    private static int process(int[][] income, int index, int rest, int[][] dp) {
        if (dp[index][rest] != 0) {
            return dp[index][rest];
        }
        if (index == income.length) {
            return 0;
        }
        // 去 B 的名额没有了（只剩下去 A 的名额）
        if (income.length - index == rest) {
            int max = income[index][0] + process(income, index + 1, rest - 1);
            dp[index][rest] = max;
            return max;
        }
        // 去 A 的名额没有了
        if (rest == 0) {
            int max = income[index][1] + process(income, index + 1, rest);
            dp[index][rest] = max;
            return max;
        }
        // 选择去 A
        int p1 = income[index][0] + process(income, index + 1, rest - 1);
        // 选择去 B
        int p2 = income[index][1] + process(income, index + 1, rest);
        int max = Math.max(p1, p2);
        dp[index][rest] = max;
        return max;
    }

    /**
     * 严格递推优化
     */
    static int maxMoney3(int[][] income) {
        if (income == null || income.length == 0 || (income.length & 1) == 1) {
            return 0;
        }
        int n = income.length >> 1;
        // dp[i][j] 表示 i~n 号司机等待调度，去往 A 区域还剩 j 名额条件下的最高收入
        int[][] dp = new int[income.length + 1][n + 1];

        for (int i = income.length - 1; i >= 0; i--) {
            for (int j = 0; j <= n; j++) {
                if (income.length - i == j) {
                    dp[i][j] = income[i][0] + dp[i + 1][j - 1];
                    continue;
                }
                if (j == 0) {
                    dp[i][j] = income[i][1] + dp[i + 1][j];
                    continue;
                }
                int p1 = income[i][0] + dp[i + 1][j - 1];
                int p2 = income[i][1] + dp[i + 1][j];
                dp[i][j] = Math.max(p1, p2);
            }
        }
        return dp[0][n];
    }

    /**
     * 贪心策略，先让全部司机去 A，然后看让哪一半司机去 B 能够得到的收益最大
     */
    static int maxMoney4(int[][] income) {
        int N = income.length;
        int[] arr = new int[N];
        int sum = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = income[i][1] - income[i][0];
            sum += income[i][0];
        }
        // 让去 B-A 最大的前 N 个去 B
        Arrays.sort(arr);
        int M = N >> 1;
        for (int i = N - 1; i >= M; i--) {
            sum += arr[i];
        }
        return sum;
    }

    // for test
    private static int[][] randomMatrix(int len, int value) {
        int[][] ans = new int[len << 1][2];
        for (int i = 0; i < ans.length; i++) {
            ans[i][0] = (int) (Math.random() * value);
            ans[i][1] = (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTimes = 100;
        int maxSize = 10;
        int maxValue = 10;
        for (int i = 0; i < testTimes; i++) {
            int[][] income = randomMatrix(maxSize, maxValue);
            int ans1 = maxMoney(income);
            int ans2 = maxMoney2(income);
            int ans3 = maxMoney3(income);
            int ans4 = maxMoney4(income);
            if (ans1 != ans2 || ans1 != ans2 || ans1 != ans4) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
