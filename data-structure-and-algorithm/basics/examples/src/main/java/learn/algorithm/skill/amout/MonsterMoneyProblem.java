package learn.algorithm.skill.amout;

/**
 * 根据数据规模猜解法
 * 贿赂怪物问题，代码如下所示：
 * 有两个数组：
 * <p>
 * - int[] d，d[i] 表示 i 号怪兽的能力；
 * - int[] p，p[i] 表示 i 号怪兽要求的钱
 * <p>
 * 开始时你的能力是 0，你的目标是从 0 号怪兽开始，通过所有的怪兽。
 * <p>
 * 如果你当前的能力，小于 i 号怪兽的能力，你必须付出 p[i] 的钱，贿赂这个怪兽，然后怪兽就会加入你，他的能力直接累加到你的能力上；
 * <p>
 * 如果你当前的能力，大于等于i号怪兽的能力，你可以选择直接通过，你的能力并不会下降，你也可以选择贿赂这个怪兽，然后怪兽就会加入你，
 * 它的能力直接累加到你的能力上。
 * <p>
 * 返回通过所有的怪兽，需要花的最小钱数。
 */
public class MonsterMoneyProblem {

    /**
     * 递归尝试 1
     */
    static long minMoney1(int[] d, int[] p) {
        return process1(d, p, 0, 0);
    }

    /**
     * 目前，你的能力是 ability，你来到了 index 号怪兽的面前，如果要通过后续所有的怪兽，返回需要花的最少钱数。
     *
     * @param d       怪物能力数组
     * @param p       怪物钱数数组
     * @param index   当前怪物编号
     * @param ability 当前能力值
     * @return 通关需要的最少钱数
     */
    private static long process1(int[] d, int[] p, int index, int ability) {
        if (index == d.length) {
            return 0;
        }
        if (ability < d[index]) {
            return p[index] + process1(d, p, index + 1, ability + d[index]);
        } else {
            // 可能性 1：不贿赂当前怪物
            long p1 = Integer.MAX_VALUE;
            // 可能性 2：贿赂当前怪物
            long p2 = p[index] + process1(d, p, index + 1, ability + d[index]);
            return Math.min(p1, p2);
        }
    }

    /**
     * 递归尝试 1，动态规划优化
     */
    static long dp1(int[] d, int[] p) {
        int sum = 0;
        for (int num : d) {
            sum += num;
        }
        long[][] dp = new long[d.length + 1][sum + 1];
        for (int i = 0; i <= sum; i++) {
            dp[0][i] = 0;
        }
        for (int cur = d.length - 1; cur >= 0; cur--) {
            for (int hp = 0; hp <= sum; hp++) {
                // 如果这种情况发生，那么这个hp必然是递归过程中不会出现的状态
                // 既然动态规划是尝试过程的优化，尝试过程碰不到的状态，不必计算
                if (hp + d[cur] > sum) {
                    continue;
                }
                if (hp < d[cur]) {
                    dp[cur][hp] = p[cur] + dp[cur + 1][hp + d[cur]];
                } else {
                    dp[cur][hp] = Math.min(p[cur] + dp[cur + 1][hp + d[cur]], dp[cur + 1][hp]);
                }
            }
        }
        return dp[0][0];
    }

    /**
     * 递归尝试 2
     */
    static int minMoney2(int[] d, int[] p) {
        int allMoney = 0;
        for (int i = 0; i < p.length; i++) {
            allMoney += p[i];
        }
        int N = d.length;
        for (int money = 0; money < allMoney; money++) {
            if (process2(d, p, N - 1, money) != -1) {
                return money;
            }
        }
        return allMoney;
    }

    /**
     * 从 0....index 号怪兽，花的钱，必须严格等于 money
     * 如果通过不了，返回-1
     * 如果可以通过，返回能通过情况下的最大能力值
     */
    private static long process2(int[] d, int[] p, int index, int money) {
        // 一个怪兽也没遇到
        if (index == -1) {
            return money == 0 ? 0 : -1;
        }
        // index >= 0
        // 1) 不贿赂当前 index 号怪兽
        long preMaxAbility = process2(d, p, index - 1, money);
        long p1 = -1;
        if (preMaxAbility != -1 && preMaxAbility >= d[index]) {
            p1 = preMaxAbility;
        }
        // 2) 贿赂当前的怪兽 当前的钱 p[index]
        long preMaxAbility2 = process2(d, p, index - 1, money - p[index]);
        long p2 = -1;
        if (preMaxAbility2 != -1) {
            p2 = d[index] + preMaxAbility2;
        }
        return Math.max(p1, p2);
    }

    /**
     * 递归尝试 2 的动态规划优化
     */
    static long dp2(int[] d, int[] p) {
        int sum = 0;
        for (int num : p) {
            sum += num;
        }
        // dp[i][j]含义：
        // 能经过0～i的怪兽，且花钱为j（花钱的严格等于j）时的武力值最大是多少？
        // 如果dp[i][j]==-1，表示经过0～i的怪兽，花钱为j是无法通过的，或者之前的钱怎么组合也得不到正好为j的钱数
        int[][] dp = new int[d.length][sum + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j <= sum; j++) {
                dp[i][j] = -1;
            }
        }
        // 经过0～i的怪兽，花钱数一定为p[0]，达到武力值d[0]的地步。其他第0行的状态一律是无效的
        dp[0][p[0]] = d[0];
        for (int i = 1; i < d.length; i++) {
            for (int j = 0; j <= sum; j++) {
                // 可能性一，为当前怪兽花钱
                // 存在条件：
                // j - p[i]要不越界，并且在钱数为j - p[i]时，要能通过0～i-1的怪兽，并且钱数组合是有效的。
                if (j >= p[i] && dp[i - 1][j - p[i]] != -1) {
                    dp[i][j] = dp[i - 1][j - p[i]] + d[i];
                }
                // 可能性二，不为当前怪兽花钱
                // 存在条件：
                // 0~i-1怪兽在花钱为j的情况下，能保证通过当前i位置的怪兽
                if (dp[i - 1][j] >= d[i]) {
                    // 两种可能性中，选武力值最大的
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                }
            }
        }
        int ans = 0;
        // dp表最后一行上，dp[N-1][j]代表：
        // 能经过0～N-1的怪兽，且花钱为j（花钱的严格等于j）时的武力值最大是多少？
        // 那么最后一行上，最左侧的不为-1的列数(j)，就是答案
        for (int j = 0; j <= sum; j++) {
            if (dp[d.length - 1][j] != -1) {
                ans = j;
                break;
            }
        }
        return ans;
    }

    public static int[][] generateTwoRandomArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arrs = new int[2][size];
        for (int i = 0; i < size; i++) {
            arrs[0][i] = (int) (Math.random() * value) + 1;
            arrs[1][i] = (int) (Math.random() * value) + 1;
        }
        return arrs;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] d = arrs[0];
            int[] p = arrs[1];
            long ans1 = minMoney1(d, p);
            long ans2 = dp1(d, p);
            long ans3 = minMoney2(d, p);
            long ans4 = dp2(d, p);
            if (ans1 != ans2 || ans2 != ans3 || ans1 != ans4) {
                System.out.println("Oops!");
            }
        }
    }
}
