package learn.algorithm.basics.algorithm.dp.business;

/**
 * 问题描述如下：
 * 想象一个象棋的棋盘，然后把整个棋盘放入第一象限，棋盘的最左下角是 (0,0) 位置，那么整个棋盘就是横坐标上 9 条线、纵坐标上 10 条线的区域。
 * 给你三个参数 x，y，k，返回马从 (0,0) 位置出发，必须走 k 步，最后落在 (x,y) 上的方法数有多少种？
 */
public class Code01_HorseJump {

    static int jump(int a, int b, int k) {
        return process(0, 0, a, b, k);
    }

    /**
     * 当前来到的位置是 (y,x)，还剩下 rest 步需要跳
     * 跳完 rest 步，正好跳到 (a,b) 的方法数是多少
     */
    private static int process(int x, int y, int a, int b, int rest) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        if (rest == 0) {
            return (a == x && b == y) ? 1 : 0;
        }
        int ways = process(x + 1, y + 2, a, b, rest - 1);
        ways += process(x + 2, y + 1, a, b, rest - 1);
        ways += process(x + 2, y - 1, a, b, rest - 1);
        ways += process(x + 1, y - 2, a, b, rest - 1);
        ways += process(x - 1, y - 2, a, b, rest - 1);
        ways += process(x - 2, y - 1, a, b, rest - 1);
        ways += process(x - 2, y + 1, a, b, rest - 1);
        ways += process(x - 1, y + 2, a, b, rest - 1);
        return ways;
    }

    /**
     * 方法 2，动态规划优化
     */
    static int dp(int a, int b, int k) {
        int[][][] dp = new int[9][10][k + 1];
        dp[a][b][0] = 1;
        for (int rest = 1; rest <= k; rest++) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 10; y++) {
                    int ways = pick(dp, x + 1, y + 2, rest - 1);
                    ways += pick(dp, x + 2, y + 1, rest - 1);
                    ways += pick(dp, x + 2, y - 1, rest - 1);
                    ways += pick(dp, x + 1, y - 2, rest - 1);
                    ways += pick(dp, x - 1, y - 2, rest - 1);
                    ways += pick(dp, x - 2, y - 1, rest - 1);
                    ways += pick(dp, x - 2, y + 1, rest - 1);
                    ways += pick(dp, x - 1, y + 2, rest - 1);
                    dp[x][y][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    /**
     * 从 dp 表拿去指定位置的数，处理边界问题
     */
    private static int pick(int[][][] dp, int x, int y, int rest) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        return dp[x][y][rest];
    }

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        System.out.println(jump(x, y, step));
        System.out.println(dp(x, y, step));
    }
}
