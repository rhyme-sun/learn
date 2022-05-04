package learn.algorithm.dp.status;

import java.util.ArrayList;
import java.util.List;

import learn.algorithm.comparator.GraphComparator;

/**
 * TSP 问题，描述如下：
 * 有 N 个城市，任何两个城市之间的都有距离，任何一座城市到自己的距离都为 0。所有点到点的距离都存在一个 N*N 的二维数组 matrix 里，
 * 也就是整张图由邻接矩阵表示。现要求一旅行商从 k 城市出发必须经过每一个城市且只在一个城市逗留一次，最后回到出发的 k 城，返回总距离最短的路的距离。
 */
public class TSP {

    static int minDistance1(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int[] city = new int[matrix.length];
        return process1(matrix, city, 0);
    }

    /**
     * 考虑当前来到 start 城市，最终回到 0 号城市的最短路径
     *
     * @param matrix 城市图的邻接矩阵表示
     * @param city   记录已经经过的城市
     *               city[i]=-1 表示该城市已经选择过
     *               city[i]=0 表示该城市没有选择过
     * @param start  当前来到的城市
     * @return 回到 0 号城市的最短路径
     */
    private static int process1(int[][] matrix, int[] city, int start) {
        int cityNum = 0;
        for (int i = 0; i < city.length; i++) {
            if (city[i] != -1) {
                cityNum++;
            }
        }
        // 来到最后一个城市，最短距离就是从该城市回到 0 号城市的距离
        if (cityNum == 1) {
            return matrix[start][0];
        }
        // cityNum > 1
        city[start] = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < city.length; i++) {
            if (city[i] != -1) {
                // 从 start 到 i
                int next = matrix[start][i] + process1(matrix, city, i);
                city[i] = 0;
                min = Math.min(next, min);
            }
        }
        return min;
    }

    /**
     * 使用位表示一个城市是否经过
     */
    static int minDistance2(int[][] matrix) {
        int N = matrix.length; // 0...N-1
        int allCity = (1 << N) - 1;
        return process2(matrix, allCity, 0);
    }

    /**
     * 考虑当前来到 start 城市，最终回到 0 号城市的最短路径
     * <p>
     * cityStatus 表示城市是否经过过，对应位置上如果为 1 表示未经过，为 0 表示已经经过了，比如 11111 表示所有城市还未经过，11110 表示经过了
     * 0 号城市
     */
    private static int process2(int[][] matrix, int cityStatus, int start) {
        // 还剩一个城市
        if (cityStatus == (cityStatus & (~cityStatus + 1))) {
            return matrix[start][0];
        }

        cityStatus &= (~(1 << start));
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if ((cityStatus & (1 << i)) != 0) {
                int cur = matrix[start][i] + process2(matrix, cityStatus, i);
                // 恢复现场 i 位置的 0 变成 1
                cityStatus |= (1 << i);
                min = Math.min(min, cur);
            }
        }
        return min;
    }

    /**
     * 添加缓存优化
     */
    static int minDistance3(int[][] matrix) {
        int N = matrix.length;
        int allCity = (1 << N) - 1;
        int[][] dp = new int[1 << N][N];
        for (int i = 0; i < (1 << N); i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j] = -1;
            }
        }
        return process3(matrix, allCity, 0, dp);
    }

    private static int process3(int[][] matrix, int cityStatus, int start, int[][] dp) {
        if (dp[cityStatus][start] != -1) {
            return dp[cityStatus][start];
        }
        if (cityStatus == (cityStatus & (~cityStatus + 1))) {
            dp[cityStatus][start] = matrix[start][0];
        } else {
            cityStatus &= (~(1 << start));
            int min = Integer.MAX_VALUE;
            for (int move = 0; move < matrix.length; move++) {
                if (move != start && (cityStatus & (1 << move)) != 0) {
                    int cur = matrix[start][move] + process3(matrix, cityStatus, move, dp);
                    min = Math.min(min, cur);
                }
            }
            cityStatus |= (1 << start);
            dp[cityStatus][start] = min;
        }
        return dp[cityStatus][start];
    }

    /**
     * 动态规划优化
     */
    static int minDistance4(int[][] matrix) {
        int N = matrix.length; // 0...N-1
        int statusNums = 1 << N;

        // dp[i][j] 当前在 i 这个城市选择的状态下，从 j 位置出发到 0 号城市的最短距离
        int[][] dp = new int[statusNums][N];

        for (int status = 0; status < statusNums; status++) {
            for (int start = 0; start < N; start++) {
                // start 位置还未选择
                if ((status & (1 << start)) != 0) {
                    if (status == (status & (~status + 1))) {
                        dp[status][start] = matrix[start][0];
                    } else {
                        int min = Integer.MAX_VALUE;
                        int preStatus = status & (~(1 << start));
                        for (int i = 0; i < N; i++) {
                            if ((preStatus & (1 << i)) != 0) {
                                int cur = matrix[start][i] + dp[preStatus][i];
                                min = Math.min(min, cur);
                            }
                        }
                        dp[status][start] = min;
                    }
                }
            }
        }
        // 从 0 号城市回到 0 号城市，经过全部城市的最短路径
        return dp[statusNums - 1][0];
    }

    public static void main(String[] args) {
        int len = 7;
        int value = 100;
        System.out.println("功能测试开始");
        for (int i = 0; i < 20000; i++) {
            int[][] matrix = GraphComparator.generateGraph(len, value);
            int ans1 = minDistance1(matrix);
            int ans2 = minDistance2(matrix);
            int ans3 = minDistance3(matrix);
            int ans4 = minDistance4(matrix);
            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("功能测试结束");
    }
}
