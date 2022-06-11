package learn.algorithm.advance.algorithm.amout;

import java.util.Map;
import java.util.TreeMap;

/**
 * 根据数据规模猜解法
 * 题目描述如下：
 * 牛牛家里一共有 n 袋零食, 第i袋零食体积为 v[i]，背包容量为 w。牛牛想知道在总体积不超过背包容量的情况下,一共有多少种零食放法。
 * 体积为 0 也算一种方法
 */
public class Code02_SnacksWays {

    /**
     * 暴力递归，不分治，时间复杂度为 2^w
     */
    static int ways1(int[] arr, int w) {
        return process(arr, 0, w);
    }

    /**
     * 当前来到 index 位置的零食，背包还剩 rest 的容量，返回选择零食的方法数
     */
    private static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            // 返回 -1，表示方案无效
            return -1;
        }
        if (index == arr.length) {
            return 1;
        }
        // 可能性 1：不要 index 位置的零食
        int next1 = process(arr, index + 1, rest);
        // 可能性 2：要当前位置的零食
        int next2 = process(arr, index + 1, rest - arr[index]);
        return next1 + (next2 == -1 ? 0 : next2);
    }

    /**
     * 上述递归尝试的动态规划优化
     */
    static int dp1(int[] arr, int w) {
        int N = arr.length;
        int[][] dp = new int[N + 1][w + 1];
        for (int j = 0; j <= w; j++) {
            dp[N][j] = 1;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= w; j++) {
                dp[i][j] = dp[i + 1][j] + ((j - arr[i] >= 0) ? dp[i + 1][j - arr[i]] : 0);
            }
        }
        return dp[0][w];
    }

    static int ways2(int[] arr, int w) {
        int N = arr.length;
        int[][] dp = new int[N][w + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = 1;
        }
        if (arr[0] <= w) {
            dp[0][arr[0]] = 1;
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= w; j++) {
                dp[i][j] = dp[i - 1][j] + ((j - arr[i]) >= 0 ? dp[i - 1][j - arr[i]] : 0);
            }
        }
        int ans = 0;
        for (int j = 0; j <= w; j++) {
            ans += dp[N - 1][j];
        }
        return ans;
    }

    /**
     * 分治 + 递归
     */
    static long ways3(int[] arr, int bag) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] <= bag ? 2 : 1;
        }
        int mid = (arr.length - 1) >> 1;
        TreeMap<Long, Long> lmap = new TreeMap<>();
        long ways = process(arr, 0, mid, bag, 0, lmap);
        TreeMap<Long, Long> rmap = new TreeMap<>();
        ways += process(arr, mid + 1, arr.length - 1, bag, 0, rmap);
        TreeMap<Long, Long> rpre = new TreeMap<>();
        long pre = 0;
        for (Map.Entry<Long, Long> entry : rmap.entrySet()) {
            pre += entry.getValue();
            rpre.put(entry.getKey(), pre);
        }
        for (Map.Entry<Long, Long> entry : lmap.entrySet()) {
            long lweight = entry.getKey();
            long lways = entry.getValue();
            Long floor = rpre.floorKey(bag - lweight);
            if (floor != null) {
                long rways = rpre.get(floor);
                ways += lways * rways;
            }
        }
        return ways + 1;
    }

    /**
     * 当前来到 index 位置零食，背包还剩 rest 的容量，返回选择零食的方法数
     * 并维护号零食体积所有情况的累加和及其出现的次数，后序合并结果时会使用
     *
     * @param arr   样本数组
     * @param index 当前来到零食的位置
     * @param end   零食结束位置
     * @param rest  背包剩余容量
     * @param sum   零食体积累加和对应累加和一共出现的次数（用作分治合并）
     * @param map   存放零食累加和和累加和一共出现的次数（用作分治合并）
     * @return 方法数
     */
    private static long process(int[] arr, int index, int end, int rest, long sum, TreeMap<Long, Long> map) {
        if (sum > rest) {
            return 0;
        }
        if (index > end) {
            if (sum != 0) {
                if (!map.containsKey(sum)) {
                    map.put(sum, 1L);
                } else {
                    map.put(sum, map.get(sum) + 1);
                }
                return 1;
            } else {
                return 0;
            }
        } else {
            long ways = process(arr, index + 1, end, rest, sum, map);
            ways += process(arr, index + 1, end, rest, sum + arr[index], map);
            return ways;
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 3, 2, 9};
        int w = 8;
        System.out.println(ways1(arr, w));
        System.out.println(dp1(arr, w));
        System.out.println(ways2(arr, w));
        System.out.println(ways3(arr, w));
    }
}
