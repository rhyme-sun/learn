package learn.algorithm.practice.p18;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 给定一个矩阵 matrix，先从左上角开始，每一步只能往右或者往下走，走到右下角。然后从右下角出发，每一步只能往上或者往左走，再回到左上角。任何一个位置的数字，只能获得一遍。返回最大路径和。
 * 输入描述:
 * 第一行输入两个整数 M 和 N，M,N<=200
 * 接下来 M 行，每行 N 个整数，表示矩阵中元素
 * 输出描述:
 * 输出一个整数，表示最大路径和
 * 牛客网题目：https://www.nowcoder.com/questionTerminal/8ecfe02124674e908b2aae65aad4efdf
 * <p>
 * 把如下的全部代码拷贝进 java 编辑器
 * 把文件大类名字改成 Main，可以直接通过
 */
public class Code03_CherryPickup {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] matrix = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        int ans = cherryPickup2(matrix);
        System.out.println(ans);
        sc.close();
    }

    // 递归尝试版本，帮助理解
    static int cherryPickup(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        return process2(matrix, n - 1, m - 1, n - 1, m - 1);
    }

    // A 和 B 同时从左上角出发，A 来到 (a,b)，B 来到 (c,d)，A 和 B 一起结算的最大路径和
    private static int process2(int[][] matrix, int a, int b, int c, int d) {
        // 越界，返回无效值
        if (a == -1 || b == -1 || c == -1 || d == -1) {
            return -1;
        }
        if (a == 0 && b == 0) {
            return matrix[0][0];
        }
        int max = -1;
        // A 从上面来，B 从上面来
        max = Math.max(max, process2(matrix, a - 1, b, c - 1, d));
        // A 从上面来，B 从左边来
        max = Math.max(max, process2(matrix, a - 1, b, c, d - 1));
        // A 从左边来，B 从上面来
        max = Math.max(max, process2(matrix, a, b - 1, c - 1, d));
        // A 从左边来，B 从左边来
        max = Math.max(max, process2(matrix, a, b - 1, c, d - 1));
        if (max == -1) {
            return -1;
        }
        if (a == c && b == d) {
            max += matrix[a][b];
        } else {
            max += matrix[a][b] + matrix[c][d];
        }
        return max;
    }

    // 记忆化搜索优化，并且去掉了一维
    static int cherryPickup2(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][][] dp = new int[n][m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        return process1(matrix, n - 1, m - 1, n - 1, dp);
    }

    // A 和 B 同时从左上角出发，A 来到 (a,b)，B 来到 (c,a+b-c)，A 和 B 一起结算的最大路径和
    private static int process1(int[][] matrix, int a, int b, int c, int[][][] dp) {
        // 越界，返回无效值
        int d = a + b - c;
        if (a == -1 || b == -1 || c == -1 || d == -1) {
            return -1;
        }
        if (dp[a][b][c] != -1) {
            return dp[a][b][c];
        }
        int ans = -1;
        if (a == 0 && b == 0) {
            ans = matrix[0][0];
        } else {
            // A 从上面来，B 从上面来
            ans = Math.max(ans, process1(matrix, a - 1, b, c - 1, dp));
            // A 从上面来，B 从左边来
            ans = Math.max(ans, process1(matrix, a - 1, b, c, dp));
            // A 从左边来，B 从上面来
            ans = Math.max(ans, process1(matrix, a, b - 1, c - 1, dp));
            // A 从左边来，B 从左边来
            ans = Math.max(ans, process1(matrix, a, b - 1, c, dp));
            if (ans == -1) {
                return -1;
            }
            if (a == c && b == d) {
                ans += matrix[a][b];
            } else {
                ans += matrix[a][b] + matrix[c][d];
            }
        }
        dp[a][b][c] = ans;
        return ans;
    }

    // 记忆化搜索优化，方法二已终止位置进行讨论，这里一起始位置进行讨论，大思路一直
    static int cherryPickup3(int[][] grid) {
        int N = grid.length;
        int M = grid[0].length;

        // dp[i][j][k]
        // A 从 (i,j) 出发，B 从 (k,i+j-k) 到右下角位置一起结算最大路径和
        int[][][] dp = new int[N][M][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < N; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }
        int ans = process3(grid, 0, 0, 0, dp);
        return ans < 0 ? 0 : ans;
    }

    // A 从 (x1,y1) 出发，B 从 (x2,x1+y1-x2) 到右下角位置一起结算最大路径和
    private static int process3(int[][] grid, int x1, int y1, int x2, int[][][] dp) {
        if (x1 == grid.length || y1 == grid[0].length || x2 == grid.length || x1 + y1 - x2 == grid[0].length) {
            return Integer.MIN_VALUE;
        }
        if (dp[x1][y1][x2] != Integer.MIN_VALUE) {
            return dp[x1][y1][x2];
        }
        if (x1 == grid.length - 1 && y1 == grid[0].length - 1) {
            dp[x1][y1][x2] = grid[x1][y1];
            return dp[x1][y1][x2];
        }
        int next = Integer.MIN_VALUE;
        next = Math.max(next, process3(grid, x1 + 1, y1, x2 + 1, dp));
        next = Math.max(next, process3(grid, x1 + 1, y1, x2, dp));
        next = Math.max(next, process3(grid, x1, y1 + 1, x2, dp));
        next = Math.max(next, process3(grid, x1, y1 + 1, x2 + 1, dp));
        if (grid[x1][y1] == -1 || grid[x2][x1 + y1 - x2] == -1 || next == -1) {
            dp[x1][y1][x2] = -1;
            return dp[x1][y1][x2];
        }
        if (x1 == x2) {
            dp[x1][y1][x2] = grid[x1][y1] + next;
            return dp[x1][y1][x2];
        }
        dp[x1][y1][x2] = grid[x1][y1] + grid[x2][x1 + y1 - x2] + next;
        return dp[x1][y1][x2];
    }
}