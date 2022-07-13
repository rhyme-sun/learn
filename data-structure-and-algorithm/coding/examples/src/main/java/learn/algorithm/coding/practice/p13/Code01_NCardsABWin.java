package learn.algorithm.coding.practice.p13;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 谷歌面试题扩展版
 * 面值为 1~N 的牌组成一组，每次你从组里等概率的抽出 1~N 中的一张，下次抽会换一个新的组，有无限组。
 * 记录抽牌的累加和为 sum：
 * - 当 `sum<a`，你将一直抽牌；
 * - 当 `a<=sum<b` ，你将获胜；
 * - 当 `sum>=b`，你将失败；
 * <p>
 * 返回获胜的概率，给定的参数为 N，a，b.
 */
public class Code01_NCardsABWin {

    // 谷歌面试题
    // 面值为 1~10 的牌组成一组，
    // 每次你从组里等概率的抽出 1~10 中的一张
    // 下次抽会换一个新的组，有无限组
    // 当累加和 <17 时，你将一直抽牌
    // 当累加和 >=17 且 <21 时，你将获胜
    // 当累加和 >=21 时，你将失败
    // 返回获胜的概率
    public static double f1() {
        return p1(0);
    }

    // 游戏的规则，如上
    // 当你来到 cur 这个累加和的时候，获胜概率是多少返回！
    public static double p1(int cur) {
        if (cur >= 17 && cur < 21) {
            return 1.0;
        }
        if (cur >= 21) {
            return 0.0;
        }
        double w = 0.0;
        for (int i = 1; i <= 10; i++) {
            w += p1(cur + i);
        }
        return w / 10;
    }

    // 谷歌面试题扩展版
    // 面值为 1~N 的牌组成一组，
    // 每次你从组里等概率的抽出 1~N 中的一张
    // 下次抽会换一个新的组，有无限组
    // 当累加和 <a 时，你将一直抽牌
    // 当累加和 >=a 且 <b 时，你将获胜
    // 当累加和 >=b 时，你将失败
    // 返回获胜的概率，给定的参数为 N，a，b
    public static double f2(int N, int a, int b) {
        if (N < 1 || a >= b || a < 0 || b < 0) {
            return 0.0;
        }
        if (b - a >= N) {
            return 1.0;
        }
        // 所有参数都合法，并且 b-a < N
        return p2(0, N, a, b);
    }

    // 当来到 cur 累加和的时候，根据规则返回获胜概率
    public static double p2(int cur, int N, int a, int b) {
        if (cur >= a && cur < b) {
            return 1.0;
        }
        if (cur >= b) {
            return 0.0;
        }
        double w = 0.0;
        for (int i = 1; i <= N; i++) {
            w += p2(cur + i, N, a, b);
        }
        return w / N;
    }

    // f2的改进版本，用到了观察位置优化枚举的技巧
    // 可以课上讲一下
    public static double f3(int N, int a, int b) {
        if (N < 1 || a >= b || a < 0 || b < 0) {
            return 0.0;
        }
        if (b - a >= N) {
            return 1.0;
        }
        return p3(0, N, a, b);
    }

    public static double p3(int cur, int N, int a, int b) {
        if (cur >= a && cur < b) {
            return 1.0;
        }
        if (cur >= b) {
            return 0.0;
        }
        // 使用斜率优化时增加的新边界
        if (cur == a - 1) {
            return 1.0 * (b - a) / N;
        }
        double w = p3(cur + 1, N, a, b) + p3(cur + 1, N, a, b) * N - p3(cur + 1 + N, N, a, b);
        return w / N;
    }

    // f3 的改进版本的动态规划
    // 可以课上讲一下
    public static double f4(int N, int a, int b) {
        if (N < 1 || a >= b || a < 0 || b < 0) {
            return 0.0;
        }
        if (b - a >= N) {
            return 1.0;
        }
        double[] dp = new double[b];
        for (int i = a; i < b; i++) {
            dp[i] = 1.0;
        }
        if (a - 1 >= 0) {
            dp[a - 1] = 1.0 * (b - a) / N;
        }
        for (int cur = a - 2; cur >= 0; cur--) {
            double w = dp[cur + 1] + dp[cur + 1] * N;
            if (cur + 1 + N < b) {
                w -= dp[cur + 1 + N];
            }
            dp[cur] = w / N;
        }
        return dp[0];
    }

    public static void main(String[] args) {
        int N = 10;
        int a = 17;
        int b = 21;
        System.out.println("N = " + N + ", a = " + a + ", b = " + b);
        System.out.println(f1());
        System.out.println(f2(N, a, b));
        System.out.println(f3(N, a, b));
        System.out.println(f4(N, a, b));

        int maxN = 15;
        int maxM = 20;
        int testTime = 100000;
        // 比对 double 类型答案可能会有精度对不准的问题，所以答案一律只保留小数点后四位进行比对
        for (int i = 0; i < testTime; i++) {
            N = (int) (Math.random() * maxN);
            a = (int) (Math.random() * maxM);
            b = (int) (Math.random() * maxM);
            double ans2 = new BigDecimal(f2(N, a, b)).setScale(4, RoundingMode.HALF_UP).doubleValue();
            double ans3 = new BigDecimal(f3(N, a, b)).setScale(4, RoundingMode.HALF_UP).doubleValue();
            double ans4 = new BigDecimal(f4(N, a, b)).setScale(4, RoundingMode.HALF_UP).doubleValue();
            if (ans2 != ans3 || ans2 != ans4) {
                System.out.println("Oops!");
                System.out.println(N + " , " + a + " , " + b);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("Finish!");

        N = 10000;
        a = 67834;
        b = 72315;
        System.out.println("N = " + N + ", a = " + a + ", b = " + b + "时, 除了方法4外都超时");
        System.out.print("方法 4 答案: ");
        System.out.println(f4(N, a, b));
    }
}
