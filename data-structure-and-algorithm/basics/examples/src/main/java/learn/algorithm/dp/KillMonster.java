package learn.algorithm.dp;

/**
 * 打怪兽问题，描述如下：
 * 给定 3 个参数，N、M、K，怪兽有 N 滴血，等着英雄来砍自己，英雄每一次打击，都会让怪兽流失 [0~M] 的血量，
 * 每一次在 [0~M] 上等概率的获得一个值。求 K 次打击之后，英雄把怪兽砍死的概率（中途砍死也算砍死）。
 */
public class KillMonster {

    /**
     * 求砍死怪物，递归尝试
     *
     * @param hp     怪兽的血量
     * @param attack 随机伤害的范围为 0~attack
     * @param times  打击次数
     * @return 砍死怪物的概率
     */
    static double kill(int hp, int attack, int times) {
        if (hp < 1 || attack < 1 || times < 1) {
            return 0;
        }
        long all = (long) Math.pow(attack + 1, times);
        long kill = process(hp, attack, times);
        return ((double) kill / (double) all);
    }

    /**
     * 讨论怪兽还剩 hp 点血，还剩 times 次打击次数时，击杀怪物情况有多少种
     *
     * @param hp     怪兽还剩 hp 点血
     * @param attack 每次的伤害在 [0~attack] 范围上
     * @param times  还有 times 次可以砍
     * @return 击杀怪物情况数
     */
    private static long process(int hp, int attack, int times) {
        if (times == 0) {
            return hp <= 0 ? 1 : 0;
        }
        // hp 小于等于 0 后，可以根据打击次数直接计算出击杀情况数量
        if (hp <= 0) {
            return (long) Math.pow(attack + 1, times);
        }
        long ways = 0;
        for (int i = 0; i <= attack; i++) {
            ways += process(hp - i, attack, times - 1);
        }
        return ways;
    }

    /**
     * 动态规划优化
     *
     * @param totalHp    怪物总血量
     * @param attack     每次的伤害在 [0~attack] 范围上
     * @param totalTimes 攻击次数
     * @return 击杀怪物概率
     */
    static double dp1(int totalHp, int attack, int totalTimes) {
        if (totalHp < 1 || attack < 1 || totalTimes < 1) {
            return 0;
        }
        long all = (long) Math.pow(attack + 1, totalTimes);
        long[][] dp = new long[totalTimes + 1][totalHp + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= totalTimes; times++) {
            dp[times][0] = (long) Math.pow(attack + 1, times);
            for (int hp = 1; hp <= totalHp; hp++) {
                long ways = 0;
                for (int i = 0; i <= attack; i++) {
                    if (hp - i >= 0) {
                        ways += dp[times - 1][hp - i];
                    } else {
                        ways += (long) Math.pow(attack + 1, times - 1);
                    }
                }
                dp[times][hp] = ways;
            }
        }
        long kill = dp[totalTimes][totalHp];
        return ((double) kill / (double) all);
    }

    /**
     * 去迭代行为优化
     *
     * @param totalHp    怪物总血量
     * @param attack     每次的伤害在 [0~attack] 范围上
     * @param totalTimes 攻击次数
     * @return 击杀怪物概率
     */
    static double dp2(int totalHp, int attack, int totalTimes) {
        if (totalHp < 1 || attack < 1 || totalTimes < 1) {
            return 0;
        }
        long all = (long) Math.pow(attack + 1, totalTimes);
        long[][] dp = new long[totalTimes + 1][totalHp + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= totalTimes; times++) {
            dp[times][0] = (long) Math.pow(attack + 1, times);
            for (int hp = 1; hp <= totalHp; hp++) {
                dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
                if (hp - (attack + 1) >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - (attack + 1)];
                } else {
                    dp[times][hp] -= Math.pow(attack + 1, times - 1);
                }
            }
        }
        long kill = dp[totalTimes][totalHp];
        return ((double) kill / (double) all);
    }

    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200;
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = kill(N, M, K);
            double ans2 = dp1(N, M, K);
            double ans3 = dp2(N, M, K);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("Finish!");
    }
}
