package learn.algorithm.coding.practice.p08;

import java.util.Arrays;

import learn.algorithm.coding.comparator.MatrixComparator;

public class Code04_SnakeGame {

    static int walk1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                Info cur = process(matrix, i, j);
                ans = Math.max(ans, Math.max(cur.no, cur.yes));
            }
        }
        return ans;
    }

    /**
     * 蛇从最左列的某个位置空降，沿途来到了 (i,j) 位置停止，返回使用能力和不使用能力能够获得的最大能力值；
     * 如果蛇来不到 (i,j) 位置，返回 -1。
     *
     * @return Info 对象，包括了使用能力和不使用能力时最大值，如果到不了 (i,j) 返回 -1。
     */
    private static Info process(int[][] matrix, int i, int j) {
        // 出发位置
        if (j == 0) {
            int no = Math.max(matrix[i][0], -1);
            int yes = Math.max(-matrix[i][0], -1);
            return new Info(no, yes);
        }
        // j > 0 不在最左列
        int preNo = -1;
        int preYes = -1;
        // 蛇上次可能位置
        Info pre = process(matrix, i, j - 1);
        preNo = Math.max(pre.no, preNo);
        preYes = Math.max(pre.yes, preYes);
        if (i > 0) {
            pre = process(matrix, i - 1, j - 1);
            preNo = Math.max(pre.no, preNo);
            preYes = Math.max(pre.yes, preYes);
        }
        if (i < matrix.length - 1) {
            pre = process(matrix, i + 1, j - 1);
            preNo = Math.max(pre.no, preNo);
            preYes = Math.max(pre.yes, preYes);
        }
        // 不使用能力，蛇在 (i,j) 位置可以达到的最大值
        int no = preNo == -1 ? -1 : (Math.max(-1, preNo + matrix[i][j]));

        // 使用一次能力，，蛇在 (i,j) 位置可以达到的最大值
        // 能力只有一次，是之前用的
        int p1 = preYes == -1 ? -1 : (Math.max(-1, preYes + matrix[i][j]));
        // 能力只有一次，就当前用
        int p2 = preNo == -1 ? -1 : (Math.max(-1, preNo - matrix[i][j]));
        int yes = Math.max(Math.max(p1, p2), -1);
        return new Info(no, yes);
    }

    private static class Info {
        // 不使用能力达到的最大值
        public int no;
        // 使用能力达到的最大值
        public int yes;

        public Info(int n, int y) {
            no = n;
            yes = y;
        }
    }

    static int walk2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int n = matrix.length;
        int m = matrix[0].length;
        Info[][] dp = new Info[n][m];
        for (int i = 0; i < n; i++) {
            int no = Math.max(-1, matrix[i][0]);
            int yes = Math.max(-1, -matrix[i][0]);
            dp[i][0] = new Info(no, yes);
            max = Math.max(max, Math.max(no, yes));
        }
        for (int j = 1; j < m; j++) {
            for (int i = 0; i < n; i++) {
                // 从左边来
                Info pre = dp[i][j - 1];
                int preNo = Math.max(-1, pre.no);
                int preYes = Math.max(-1, pre.yes);
                // 从左上角来
                if (i > 0) {
                    pre = dp[i - 1][j - 1];
                    preNo = Math.max(preNo, pre.no);
                    preYes = Math.max(preYes, pre.yes);
                }
                // 从左下角来
                if (i < n - 1) {
                    pre = dp[i + 1][j - 1];
                    preNo = Math.max(preNo, pre.no);
                    preYes = Math.max(preYes, pre.yes);
                }
                // 不使用能力最大值
                int no = (preNo == -1) ? -1 : preNo + matrix[i][j];
                // 使用能力，有两种情况，上次使用了能力，这次就不能用了，上次没有使用能力，这次可以使用
                int p1 = preYes == -1 ? -1 : preYes + matrix[i][j];
                int p2 = preNo == -1 ? -1 : preNo - matrix[i][j];
                int yes = Math.max(Math.max(p1, p2), -1);
                dp[i][j] = new Info(no, yes);
                max = Math.max(max, Math.max(no, yes));
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int N = 7;
        int M = 7;
        int V = 10;
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            int r = (int) (Math.random() * (N + 1));
            int c = (int) (Math.random() * (M + 1));
            int[][] matrix = MatrixComparator.generateRandomMatrix(r, c, V);
            int ans1 = walk1(matrix);
            int ans2 = walk2(matrix);
            if (ans1 != ans2) {
                for (int j = 0; j < matrix.length; j++) {
                    System.out.println(Arrays.toString(matrix[j]));
                }
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
