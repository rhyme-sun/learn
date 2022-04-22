package learn.algorithm.slidingwindow;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * arr 是货币数组，其中的值都是正数。再给定一个正数 aim。每个值都认为是一张货币，返回组成 aim 的最少货币数。
 */
public class MinCoinsOnePaper {

    /**
     * 暴力递归
     */
    static int minCoins(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    /**
     * 当前来到 arr[index...] 位置，考虑还剩 rest， 返回最小货币数量
     * 使用 Integer.MAX_VALUE 标记无效值
     */
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        // 第一种可能，不选 index 位置的货币
        int p1 = process(arr, index + 1, rest);
        // 第二种可能，选择 index 位置的货币
        int p2 = Integer.MAX_VALUE;
        if (rest - arr[index] >= 0) {
            int next = process(arr, index + 1, rest - arr[index]);
            if (next != Integer.MAX_VALUE) {
                p2 = 1 + next;
            }
        }
        return Math.min(p1, p2);
    }

    /**
     * 动态规划优化，O(arr长度 * aim)
     */
    static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[n][i] = Integer.MAX_VALUE;
        }
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 第一种可能，不选 index 位置的货币
                int p1 = dp[index + 1][rest];
                // 第二种可能，选择 index 位置的货币
                int p2 = Integer.MAX_VALUE;
                if (rest - arr[index] >= 0) {
                    int next = dp[index + 1][rest - arr[index]];
                    if (next != Integer.MAX_VALUE) {
                        p2 = 1 + next;
                    }
                }
                dp[index][rest] = Math.min(p1, p2);
            }
        }
        return dp[0][aim];
    }

    /**
     * 暴力递归，使用面值和其频率数组
     */
    static int minCoins2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        return process2(coins, zhangs, 0, aim);
    }

    /**
     * 当前来到 index 位置，考虑还剩 rest， 返回最小货币数量
     * 使用 Integer.MAX_VALUE 标记无效值
     *
     * @param coins  表示货币面值数组
     * @param zhangs 表示面值对应面值的张数
     */
    private static int process2(int[] coins, int[] zhangs, int index, int rest) {
        if (index == coins.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        for (int zhang = 0; zhang <= zhangs[index]; zhang++) {
            if (rest - zhang * coins[index] >= 0) {
                int next = process2(coins, zhangs, index + 1, rest - zhang * coins[index]);
                if (next != Integer.MAX_VALUE) {
                    ans = Math.min(ans, zhang + next);
                }
            }
        }
        return ans;
    }

    /**
     * 使用面值频率数组优化（张数压缩）
     * 时间复杂度为：O(arr长度) + O(货币种数 * aim * 每种货币的平均张数)
     */
    static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        // 得到 info 时间复杂度 O(arr长度)
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;

        int n = coins.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[n][j] = Integer.MAX_VALUE;
        }
        // 这三层 for 循环，时间复杂度为 O(货币种数 * aim * 每种货币的平均张数)
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                for (int zhang = 1; zhang * coins[index] <= aim && zhang <= zhangs[index]; zhang++) {
                    if (rest - zhang * coins[index] >= 0
                            && dp[index + 1][rest - zhang * coins[index]] != Integer.MAX_VALUE) {
                        dp[index][rest] = Math.min(dp[index][rest], zhang + dp[index + 1][rest - zhang * coins[index]]);
                    }
                }
            }
        }
        return dp[0][aim];
    }

    /**
     * dp2 的去迭代优化，优化需要用到窗口内最小值的更新结构
     * <p>
     * 时间复杂度为：O(arr长度) + O(货币种数 * aim)
     */
    static int dp3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        // 得到 info 时间复杂度 O(arr长度)
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int n = coins.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[n][j] = Integer.MAX_VALUE;
        }
        // 虽然是嵌套了很多循环，但是时间复杂度为 O(货币种数 * aim)
        // 因为用了窗口内最小值的更新结构
        for (int i = n - 1; i >= 0; i--) {
            for (int mod = 0; mod < Math.min(aim + 1, coins[i]); mod++) {
                // 当前面值 x，迭代次序依次为
                // mod  mod + x   mod + 2*x   mod + 3*x
                Deque<Integer> minQ = new LinkedList<>();
                minQ.add(mod);
                dp[i][mod] = dp[i + 1][mod];
                for (int r = mod + coins[i]; r <= aim; r += coins[i]) {
                    while (!minQ.isEmpty() && (dp[i + 1][minQ.peekLast()] == Integer.MAX_VALUE
                            || dp[i + 1][minQ.peekLast()] + compensate(minQ.peekLast(), r, coins[i]) >= dp[i + 1][r])) {
                        minQ.pollLast();
                    }
                    minQ.addLast(r);
                    int overdue = r - coins[i] * (zhangs[i] + 1);
                    if (minQ.peekFirst() == overdue) {
                        minQ.pollFirst();
                    }
                    dp[i][r] = dp[i + 1][minQ.peekFirst()] + compensate(minQ.peekFirst(), r, coins[i]);
                }
            }
        }
        return dp[0][aim];
    }

    static class Info {
        public int[] coins;
        public int[] zhangs;

        public Info(int[] c, int[] z) {
            coins = c;
            zhangs = z;
        }
    }

    /**
     * 获取货币数组中相同面值的货币出现的次数
     */
    private static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int value : arr) {
            if (!counts.containsKey(value)) {
                counts.put(value, 1);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        int N = counts.size();
        int[] coins = new int[N];
        int[] zhangs = new int[N];
        int index = 0;
        for (Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            zhangs[index++] = entry.getValue();
        }
        return new Info(coins, zhangs);
    }

    /**
     * 补偿值
     * 入队时和队列元素值比较时的补偿值（即用队列种位置对应的值 + 补偿值和要入队的值进行比较）
     *
     * @return 补偿值
     */
    private static int compensate(int pre, int cur, int coin) {
        return (cur - pre) / coin;
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = ArrayComparator.generatePositiveRandomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = minCoins2(arr, aim);
            int ans4 = dp2(arr, aim);
            if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("Finish!");

        int aim;
        int[] arr;
        long start;

        System.out.println();
        System.out.println("货币很少出现重复性能测试开始");
        maxLen = 20000;
        maxValue = 20000;
        aim = 60000;
        arr = ArrayComparator.generatePositiveRandomArray(maxLen, maxValue);

        start = System.currentTimeMillis();
        dp2(arr, aim);
        System.out.println("动态规划优化（面值压缩）（dp2）运行时间 : " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        dp3(arr, aim);
        System.out.println("滑动窗口取出迭代行为（dp3）运行时间 : " + (System.currentTimeMillis() - start) + " ms");

        System.out.println();
        System.out.println("货币大量重复出现情况下性能测试开始");
        maxLen = 20000;
        maxValue = 20;
        aim = 60000;
        arr = ArrayComparator.generatePositiveRandomArray(maxLen, maxValue);
        start = System.currentTimeMillis();
        dp2(arr, aim);
        System.out.println("动态规划优化（面值压缩）（dp2）运行时间 : " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        dp3(arr, aim);
        System.out.println("滑动窗口取出迭代行为（dp3）运行时间 : " + (System.currentTimeMillis() - start) + " ms");

        System.out.println();
        System.out.println("当货币很少出现重复，dp2 比 dp3 有常数时间优势");
        System.out.println("当货币大量出现重复，dp3 时间复杂度明显优于 dp2");
        System.out.println("dp3 的优化用到了窗口内最小值的更新结构");
    }
}
